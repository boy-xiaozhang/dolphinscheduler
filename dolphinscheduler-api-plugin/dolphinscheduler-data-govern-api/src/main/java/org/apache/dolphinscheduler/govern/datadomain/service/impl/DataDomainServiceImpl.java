package org.apache.dolphinscheduler.govern.datadomain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dolphinscheduler.api.exceptions.ServiceException;
import org.apache.dolphinscheduler.api.service.ProjectService;
import org.apache.dolphinscheduler.api.base.service.impl.BaseServiceImpl;
import org.apache.dolphinscheduler.api.utils.PageInfo;
import org.apache.dolphinscheduler.api.utils.TreeNode;
import org.apache.dolphinscheduler.api.utils.TreeUtils;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.govern.datadomain.mapper.DataDomainMapper;
import org.apache.dolphinscheduler.govern.datadomain.pojo.entity.DataDomain;
import org.apache.dolphinscheduler.govern.datadomain.pojo.request.DeleteRequest;
import org.apache.dolphinscheduler.govern.datadomain.pojo.request.QueryPageRequest;
import org.apache.dolphinscheduler.govern.datadomain.service.DataDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static org.apache.dolphinscheduler.api.enums.Status.*;

@Service
public class DataDomainServiceImpl extends BaseServiceImpl implements DataDomainService {

    @Autowired
    private DataDomainMapper dataDomainMapper;

    @Autowired
    private ProjectService projectService;

    @Override
    public PageInfo<DataDomain> queryDataDomainPaging(QueryPageRequest request, User loginUser) {
        Page<DataDomain> page = new Page<>(request.getPageNo(), request.getPageSize());

        QueryWrapper<DataDomain> queryWrapper = new QueryWrapper<>();

        IPage<DataDomain> pageData = dataDomainMapper.selectPage(page, queryWrapper);

        PageInfo<DataDomain> pageInfo = new PageInfo<>(request.getPageNo(), request.getPageSize());
        pageInfo.setTotal((int) pageData.getTotal());
        pageInfo.setTotalList(pageData.getRecords());

        return pageInfo;
    }

    @Override
    public int deleteDataDomain(DeleteRequest request) {
        return dataDomainMapper.deleteBatchIds(Collections.singletonList(request.getIds()));
    }

    @Override
    public int createDataDomain(DataDomain domain, User loginUser) {
        //check project
        if (projectService.queryByCode(loginUser, domain.getProjectCode()).getCode() != 0) {
            throw new ServiceException(PROJECT_NOT_FOUND, domain.getProjectCode());
        }
        //todo add check user create permission

        if (ObjectUtils.isEmpty(domain.getParentCode())) {
            domain.setParentCode(0);
        }

        domain.setCreateAuthor(loginUser.getId());
        domain.setCreateTime(DateUtils.getCurrentDateTime());
        domain.setUpdateTime(DateUtils.getCurrentDateTime());
        return dataDomainMapper.insert(domain);
    }

    @Override
    public int updateDataDomain(DataDomain domain, User loginUser) {
        if (ObjectUtils.isEmpty(domain.getId())) {
            throw new ServiceException(DATA_DOMAIN_ID_NULL_ERROR, domain.getProjectCode());
        }

        DataDomain dataDomain = dataDomainMapper.selectById(domain.getId());

        if (ObjectUtils.isEmpty(dataDomain)) {
            throw new ServiceException(DATA_DOMAIN_NOT_FOUND_ERROR, domain.getProjectCode());
        }

        domain.setUpdateTime(DateUtils.getCurrentDateTime());

        return dataDomainMapper.updateById(domain);
    }

    @Override
    public List<TreeNode<DataDomain, Integer>> treeDataGenerate(User loginUser) {
        QueryWrapper<DataDomain> queryWrapper = new QueryWrapper<>();

        //todo check user permission

        List<DataDomain> dataDomains = dataDomainMapper.selectList(queryWrapper);

        TreeUtils<DataDomain, Integer> treeUtils = new TreeUtils<>();

        return treeUtils.buildTree(dataDomains, "id", "parentCode", 0);
    }

}
