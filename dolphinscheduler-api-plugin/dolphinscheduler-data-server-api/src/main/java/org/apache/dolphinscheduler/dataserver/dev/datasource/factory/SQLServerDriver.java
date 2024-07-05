package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;


import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.SQLServerDataSource;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.springframework.stereotype.Component;

/**
 * SQL  构造器
 */
@Component
public class SQLServerDriver extends JdbcDriver {

    @Override
    public String getName() {
        return "Microsoft SQL Server";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/SQLServer.png";
    }

    @Override
    public String getFormat() {
        return "jdbc:sqlserver://localhost:1433;database=test";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        return new SQLServerDataSource(super.getDataSource(config));
    }
}
