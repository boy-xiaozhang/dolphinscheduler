package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.Data;


@Data
public class ExportReq {
    private String token;
    private String fileName;
    private String apiInfoIds;
}
