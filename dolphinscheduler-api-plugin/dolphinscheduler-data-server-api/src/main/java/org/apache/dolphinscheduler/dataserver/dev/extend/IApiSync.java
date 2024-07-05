package org.apache.dolphinscheduler.dataserver.dev.extend;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;

import java.util.List;

/**
 * API信息接口同步，
 */
public interface IApiSync {
    public void sync(List<ApiInfo> apiInfos);
}
