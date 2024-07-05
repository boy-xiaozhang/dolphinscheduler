package org.apache.dolphinscheduler.dataserver.dev.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 结果包装类
 */
@Data
@AllArgsConstructor
public class ResultWrapper {
    private String code;
    private String action;
    private String msg;
    private Object data;
}
