package org.apache.dolphinscheduler.dataserver.dev.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.constants.Constants;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dataserver.dev.entity.*;
import org.apache.dolphinscheduler.dataserver.dev.entity.request.QueryPageRequest;
import org.apache.dolphinscheduler.dataserver.dev.entity.request.QuerySingleObjRequest;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.*;
import org.apache.dolphinscheduler.dataserver.dev.extend.IScriptEncrypt;
import org.apache.dolphinscheduler.dataserver.dev.permission.api.ApiPermission;
import org.apache.dolphinscheduler.dataserver.dev.permission.constant.DataConstant;
import org.apache.dolphinscheduler.dataserver.dev.permission.datalist.DataListPermissionClient;
import org.apache.dolphinscheduler.dataserver.dev.service.APIServerInfoService;
import org.apache.dolphinscheduler.dataserver.dev.service.impl.ApiInfoService;
import org.apache.dolphinscheduler.dataserver.dev.service.impl.CompletionService;
import org.apache.dolphinscheduler.dataserver.dev.utils.GenerateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.dolphinscheduler.api.enums.Status.DATA_SERVER_DIR_NOT_FOUND_ERROR;
import static org.apache.dolphinscheduler.api.enums.Status.INTERNAL_SERVER_ERROR_ARGS;


@Tag(name = "数据服务管理接口")
@RestController
@RequestMapping("/dmc/data-server")
public class APIInfoController {

    @Autowired
    private IScriptEncrypt scriptEncrypt;

    @Autowired
    private APIServerInfoService apiServerInfoService;

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private CompletionService completionService;

    @Autowired
    private DataListPermissionClient dataListPermissionClient;

    /**
     * 获取接口列表
     *
     * @param pageRequest request params,click {@link QueryPageRequest}
     * @return {@link List<ApiInfo>}
     */
    @PostMapping("/api-list")
    public Result<List<ApiInfo>> getPathList(@RequestBody QueryPageRequest pageRequest) {
        return Result.success(apiServerInfoService.queryAPIInfoPage(pageRequest));
    }

    /**
     * 单个获取
     */
    @PostMapping("/api-info/query")
    public Result<ApiInfo> getPathList(@RequestBody QuerySingleObjRequest singleObjRequest) {
        return Result.success(apiServerInfoService.queryApiInfoByParams(singleObjRequest));
    }

