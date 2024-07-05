package org.apache.dolphinscheduler.dataserver.dev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiDirectory;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiExample;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiResult;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.DocApi;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.DocsInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.ExportReq;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.ExportRes;
import org.apache.dolphinscheduler.dataserver.dev.extend.IApiDocSync;
import org.apache.dolphinscheduler.dataserver.dev.permission.api.ApiPermission;
import org.apache.dolphinscheduler.dataserver.dev.permission.constant.DataConstant;
import org.apache.dolphinscheduler.dataserver.dev.service.impl.ApiInfoService;
import org.apache.dolphinscheduler.dataserver.dev.service.impl.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ExtendController {


    @Autowired
    private IApiDocSync apiDocSync;


    @Autowired
    private ApiInfoService apiInfoService;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConfigService configService;

    /**
     * API DOC 同步
     */
    @GetMapping("/api-doc-sync")
    public ApiResult apiDocPush(String apiInfoId) throws Exception {
        Collection<ApiInfo> apiInfos = apiInfoService.getPathList(false);
        String result = null;
        List<ApiDirectory> directoryList = apiInfoService.loadDirectoryList();
        List<DocApi> docsInfoList = null;
        if (!ObjectUtils.isEmpty(apiInfoId)) {

            ApiInfo apiInfo = apiInfos.stream().filter(item -> item.getId().equals(apiInfoId)).findFirst().orElse(null);
            ApiExample apiExample = buildLastApiExample(apiInfo.getId());
            docsInfoList = Arrays.asList(new DocApi(apiInfo, apiExample));
        } else {
            docsInfoList = apiInfos.stream().map(item -> new DocApi(item, buildLastApiExample(item.getId()))).collect(Collectors.toList());
        }
        result = apiDocSync.sync(new DocsInfo(directoryList, docsInfoList));
        return ApiResult.success(result);
    }

    /**
     * 接口导出
     *
     * @param exportReq
     * @return
     */
    @PostMapping("/export")
    public void exportApi(ExportReq exportReq, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExportRes exportRes = apiInfoService.exportApi(exportReq);

        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(exportReq.getFileName(), "UTF-8") + ".json");
        response.addHeader("Content-Type", "application/octet-stream");
        String resStr = objectMapper.writeValueAsString(exportRes);
        response.getOutputStream().write(resStr.getBytes());
    }

    /**
     * 接口导入
     *
     * @param file
     * @param request
     * @param override 0：增量，1：覆盖
     * @return
     */
    @PostMapping("/import")
    @ApiPermission(permissions = {DataConstant.ApiDataList.ADD})
    public ApiResult importApiInfo(MultipartFile file, Integer override, HttpServletRequest request) {
        if (file == null) {
            return ApiResult.fail("file is null");
        }

        try {
            ExportRes exportRes = objectMapper.readValue(file.getBytes(), ExportRes.class);
            Object result = apiInfoService.importAPI(exportRes.getDirectories(), exportRes.getApiInfos(), override == 1);
            return ApiResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.fail(e.getMessage());
        }
    }



    /**
     * 动态配置获取
     *
     * @param request
     * @return
     */
    @GetMapping("/api-config")
    public ApiResult getApiConfig(HttpServletRequest request) {
        Object result = null;
        try {
            result = configService.getYmlConfig();
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.success(result);
    }

    /**
     * 动态配置修改
     *
     * @return
     */
    @PostMapping("/api-config")
    public ApiResult saveApiConfig(@RequestBody(required = false) String configContext, HttpServletRequest request) {

        try {
            configService.saveYmlConfig(configContext);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.success(null);
    }


    private ApiExample buildLastApiExample(String apiInfoId) {
        List<ApiExample> result = apiInfoService.listApiExampleScript(apiInfoId, 1, 1);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        ApiExample apiExample = result.get(0);
        try {
            if (!ObjectUtils.isEmpty(apiExample.getResponseBody())) {
                apiExample.setResponseBody(URLDecoder.decode(apiExample.getResponseBody(), "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return apiExample;
    }

}
