package org.apache.dolphinscheduler.dao.entity.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {
    /**
     * 项目Code。
     */
    private Long projectCode;
    /**
     * 安全等级。
     */
    private Long securityLevel;
    /**
     * 数据域ID
     */
    private Long dataDomainId;
    /**
     * 创建人的ID。
     */
    private Integer createAuthor;
    /**
     * 创建时间。
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间。
     */
    private LocalDateTime updateTime;
    /**
     * 修改人
     */
    private Integer updateAuthor;
}
