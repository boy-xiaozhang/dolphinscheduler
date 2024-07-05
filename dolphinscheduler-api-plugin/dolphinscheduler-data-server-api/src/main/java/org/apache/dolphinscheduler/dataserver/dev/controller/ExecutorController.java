package org.apache.dolphinscheduler.dataserver.dev.controller;

import org.apache.dolphinscheduler.dataserver.dev.config.RocketApiProperties;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiParams;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiResult;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.RunApiReq;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.RunApiRes;
import org.apache.dolphinscheduler.dataserver.dev.extend.ApiInfoContent;
import org.apache.dolphinscheduler.dataserver.dev.script.IScriptParse;
import org.apache.dolphinscheduler.dataserver.dev.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ExecutorController {

    @Autowired
    private ApiInfoContent apiInfoContent;

    @Autowired
    private RocketApiProperties properties;

    @Autowired
    @Lazy
    private IScriptParse scriptParse;


    /**
     * 脚本执行
     */
    @PostMapping("/api-info/run")
    public ApiResult runScript(@RequestBody RunApiReq runApiReq, HttpServletRequest request){

        RunApiRes runApiRes = new RunApiRes();
        try {
            apiInfoContent.setIsDebug(runApiReq.isDebug());
            ApiInfo apiInfo = ApiInfo.builder()
                    .fullPath(runApiReq.getPattern())
                    .options(runApiReq.getOptions())
                    .datasource(runApiReq.getDatasource())
                    .script(runApiReq.getScript())
                    .build();
            ApiParams apiParams = ApiParams.builder()
                    .header(decodeHeaderValue(runApiReq.getHeader()))
                    .pathVar(getPathVar(runApiReq.getPattern(),runApiReq.getUrl()))
                    .param(getParam(runApiReq.getUrl()))
                    .body(buildBody(runApiReq.getBody()))
                    .session(RequestUtils.buildSessionParams(request))
                    .build();
            Object value = scriptParse.runScript(apiInfo.getScript(),apiInfo,apiParams);
            runApiRes.setData(value);
            return ApiResult.success(runApiRes);
        }catch (Throwable e){
            e.printStackTrace();
            return ApiResult.fail(e.getMessage(),runApiRes);
        }finally {
            runApiRes.setLogs(apiInfoContent.getLogs());
            apiInfoContent.removeAll();
        }
    }


    private Map<String,String> decodeHeaderValue(Map<String,String> header) throws UnsupportedEncodingException {
        Map<String,String> newHeader = new HashMap<>(header.size());
        for (String key : header.keySet()){
            newHeader.put(key.toLowerCase(), URLDecoder.decode(header.get(key),"utf-8"));
        }
        return newHeader;
    }

    private Map<String,String> getPathVar(String pattern,String url){
        Integer beginIndex = url.indexOf("/",8);
        if (beginIndex == -1){
            return null;
        }
        Integer endIndex = url.indexOf("?") == -1?url.length():url.indexOf("?");
        String path = url.substring(beginIndex,endIndex);
        AntPathMatcher matcher = new AntPathMatcher();
        if (matcher.match(pattern,path)){
            return matcher.extractUriTemplateVariables(pattern,path);
        }
        return null;
    }

    private Map<String, Object> buildBody(Object body) {
        Map<String,Object> params = new HashMap<>();
        if (body instanceof Map){
            params.putAll((Map<? extends String, ?>) body);
        }
        params.put(properties.getBodyRootKey(),body);
        return params;
    }


    private Map<String,Object> getParam(String url) {
        Map<String,Object> result = new HashMap<>();
        MultiValueMap<String, String> urlMvp = UriComponentsBuilder.fromHttpUrl(url).build().getQueryParams();
        urlMvp.forEach((key,value)->{
            String firstValue = CollectionUtils.isEmpty(value)?null:value.get(0);
            result.put(key,firstValue);
        });
        return  result;
    }
}
