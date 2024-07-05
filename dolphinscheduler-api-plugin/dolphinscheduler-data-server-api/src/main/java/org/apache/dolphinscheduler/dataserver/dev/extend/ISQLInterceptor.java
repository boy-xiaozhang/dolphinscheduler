package org.apache.dolphinscheduler.dataserver.dev.extend;

public interface ISQLInterceptor {
    String before(String script);

    void after(String script);
}
