package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.ClickHouseDataSource;
import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.springframework.stereotype.Component;

/**
 * SQL  构造器
 */
@Component
public class ClickHouseDriver extends JdbcDriver {

    @Override
    public String getName() {
        return "ClickHouse";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/ClickHouse.png";
    }

    @Override
    public String getFormat() {
        return "jdbc:clickhouse://localhost:8123";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        return new ClickHouseDataSource(super.getDataSource(config));
    }
}
