package org.apache.dolphinscheduler.dataserver.dev.config;

import org.apache.dolphinscheduler.dataserver.dev.datasource.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 默认数据源管理器
 */
@Component
@Slf4j
public class DefaultDataSourceManager extends DataSourceManager {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    public void init() {
        Map<String,DataSourceDialect> dialectMap = new LinkedHashMap<>();
        dialectMap.put("mysql",new MySQLDataSource(transactionManager,true));
        super.setDialectMap(dialectMap);
    }
}
