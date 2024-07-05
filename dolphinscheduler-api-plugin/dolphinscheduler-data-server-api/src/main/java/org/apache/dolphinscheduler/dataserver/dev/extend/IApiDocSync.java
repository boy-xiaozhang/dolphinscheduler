package org.apache.dolphinscheduler.dataserver.dev.extend;

import org.apache.dolphinscheduler.dataserver.dev.entity.response.DocsInfo;

/**
 * API信息接口同步，
 */
public interface IApiDocSync {
    public String sync(DocsInfo docsInfo);
}
