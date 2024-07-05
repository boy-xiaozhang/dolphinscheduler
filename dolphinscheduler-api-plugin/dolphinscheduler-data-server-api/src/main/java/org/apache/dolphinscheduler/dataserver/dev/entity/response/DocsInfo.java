package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiDirectory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocsInfo {
    private List<ApiDirectory> directoryList;
    private List<DocApi> docApiList;
}
