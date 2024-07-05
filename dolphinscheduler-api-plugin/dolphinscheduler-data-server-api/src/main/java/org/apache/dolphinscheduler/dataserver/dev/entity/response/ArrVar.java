package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数组变量对象
 */
@Data
@AllArgsConstructor
public class ArrVar {
    private String varName;
    private Integer index;
}
