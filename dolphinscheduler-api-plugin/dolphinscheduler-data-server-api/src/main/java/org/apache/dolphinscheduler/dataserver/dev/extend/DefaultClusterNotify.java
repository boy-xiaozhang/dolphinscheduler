package org.apache.dolphinscheduler.dataserver.dev.extend;

import org.apache.dolphinscheduler.dataserver.dev.config.QLRequestMappingFactory;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.NotifyEntity;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.NotifyEventType;
import org.apache.dolphinscheduler.dataserver.dev.service.impl.ApiInfoService;
import org.apache.dolphinscheduler.dataserver.dev.service.impl.ConfigService;
import org.apache.dolphinscheduler.dataserver.dev.service.impl.DataSourceService;
import org.apache.dolphinscheduler.dataserver.dev.utils.GenerateId;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 集群通知
 */
public class DefaultClusterNotify implements IClusterNotify {


    private String instanceId = GenerateId.get().toHexString();

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private QLRequestMappingFactory mappingFactory;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private ConfigService configService;

    /**
     * 发送系统缓存刷新的通知
     * 1. 在页面触发"Rebuild API List"操作时，会触发此方法
     * 2. 在页面触发接口编辑"Save"操作时，会触发此方法
     * 3. 动态数据源变更时，会触发此方法
     * 以达到分布式环境下多实例部署系统更新问题
     */
    @Override
    public void sendNotify(NotifyEntity notifyEntity) {
        notifyEntity.setInstanceId(instanceId);
    }

    /**
     * 监听 "@refreshNotify"行为，来重载本地request mapping等本地实体行为的重新初始化
     * @param notifyEntity
     */
    @Override
    public void receiveNotify(NotifyEntity notifyEntity) {
        //避免本实例重复初始化
        if (this.instanceId.equals(notifyEntity.getInstanceId())){
            return;
        }

        //重新初始化
        if (NotifyEventType.ReInit.equals(notifyEntity.getEventType())){
            try {
                mappingFactory.reInit(false);
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }

        //刷新单个接口
        if (NotifyEventType.RefreshMapping.equals(notifyEntity.getEventType())){
            try {
                apiInfoService.refreshMapping(notifyEntity.getRefreshMapping());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return;
        }

        //刷新数据源
        if (NotifyEventType.RefreshDB.equals(notifyEntity.getEventType())){
            try {
                dataSourceService.refreshDB(notifyEntity.getRefreshDB());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //刷新全局配置
        if (NotifyEventType.RefreshConfig.equals(notifyEntity.getEventType())){
            configService.refreshConfig();
        }
    }
}
