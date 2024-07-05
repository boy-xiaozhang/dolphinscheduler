package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptContext {

    /**
     * 执行脚本
     */
    private StringBuilder script;
    /**
     * 脚本参数
     */
    private Map<String,Object>[] params;

    /**
     * 执行脚本数据源
     */
    private DataSourceDialect dataSourceDialect;

}
