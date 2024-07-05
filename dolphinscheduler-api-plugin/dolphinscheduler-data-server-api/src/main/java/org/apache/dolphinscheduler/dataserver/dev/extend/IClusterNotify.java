package org.apache.dolphinscheduler.dataserver.dev.extend;

import org.apache.dolphinscheduler.dataserver.dev.entity.response.NotifyEntity;

/**
 * API信息缓存
 */
public interface IClusterNotify {
    public void sendNotify(NotifyEntity notifyEntity);
    public void receiveNotify(NotifyEntity notifyEntity);
}
