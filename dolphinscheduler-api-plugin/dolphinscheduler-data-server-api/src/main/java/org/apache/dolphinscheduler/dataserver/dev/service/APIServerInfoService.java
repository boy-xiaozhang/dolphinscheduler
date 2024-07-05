package org.apache.dolphinscheduler.dataserver.dev.service;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.request.QueryPageRequest;
import org.apache.dolphinscheduler.dataserver.dev.entity.request.QuerySingleObjRequest;

import java.util.List;

public interface APIServerInfoService {
    /**
     * 查询数据服务接口信息列表
     *
     * @param queryPageRequest params,click {@link QueryPageRequest}
     * @return {@link List}
     */
    List<ApiInfo> queryAPIInfoPage(QueryPageRequest queryPageRequest);

    /**
     * 查询数据服务接口信息
     *
     * @param queryPageRequest params,click {@link QuerySingleObjRequest}
     * @return {@link List}
     */
    ApiInfo queryApiInfoByParams(QuerySingleObjRequest queryPageRequest);


}
