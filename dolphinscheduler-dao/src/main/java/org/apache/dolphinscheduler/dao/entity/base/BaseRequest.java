package org.apache.dolphinscheduler.dao.entity.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseRequest {
    /**
     * 当前页
     */
    private int pageNo;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 数据域ID
     */
    private Long dataDomainId;
    /**
     * 安全等级
     */
    private Integer securityLevel;
    /**
     * 项目Code
     */
    private Long projectCode;
    /**
     * 创建人
     */
    private int createAuthor;
    /**
     * 创建时间开始
     */
    private LocalDateTime createTimeStart;
    /**
     * 创建时间结束
     */
    private LocalDateTime createTimeEnd;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
