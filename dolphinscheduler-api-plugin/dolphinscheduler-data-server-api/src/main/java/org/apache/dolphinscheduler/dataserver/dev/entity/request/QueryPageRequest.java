package org.apache.dolphinscheduler.dataserver.dev.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.dolphinscheduler.dao.entity.base.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryPageRequest extends BaseRequest {
    /**
     * 服务名称
     */
    private String serverName;
    /**
     * 接口请求方式
     */
    private Integer method;
    /**
     * 服务脚本类型
     */
    private String serverType;
    /**
     * 接口路径
     */
    private String fullPath;
}
