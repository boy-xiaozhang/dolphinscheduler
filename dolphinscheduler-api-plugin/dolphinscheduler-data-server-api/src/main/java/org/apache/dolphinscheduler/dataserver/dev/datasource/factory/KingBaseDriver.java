package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.KingBaseDataSource;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.springframework.stereotype.Component;

@Component
public class KingBaseDriver extends JdbcDriver{

    @Override
    public String getName() {
        return "KingBase";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/KingBase.png";
    }

    @Override
    public String getFormat() {
        return "jdbc:kingbase8://localhost:54321/testdb";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        return new KingBaseDataSource(super.getDataSource(config));
    }
}