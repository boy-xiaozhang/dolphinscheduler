package org.apache.dolphinscheduler.govern.datadomain.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.dolphinscheduler.dao.entity.base.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteRequest extends BaseRequest {
    /**
     * 数据域ID列表
     */
    private int[] ids;
}
