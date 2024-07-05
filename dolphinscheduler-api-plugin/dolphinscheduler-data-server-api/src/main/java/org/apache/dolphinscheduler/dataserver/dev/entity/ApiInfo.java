package org.apache.dolphinscheduler.dataserver.dev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.dolphinscheduler.dao.entity.base.BaseEntity;
import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiTable;
import org.apache.dolphinscheduler.dataserver.dev.annotation.ApiUpdateField;

/**
 * API 实体对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dmc_data_domain")
public class ApiInfo extends BaseEntity {
    /**
     * 主键ID。
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 接口支持的方法
     */
    private String method;
    /**
     * 接口路径
     */
    private String path;
    /**
     * API模式，1,CODE.2,QL.3,AG，分别为代码模式，QL模式以及代理模式
     */
    private String serverType;
    /**
     * 接口名称
     */
    private String serverName;
    /**
     * 数据源
     */
    private Integer datasource;
    /**
     * SQL模式下的执行脚本
     */
    private String script;
    /**
     * 完整路径=directory_path+this_path
     */
    private String fullPath;
    /**
     * API参数
     */
    private String params;
    /**
     * API Headers
     */
    private String headers;
    /**
     * API Body
     */
    private String bodyJson;
    /**
     * 所属节点
     */
    @TableId(value = "nodeId")
    private String directoryId;

}