    @PostMapping("/api-info/create")
    @ApiPermission(permissions = {DataConstant.ApiDataList.ADD})
    public Result<String> create(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                 @RequestBody ApiInfo apiInfo) {
        if (ObjectUtils.isEmpty(apiInfo.getDirectoryId())) {
            return Result.error(DATA_SERVER_DIR_NOT_FOUND_ERROR);
        }
        apiInfo.setCreateAuthor(loginUser.getId());
        try {
            if (!ObjectUtils.isEmpty(apiInfo.getScript())) {
                apiInfo.setScript(scriptEncrypt.encrypt(apiInfo.getScript()));
            }
            String apiInfoId = apiInfoService.saveApiInfo(apiInfo);
            return Result.success(apiInfoId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorWithArgs(INTERNAL_SERVER_ERROR_ARGS, e.getMessage());
        }
    }

    @PostMapping("/api-info/update")
    @ApiPermission(permissions = {DataConstant.ApiDataList.ADD})
    public Result<String> update(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @RequestBody ApiInfo apiInfo) {
        if (ObjectUtils.isEmpty(apiInfo.getDirectoryId())) {
            return Result.error(DATA_SERVER_DIR_NOT_FOUND_ERROR);
        }
        apiInfo.setCreateAuthor(loginUser.getId());
        try {
            if (!ObjectUtils.isEmpty(apiInfo.getScript())) {
                apiInfo.setScript(scriptEncrypt.encrypt(apiInfo.getScript()));
            }

            String apiInfoId = apiInfoService.saveApiInfo(apiInfo);
            return Result.success(apiInfoId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorWithArgs(INTERNAL_SERVER_ERROR_ARGS, e.getMessage());
        }
    }


    /**
     * REMOVE APIINFO
     *
     * @param apiInfo
     */
    @PostMapping("/api-info/delete")
    @ApiPermission(permissions = {DataConstant.ApiDataList.DELETE})
    public Result<String> delete(@RequestBody ApiInfo apiInfo, HttpServletRequest request) {
        try {
            apiInfoService.deleteApiInfo(apiInfo);
            return Result.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorWithArgs(INTERNAL_SERVER_ERROR_ARGS, e.getMessage());
        }

    }


    /**
     * 模拟参数保存
     */
    @PostMapping("/api-example")
    public ApiResult saveExample(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                 @RequestBody ApiExample apiExample) throws Exception {
        if (ObjectUtils.isEmpty(apiExample.getMethod())
                || ObjectUtils.isEmpty(apiExample.getUrl())
                || ObjectUtils.isEmpty(apiExample.getRequestHeader())) {
            return ApiResult.fail("Send, then Save");
        }
        apiExample.setEditor(loginUser.getUserName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        apiExample.setCreateTime(sdf.format(new Date()));
        apiExample.setId(GenerateId.get().toHexString());
        if (!ObjectUtils.isEmpty(apiExample.getResponseBody())) {
            apiExample.setResponseBody(URLEncoder.encode(apiExample.getResponseBody(), "utf-8"));
        }

        return ApiResult.success(apiInfoService.saveExample(apiExample));
    }

    /**
     * 查询最近一次模拟数据
     */
    @GetMapping("/api-example/last")
    public ApiResult lastApiExample(String apiInfoId, Integer pageSize, Integer pageNo) throws Exception {

        List<ApiExample> result = apiInfoService.listApiExampleScript(apiInfoId, pageSize, pageNo);
        result.forEach(item -> {
            if (!ObjectUtils.isEmpty(item.getResponseBody())) {
                try {
                    item.setResponseBody(URLDecoder.decode(item.getResponseBody(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return ApiResult.success(result);
    }

    /**
     * 删除模拟数据
     */
    @DeleteMapping("/api-example")
    private ApiResult deleteExampleList(@RequestBody DeleteExamleReq deleteExamleReq, HttpServletRequest request) throws Exception {
        apiInfoService.deleteExampleList(deleteExamleReq.getApiExampleList());
        return ApiResult.success(null);
    }


    /**
     * 目录查询
     *
     * @return
     */
    @GetMapping("/directory/list")
    public ApiResult directoryList() {
        List<ApiDirectory> result = apiInfoService.loadDirectoryList().stream()
                .sorted(Comparator.comparing(ApiDirectory::getName).thenComparing(ApiDirectory::getPath))
                .collect(Collectors.toList());
        if (dataListPermissionClient.hasPermissionService()) {
            List<String> list = dataListPermissionClient.filterByPermission(result.stream().map(e -> e.getId()).collect(Collectors.toList()), DataConstant.ListDataType.dirlist);
            result = result.stream().filter(e -> list.indexOf(e.getId()) > -1).collect(Collectors.toList());
        }
        return ApiResult.success(result);


    }

    /**
     * 目录保存
     *
     * @param directory
     * @return
     */
    @PostMapping("/directory")
    @ApiPermission(permissions = {DataConstant.DirDataList.ADD})
    public ApiResult saveDirectory(@RequestBody ApiDirectory directory, HttpServletRequest request) {
        try {
            apiInfoService.saveDirectory(directory);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.success(directory.getId());
    }

    /**
     * 目录删除
     *
     * @param directory
     * @return
     */
    @DeleteMapping("/directory")
    @ApiPermission(permissions = {DataConstant.DirDataList.DELETE})
    public ApiResult removeDirectory(@RequestBody ApiDirectory directory, HttpServletRequest request) {
        try {
            apiInfoService.removeDirectory(directory);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.success(null);
    }


    /**
     * 自动完成，类型获取
     */
    @GetMapping("/completion-items")
    public ApiResult provideCompletionTypes() throws Exception {
        return ApiResult.success(completionService.provideCompletionTypes());
    }


    /**
     * 自动完成，方法解析
     */
    @PostMapping("/completion-clazz")
    public Result<List<MethodVo>> provideCompletionItems(@RequestBody ProvideCompletionReq completionReq) {
        try {
            Class<?> clazz = Class.forName(completionReq.getClazz());
            return Result.success(completionService.buildMethod(clazz));
        } catch (Throwable e) {
            e.printStackTrace();
            return Result.errorWithArgs(INTERNAL_SERVER_ERROR_ARGS, e.getMessage());
        }
    }

    /**
     * 历史记录查询
     */
    @GetMapping("/api-info/last")
    public Result<List<ApiInfoHistory>> lastApiInfo(String apiInfoId, Integer pageSize, Integer pageNo) throws Exception {
        if (ObjectUtils.isEmpty(apiInfoId)) {
            return Result.success(null);
        }
        List<ApiInfoHistory> historyList = apiInfoService.lastApiInfo(apiInfoId, pageSize, pageNo);
        for (ApiInfoHistory history : historyList) {
            history.setScript(scriptEncrypt.decrypt(history.getScript()));
        }
        return Result.success(historyList);
    }
}
