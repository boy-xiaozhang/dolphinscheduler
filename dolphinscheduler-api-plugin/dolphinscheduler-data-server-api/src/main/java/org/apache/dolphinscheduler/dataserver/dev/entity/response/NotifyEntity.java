package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotifyEntity {

    /**
     * 发送者实例ID
     */
    private String instanceId;

    /**
     * 事件类型
     */
    private NotifyEventType eventType;

    /**
     * eventType=@link NotifyEventType.UpdateMapping时存在
     */
    private RefreshMapping refreshMapping;

    /**
     * eventType=@link NotifyEventType.UpdateDB时存在
     */
    private RefreshDB refreshDB;
}
