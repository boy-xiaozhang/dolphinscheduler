package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.PostgreSQLDataSource;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.springframework.stereotype.Component;

/**
 * postgre SQL  构造器
 */
@Component
public class PostgreSQLDriver extends JdbcDriver {

    @Override
    public String getName() {
        return "PostgreSQL";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/PostgreSQL.png";
    }

    @Override
    public String getFormat() {
        return "jdbc:postgresql://localhost:5432/postgres";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        return new PostgreSQLDataSource(super.getDataSource(config));
    }
}
