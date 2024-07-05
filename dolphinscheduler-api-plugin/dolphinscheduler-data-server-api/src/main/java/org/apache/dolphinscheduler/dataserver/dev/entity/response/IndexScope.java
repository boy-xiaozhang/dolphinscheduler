package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.Data;

/**
 * 位置范围
 */
@Data
public class IndexScope {
    private String token;
    private Integer beginIndex;
    private Integer endIndex;
}
