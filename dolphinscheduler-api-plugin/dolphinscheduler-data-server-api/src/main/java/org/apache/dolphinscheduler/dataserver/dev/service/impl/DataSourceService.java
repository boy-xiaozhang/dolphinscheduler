package org.apache.dolphinscheduler.dataserver.dev.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dolphinscheduler.dataserver.dev.config.RocketApiProperties;
import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceDialect;
import org.apache.dolphinscheduler.dataserver.dev.datasource.DataSourceManager;
import org.apache.dolphinscheduler.dataserver.dev.datasource.factory.IDataSourceDialectDriver;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiConfig;
import org.apache.dolphinscheduler.dataserver.dev.entity.ConfigType;
import org.apache.dolphinscheduler.dataserver.dev.entity.DBConfig;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.NotifyEntity;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.NotifyEventType;
import org.apache.dolphinscheduler.dataserver.dev.entity.response.RefreshDB;
import org.apache.dolphinscheduler.dataserver.dev.extend.IClusterNotify;
import org.apache.dolphinscheduler.dataserver.dev.permission.constant.DataConstant;
import org.apache.dolphinscheduler.dataserver.dev.permission.datalist.DataListPermissionClient;
import org.apache.dolphinscheduler.dataserver.dev.utils.GenerateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataSourceService {

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private RocketApiProperties rocketApiProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConfigService configService;

    @Autowired
    @Lazy
    private IClusterNotify clusterNotify;

    @Autowired
    private DataListPermissionClient dataListPermissionClient;

    public List<DBConfig> getDBConfig(){
        List<ApiConfig> configList = dataSourceManager.getStoreApiDataSource().listByEntity(ApiConfig.builder().service(rocketApiProperties.getServiceName()).type(ConfigType.DB.name()).build());
        return configList.stream().map(item-> {
            try {
                DBConfig dbConfig = objectMapper.readValue(item.getConfigContext(),DBConfig.class);
                return dbConfig;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public DBConfig getDBConfigById(String id) throws IOException {
        ApiConfig apiConfig = configService.getConfigById(id);
        return objectMapper.readValue(apiConfig.getConfigContext(),DBConfig.class);
    }

    private DBConfig getDBConfigByName(String name) {
        return getDBConfig().stream().filter(item->item.getName().equals(name)).findFirst().orElse(null);
    }

    @Transactional
    public void deleteDBConfig(DBConfig dbConfig) throws IOException {

        dbConfig = getDBConfigById(dbConfig.getId());

        this.closeDBConfig(dbConfig);

        configService.removeConfigById(dbConfig.getId());

        //集群刷新
        RefreshDB refreshDB = RefreshDB.builder().oldDBName(dbConfig.getName()).build();
        clusterNotify.sendNotify(NotifyEntity.builder().eventType(NotifyEventType.RefreshDB).refreshDB(refreshDB).build());
        //删除后调用权限
        if(dataListPermissionClient.hasPermissionService()){
            dataListPermissionClient.deletePermission(DataConstant.ListDataType.datasource,dbConfig.getId());
        }
    }

    private void assertDBConfigName(String dbName,String dbId) {
        List<DBConfig> dbList = getDBConfig();
        for (DBConfig dbConfig : dbList){
            if (dbConfig.getName().equals(dbName) && !dbConfig.getId().equals(dbId)){
                throw new IllegalArgumentException("name:"+dbName+" already exist");
            }
        }
    }

    /**
     * 保存数据源
     * @param dbConfig
     */
    @Transactional
    public String saveDBConfig(DBConfig dbConfig) throws Exception {
        ApiConfig apiConfig = ApiConfig.builder()
                .service(rocketApiProperties.getServiceName())
                .type(ConfigType.DB.name())
                .build();

        assertDBConfigName(dbConfig.getName(),dbConfig.getId());

        DBConfig oldDBConfig = null;
        if (StringUtils.isEmpty(dbConfig.getId())) {
            dbConfig.setId(GenerateId.get().toHexString());
            apiConfig.setId(dbConfig.getId());
            apiConfig.setConfigContext(objectMapper.writeValueAsString(dbConfig));
            dataSourceManager.getStoreApiDataSource().saveEntity(apiConfig);

            //加载新连接
            loadDBConfig(dbConfig);
            //添加后调用权限
            if(dataListPermissionClient.hasPermissionService()){
                dataListPermissionClient.addPermission(DataConstant.ListDataType.datasource,dbConfig.getId());
            }
        } else {
            oldDBConfig = getDBConfigById(dbConfig.getId());

            //关闭历史连接
            closeDBConfig(oldDBConfig);

            //加载新连接
            loadDBConfig(dbConfig);

            apiConfig.setId(dbConfig.getId());
            apiConfig.setConfigContext(objectMapper.writeValueAsString(dbConfig));
            dataSourceManager.getStoreApiDataSource().updateEntityById(apiConfig);
        }



        //集群刷新
        RefreshDB refreshDB = RefreshDB.builder().newDBName(dbConfig.getName()).build();

        if (oldDBConfig != null){
            refreshDB.setOldDBName(oldDBConfig.getName());
        }

        clusterNotify.sendNotify(NotifyEntity.builder().eventType(NotifyEventType.RefreshDB).refreshDB(refreshDB).build());

        return dbConfig.getId();
    }

    public void testDBConfig(DBConfig config) throws Exception {
        IDataSourceDialectDriver factory = (IDataSourceDialectDriver)(Class.forName(config.getDriver()).newInstance());
        DataSourceDialect dialect = factory.factory(config);
        dialect.close();
    }

    /**
     * 动态数据源关闭
     * @param config
     */
    private void closeDBConfig(DBConfig config){
        DataSourceDialect dataSourceDialect = dataSourceManager.getDialectMap().remove(config.getName());
        if (dataSourceDialect == null){
            return;
        }
        dataSourceDialect.close();
    }

    /**
     * 动态数据源加载
     * @param config
     */
    public void loadDBConfig(DBConfig config) throws Exception{

        if (!config.isEnabled()){
            return;
        }

        IDataSourceDialectDriver factory = (IDataSourceDialectDriver)(Class.forName(config.getDriver()).newInstance());
        DataSourceDialect dialect = factory.factory(config);
        dialect.setDynamic(true);

        dataSourceManager.getDialectMap().put(config.getName(),dialect);

    }

    public void reLoadDBConfig(Boolean isStart) throws Exception {

        Set<String> oldDBList = dataSourceManager.getDialectMap().keySet();
        for (String oldDBName : oldDBList ){

            if (!dataSourceManager.getDialectMap().get(oldDBName).isDynamic()){
                continue;
            }

            closeDBConfig(DBConfig.builder().name(oldDBName).build());
            if (!isStart){
                RefreshDB refreshDB = RefreshDB.builder().oldDBName(oldDBName).build();
                clusterNotify.sendNotify(NotifyEntity.builder().eventType(NotifyEventType.RefreshDB).refreshDB(refreshDB).build());
            }
        }

        List<DBConfig> newDBList = getDBConfig();
        for (DBConfig dbConfig : newDBList){
            loadDBConfig(dbConfig);
            if (!isStart){
                RefreshDB refreshDB = RefreshDB.builder().newDBName(dbConfig.getName()).build();
                clusterNotify.sendNotify(NotifyEntity.builder().eventType(NotifyEventType.RefreshDB).refreshDB(refreshDB).build());
            }
        }
    }

    public void refreshDB(RefreshDB refreshDB) throws Exception {

        if (!StringUtils.isEmpty(refreshDB.getOldDBName())){
            closeDBConfig(DBConfig.builder().name(refreshDB.getOldDBName()).build());
        }

        if (!StringUtils.isEmpty(refreshDB.getNewDBName())){
            DBConfig newDBConfig = this.getDBConfigByName(refreshDB.getNewDBName());
            if (newDBConfig != null){
                loadDBConfig(newDBConfig);
            }
        }
    }


}
