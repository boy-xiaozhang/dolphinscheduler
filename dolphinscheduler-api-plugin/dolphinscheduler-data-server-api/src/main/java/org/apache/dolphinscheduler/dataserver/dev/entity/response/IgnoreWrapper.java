package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 取消返回结构体的封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IgnoreWrapper {
    private Object data;
}
