package org.apache.dolphinscheduler.dataserver.dev.entity;

import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模拟参数实体对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiTable("api_example")
public class ApiExample extends ApiEntity{
    private String apiInfoId;
    private String url;
    private String method;
    private String requestHeader;
    private String requestBody;
    private String responseHeader;
    private String responseBody;
    private String status;
    private Integer elapsedTime;
    private String editor;
    private String options;
    private String createTime;
}
