package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiDirectory;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportRes {
    private Collection<ApiDirectory> directories;
    private Collection<ApiInfo> apiInfos;
}
