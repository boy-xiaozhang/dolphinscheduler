package org.apache.dolphinscheduler.dataserver.dev.extend;

/**
 * 断言接口类
 */
public interface IAssertException {
    void exception(String throwMsg,String... express);
}
