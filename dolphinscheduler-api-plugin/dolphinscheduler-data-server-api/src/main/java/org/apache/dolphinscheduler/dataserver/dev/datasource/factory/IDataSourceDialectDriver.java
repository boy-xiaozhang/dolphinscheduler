package org.apache.dolphinscheduler.dataserver.dev.datasource.factory;

import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;

import java.io.Serializable;

/**
 *
 */

public abstract class IDataSourceDialectDriver implements Serializable {
    public abstract String getName();
    public abstract String getIcon();
    public abstract String getFormat();
    public String getDriver(){
        return this.getClass().getName();
    }
    public abstract DataSourceDialect factory(DBConfig config) throws Exception;
}
