package org.apache.dolphinscheduler.dataserver.dev.entity;

import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiTable;
import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiUpdateField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiTable("api_directory")
public class ApiDirectory extends ApiEntity{

    @ApiUpdateField
    private String service;

    @ApiUpdateField
    private String name;

    @ApiUpdateField
    private String path;

    @ApiUpdateField
    private String parentId;
}
