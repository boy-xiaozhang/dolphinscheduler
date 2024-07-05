package org.apache.dolphinscheduler.dataserver.dev.entity.response;

public enum NotifyEventType {
    ReInit, //重新初始化所有信息
    RefreshMapping, //更新mapping
    RefreshDB,       //刷新数据源
    RefreshConfig   //刷新配置
}
