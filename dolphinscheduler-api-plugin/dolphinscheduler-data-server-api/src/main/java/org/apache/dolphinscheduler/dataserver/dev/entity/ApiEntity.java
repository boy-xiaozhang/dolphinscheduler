package org.apache.dolphinscheduler.dataserver.dev.entity;

import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiId;
import lombok.Data;

@Data
public abstract class ApiEntity {
    /**
     * 唯一标识符
     */
    @ApiId
    private String id;
}
