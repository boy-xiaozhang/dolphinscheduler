package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.Data;

import java.util.List;

/**
 * 脚本在线运行返回实体
 */
@Data
public class RunApiRes {
    private List<String> logs = null;
    private Object data = null;
}
