package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.OracleDataSource;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.springframework.stereotype.Component;

@Component
public class OracleDriver extends JdbcDriver {

    @Override
    public String getName() {
        return "Oracle";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/Oracle.png";
    }

    @Override
    public String getFormat() {
        return "jdbc:oracle:thin:@localhost:1521/test";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        return new OracleDataSource(super.getDataSource(config));
    }
}
