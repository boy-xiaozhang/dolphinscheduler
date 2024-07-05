package org.apache.dolphinscheduler.dataserver.dev.entity;

import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiTable;
import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiUpdateField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API 配置对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiTable("api_config")
public class ApiConfig extends ApiEntity{

    /**
     * 配置可视化标识符
     */
    private String service;

    /**
     * 配置分类
     * @ConfigType
     */
    private String type;

    /**
     * 配置明细
     */
    @ApiUpdateField
    private String configContext;

}
