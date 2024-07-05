package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiExample;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocApi {
    private ApiInfo apiInfo;
    private ApiExample apiExample;
}
