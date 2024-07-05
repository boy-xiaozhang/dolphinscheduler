package org.apache.dolphinscheduler.govern.datadomain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dolphinscheduler.api.utils.PageInfo;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.api.utils.TreeNode;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.govern.datadomain.pojo.entity.DataDomain;
import org.apache.dolphinscheduler.govern.datadomain.pojo.request.DeleteRequest;
import org.apache.dolphinscheduler.govern.datadomain.pojo.request.QueryPageRequest;

import java.util.List;

public interface DataDomainService {
    /**
     * 查询数据域分页
     *
     * @param request {@link QueryPageRequest}
     * @return {@link PageInfo}
     */
    PageInfo<DataDomain> queryDataDomainPaging(QueryPageRequest request, User loginUser);


    /**
     * 删除数据域基于选中的id列表
     *
     * @param request {@link DeleteRequest}
     * @return 删除的条数
     */
    int deleteDataDomain(DeleteRequest request);


    /**
     * 创建数据域
     *
     * @param domain {@link DataDomain}
     * @return 创建的条数
     */
    int createDataDomain(DataDomain domain, User loginUser);


    /**
     * 修改数据域
     *
     * @param domain {@link DataDomain}
     * @return 修改的条数
     */
    int updateDataDomain(DataDomain domain, User loginUser);


    List<TreeNode<DataDomain, Integer>> treeDataGenerate(User loginUser);
}
