package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.MySQLDataSource;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.springframework.stereotype.Component;

/**
 * SQL  构造器
 */
@Component
public class MySQLDriver extends JdbcDriver {

    @Override
    public String getName() {
        return "MySQL";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/MySQL.png";
    }

    @Override
    public String getFormat() {
        return "jdbc:mysql://localhost:3306/test";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        return new MySQLDataSource(super.getDataSource(config));
    }
}
