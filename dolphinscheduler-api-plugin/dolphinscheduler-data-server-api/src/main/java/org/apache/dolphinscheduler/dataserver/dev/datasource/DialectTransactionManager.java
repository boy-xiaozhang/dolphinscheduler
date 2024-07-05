package org.apache.dolphinscheduler.dataserver.dev.datasource;

import org.springframework.transaction.PlatformTransactionManager;

public interface DialectTransactionManager {
    public PlatformTransactionManager getTransactionManager();
}
