package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.MongoDataSource;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.apache.dolphinscheduler.dataserver.dev.utils.MongoDBUtils;
import org.springframework.stereotype.Component;

/**
 * mongodb  构造器
 */
@Component
public class MongoDriver extends IDataSourceDialectDriver {

    @Override
    public String getName() {
        return "MongoDB";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/MongoDB.png";
    }

    @Override
    public String getFormat() {
        return "mongodb://localhost:27017/test";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) {
        return new MongoDataSource(MongoDBUtils.getMongoTemplate(config));
    }
}
