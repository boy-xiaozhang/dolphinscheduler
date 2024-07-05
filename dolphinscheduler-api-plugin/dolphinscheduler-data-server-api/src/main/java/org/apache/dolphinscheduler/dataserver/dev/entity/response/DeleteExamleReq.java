package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiExample;
import lombok.Data;

import java.util.ArrayList;

/**
 * 删除模拟请求数据
 */
@Data
public class DeleteExamleReq {
    ArrayList<ApiExample> apiExampleList;
}
