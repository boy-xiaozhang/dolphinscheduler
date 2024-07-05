package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.Data;

/**
 * API组名修改入参
 */
@Data
public class RenameGroupReq {
    private String newGroupName;
    private String oldGroupName;
}
