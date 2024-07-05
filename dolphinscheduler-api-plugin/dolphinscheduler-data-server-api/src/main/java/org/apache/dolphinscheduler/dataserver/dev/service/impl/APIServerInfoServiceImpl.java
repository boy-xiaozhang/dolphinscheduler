package org.apache.dolphinscheduler.dataserver.dev.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.request.QueryPageRequest;
import org.apache.dolphinscheduler.dataserver.dev.entity.request.QuerySingleObjRequest;
import org.apache.dolphinscheduler.dataserver.dev.mapper.APIInfoMapper;
import org.apache.dolphinscheduler.dataserver.dev.service.APIServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class APIServerInfoServiceImpl implements APIServerInfoService {

    @Autowired
    private APIInfoMapper apiInfoMapper;

    @Override
    public List<ApiInfo> queryAPIInfoPage(QueryPageRequest requestParams) {
        QueryWrapper<ApiInfo> queryCondition = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(requestParams.getServerName())) {
            queryCondition.lambda().like(ApiInfo::getServerName, requestParams.getServerName());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getServerType())) {
            queryCondition.lambda().eq(ApiInfo::getServerType, requestParams.getServerType());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getDataDomainId())) {
            queryCondition.lambda().eq(ApiInfo::getDataDomainId, requestParams.getDataDomainId());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getSecurityLevel())) {
            queryCondition.lambda().eq(ApiInfo::getSecurityLevel, requestParams.getSecurityLevel());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getMethod())) {
            queryCondition.lambda().eq(ApiInfo::getMethod, requestParams.getMethod());
        }

        return apiInfoMapper.selectList(queryCondition).stream()
                .sorted(Comparator.comparing(ApiInfo::getServerName).thenComparing(ApiInfo::getFullPath))
                .collect(Collectors.toList());
    }

    @Override
    public ApiInfo queryApiInfoByParams(QuerySingleObjRequest requestParams) {
        QueryWrapper<ApiInfo> queryCondition = new QueryWrapper<>();

        if (ObjectUtils.isEmpty(requestParams.getServerId())) {
            queryCondition.lambda().eq(ApiInfo::getId, requestParams.getServerId());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getServerName())) {
            queryCondition.lambda().like(ApiInfo::getServerName, requestParams.getServerName());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getServerType())) {
            queryCondition.lambda().eq(ApiInfo::getServerType, requestParams.getServerType());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getDataDomainId())) {
            queryCondition.lambda().eq(ApiInfo::getDataDomainId, requestParams.getDataDomainId());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getSecurityLevel())) {
            queryCondition.lambda().eq(ApiInfo::getSecurityLevel, requestParams.getSecurityLevel());
        }

        if (ObjectUtils.isNotEmpty(requestParams.getMethod())) {
            queryCondition.lambda().eq(ApiInfo::getMethod, requestParams.getMethod());
        }

        return apiInfoMapper.selectOne(queryCondition);
    }


}
