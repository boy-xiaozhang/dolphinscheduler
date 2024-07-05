package org.apache.dolphinscheduler.govern.datadomain.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.dolphinscheduler.dao.entity.base.BaseEntity;

/**
 * 数据域
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dmc_data_domain")
public class DataDomain extends BaseEntity {

    /**
     * 主键ID。
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父节点代码。
     */
    private Integer parentCode;

    /**
     * 数据域中文名称。
     */
    private String zhName;

    /**
     * 数据域英文名称。
     */
    private String enName;

    /**
     * 英文缩写名称。
     */
    private String enAbridgeName;

    /**
     * 责任人，通常指数据域的负责人或维护者。
     */
    private Integer wilfulness;

    /**
     * 描述，用于解释数据域的作用和含义，默认为“域暂无描述”。
     */
    private String description;
}
