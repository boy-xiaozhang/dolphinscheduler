package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.ElasticSearchDataSource;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.elasticsearch.xpack.sql.jdbc.EsDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * SQL  构造器
 */
@Component
public class ElasticSearchDriver extends JdbcDriver {

    @Override
    public String getName() {
        return "ElasticSearch";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/ElasticSearch.png";
    }

    @Override
    public String getFormat() {
        return "jdbc:es://localhost:9300/";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        Properties properties = new Properties();
        properties.putAll(config.getProperties());

        if (StringUtils.hasText(config.getUser())){
            properties.put("user",config.getUser());
        }
        if (StringUtils.hasText(config.getPassword())){
            properties.put("password",config.getPassword());
        }
        EsDataSource dataSource = new EsDataSource();
        String address = "jdbc:es://localhost:9200";
        dataSource.setUrl(address);
        dataSource.setProperties(properties);

        return new ElasticSearchDataSource(dataSource);
    }
}
