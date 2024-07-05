package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOldApiInfo {
    private String apiInfoId;
    private ApiInfo newApiInfo;
    private ApiInfo oldApiInfo;
}
