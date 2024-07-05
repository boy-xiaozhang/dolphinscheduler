package org.apache.dolphinscheduler.govern.datadomain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dolphinscheduler.api.exceptions.ApiException;
import org.apache.dolphinscheduler.api.utils.PageInfo;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.api.utils.TreeNode;
import org.apache.dolphinscheduler.common.constants.Constants;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.govern.datadomain.pojo.entity.DataDomain;
import org.apache.dolphinscheduler.govern.datadomain.pojo.request.DeleteRequest;
import org.apache.dolphinscheduler.govern.datadomain.pojo.request.QueryPageRequest;
import org.apache.dolphinscheduler.govern.datadomain.service.DataDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.dolphinscheduler.api.enums.Status.*;

@Tag(name = "数据域接口")
@RestController
@RequestMapping("/dmc/data-domain")
public class DataDomainController {

    @Autowired
    private DataDomainService dataDomainService;

    /**
     * query data domain page
     *
     * @param loginUser        login user
     * @param queryPageRequest createTokenRequest
     * @return {@link Result<PageInfo<DataDomain>>}
     */
    @Operation(summary = "数据域分页查询", description = "根据条件进行分页查询数据域列表")
    @GetMapping(value = "/list")
    @Parameter(name = "queryPageRequest", description = "queryPageRequest", required = true, schema = @Schema(implementation = QueryPageRequest.class))
    @ApiException(DATA_DOMAIN_QUERY_ERROR)
    public Result<PageInfo<DataDomain>> queryPageList(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                                      @RequestBody QueryPageRequest queryPageRequest) {
        PageInfo<DataDomain> dataDomainPageInfo = dataDomainService.queryDataDomainPaging(queryPageRequest, loginUser);
        return Result.success(dataDomainPageInfo);
    }


    /**
     * delete data domain by ids
     *
     * @param deleteRequest delete request params {@link DeleteRequest}
     * @return {@link  Result}
     */
    @Operation(summary = "批量删除数据域", description = "根据ID数组批量删除数据域")
    @PostMapping(value = "/remove")
    @Parameter(name = "deleteRequest", description = "deleteRequest", required = true, schema = @Schema(implementation = DeleteRequest.class))
    @ApiException(DATA_DOMAIN_DELETE_ERROR)
    public Result<String> deleteDataDomains(@RequestBody DeleteRequest deleteRequest) {
        return dataDomainService.deleteDataDomain(deleteRequest) > 0 ? Result.success() : Result.error(DATA_DOMAIN_DELETE_ZERO_ERROR);
    }


    /**
     * add data domain
     *
     * @param dataDomain data domain pojo params {@link DataDomain}
     * @return {@link  Result}
     */
    @Operation(summary = "创建数据域", description = "创建数据域")
    @PostMapping(value = "/create")
    @Parameter(name = "dataDomain", description = "dataDomain", required = true, schema = @Schema(implementation = DataDomain.class))
    @ApiException(DATA_DOMAIN_CREATE_ERROR)
    public Result<String> create(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                 @RequestBody DataDomain dataDomain) {
        return dataDomainService.createDataDomain(dataDomain, loginUser) > 0 ? Result.success() : Result.error(DATA_DOMAIN_CREATE_ERROR);
    }

    /**
     * update data domain
     *
     * @param dataDomain data domain pojo params {@link DataDomain}
     * @return {@link  Result}
     */
    @Operation(summary = "修改数据域", description = "修改数据域")
    @PostMapping(value = "/update")
    @Parameter(name = "dataDomain", description = "dataDomain", required = true, schema = @Schema(implementation = DataDomain.class))
    @ApiException(DATA_DOMAIN_UPDATE_ERROR)
    public Result<String> update(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                 @RequestBody DataDomain dataDomain) {
        return dataDomainService.createDataDomain(dataDomain, loginUser) > 0 ? Result.success() : Result.error(DATA_DOMAIN_CREATE_ERROR);
    }


    /**
     * select data domain tree data
     *
     * @return {@link  Result}
     */
    @Operation(summary = "数据域树节点", description = "获取数据域树节点")
    @PostMapping(value = "/treeData")
    @ApiException(DATA_DOMAIN_UPDATE_ERROR)
    public Result<List<TreeNode<DataDomain, Integer>>> treeData(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser) {
        return Result.success(dataDomainService.treeDataGenerate(loginUser));
    }

}
