package org.apache.dolphinscheduler.dataserver.dev.utils;

import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiId;
import org.apache.dolphinscheduler.dataserver.dev.config.RocketApiProperties;
import org.apache.dolphinscheduler.dataserver.dev.config.SpringContextUtils;
import org.apache.dolphinscheduler.dataserver.dev.entity.*;
import org.springframework.util.StringUtils;

import java.util.Arrays;


public class ApiAnnotationUtil {
    public static String getApiTableName(Class clazz){
        RocketApiProperties rocketApiProperties = SpringContextUtils.getApplicationContext().getBean(RocketApiProperties.class);
        RocketApiProperties.ApiTableName apiTableName = rocketApiProperties.getTableName();
        String tableName = null;
        if (clazz.equals(ApiInfo.class)){
            tableName = apiTableName.getApi_info();
        }else if (clazz.equals(ApiConfig.class)){
            tableName = apiTableName.getApi_config();
        }else if (clazz.equals(ApiExample.class)){
            tableName = apiTableName.getApi_example();
        }else if (clazz.equals(ApiInfoHistory.class)){
            tableName = apiTableName.getApi_info_history();
        }else if (clazz.equals(ApiDirectory.class)){
            tableName = apiTableName.getApi_directory();
        }

        if (StringUtils.isEmpty(tableName))
            throw new IllegalArgumentException("not found tableName for class :`"+clazz.getClass()+"`");
        return tableName;
    }

    public static String getApiIdFieldName(Class clazz) {
        String fieldName = Arrays.asList(clazz.getSuperclass().getDeclaredFields()).stream()
                .filter(item->item.getAnnotation(ApiId.class) != null).findFirst()
                .orElseThrow(()-> new IllegalArgumentException("not found @ApiId Annotation")).getName();
        return fieldName;
    }
}
