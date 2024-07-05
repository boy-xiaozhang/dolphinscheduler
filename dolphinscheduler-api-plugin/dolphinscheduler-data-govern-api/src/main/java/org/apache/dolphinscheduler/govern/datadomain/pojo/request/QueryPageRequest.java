package org.apache.dolphinscheduler.govern.datadomain.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.dolphinscheduler.dao.entity.base.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryPageRequest extends BaseRequest {
    /**
     * 数据域中文名称
     */
    private String zhName;
    /**
     * 数据域负责人
     */
    private int wilfulness;
}
