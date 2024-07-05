/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.api.controller.v2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dolphinscheduler.api.base.controller.BaseController;
import org.apache.dolphinscheduler.api.dto.taskInstance.TaskInstanceQueryRequest;
import org.apache.dolphinscheduler.api.dto.taskInstance.TaskInstanceSuccessResponse;
import org.apache.dolphinscheduler.api.exceptions.ApiException;
import org.apache.dolphinscheduler.api.service.TaskInstanceService;
import org.apache.dolphinscheduler.api.utils.PageInfo;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.constants.Constants;
import org.apache.dolphinscheduler.dao.entity.TaskInstance;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.plugin.task.api.enums.TaskExecutionStatus;
import org.apache.dolphinscheduler.plugin.task.api.utils.ParameterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.apache.dolphinscheduler.api.enums.Status.*;

/**
 * task instance controller
 */
@Tag(name = "TASK_INSTANCE_TAG")
@RestController
@RequestMapping("/v2/projects/{projectCode}/task-instances")
public class TaskInstanceV2Controller extends BaseController {

    @Autowired
    private TaskInstanceService taskInstanceService;

    /**
     * query task list paging
     *
     * @param loginUser            login user
     * @param projectCode          project code
     * @param taskInstanceQueryReq taskInstanceQueryReq
     * @return task list page
     */
    @Operation(summary = "queryTaskListPaging", description = "QUERY_TASK_INSTANCE_LIST_PAGING_NOTES")
    @Parameters({
            @Parameter(name = "processInstanceId", description = "PROCESS_INSTANCE_ID", schema = @Schema(implementation = int.class), example = "100"),
            @Parameter(name = "processInstanceName", description = "PROCESS_INSTANCE_NAME", schema = @Schema(implementation = String.class)),
            @Parameter(name = "searchVal", description = "SEARCH_VAL", schema = @Schema(implementation = String.class)),
            @Parameter(name = "taskName", description = "TASK_NAME", schema = @Schema(implementation = String.class)),
            @Parameter(name = "taskCode", description = "TASK_CODE", schema = @Schema(implementation = Long.class)),
            @Parameter(name = "executorName", description = "EXECUTOR_NAME", schema = @Schema(implementation = String.class)),
            @Parameter(name = "stateType", description = "EXECUTION_STATUS", schema = @Schema(implementation = TaskExecutionStatus.class)),
            @Parameter(name = "host", description = "HOST", schema = @Schema(implementation = String.class)),
            @Parameter(name = "startDate", description = "START_DATE", schema = @Schema(implementation = String.class)),
            @Parameter(name = "endDate", description = "END_DATE", schema = @Schema(implementation = String.class)),
            @Parameter(name = "taskExecuteType", description = "TASK_EXECUTE_TYPE", schema = @Schema(implementation = int.class), example = "STREAM"),
            @Parameter(name = "pageNo", description = "PAGE_NO", required = true, schema = @Schema(implementation = int.class), example = "1"),
            @Parameter(name = "pageSize", description = "PAGE_SIZE", required = true, schema = @Schema(implementation = int.class), example = "20"),
    })
    @GetMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiException(QUERY_TASK_LIST_PAGING_ERROR)
    public Result<PageInfo<TaskInstance>> queryTaskListPaging(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                                              @Parameter(name = "projectCode", description = "PROJECT_CODE", required = true) @PathVariable long projectCode,
                                                              TaskInstanceQueryRequest taskInstanceQueryReq) {
        checkPageParams(taskInstanceQueryReq.getPageNo(), taskInstanceQueryReq.getPageSize());

        String searchVal = ParameterUtils.handleEscapes(taskInstanceQueryReq.getSearchVal());
        return taskInstanceService.queryTaskListPaging(loginUser, projectCode,
                taskInstanceQueryReq.getProcessInstanceId(), taskInstanceQueryReq.getProcessInstanceName(),
                taskInstanceQueryReq.getProcessDefinitionName(),
                taskInstanceQueryReq.getTaskName(), taskInstanceQueryReq.getTaskCode(),
                taskInstanceQueryReq.getExecutorName(),
                taskInstanceQueryReq.getStartTime(), taskInstanceQueryReq.getEndTime(), searchVal,
                taskInstanceQueryReq.getStateType(), taskInstanceQueryReq.getHost(),
                taskInstanceQueryReq.getTaskExecuteType(), taskInstanceQueryReq.getPageNo(),
                taskInstanceQueryReq.getPageSize());
    }

    /**
     * task savepoint, for stream task
     *
     * @param loginUser login user
     * @param projectCode project code
     * @param id task instance id
     * @return the result code and msg
     */
    @Operation(summary = "savepoint", description = "TASK_SAVEPOINT")
    @Parameters({
            @Parameter(name = "id", description = "TASK_INSTANCE_ID", required = true, schema = @Schema(implementation = int.class, example = "12"))
    })
    @PostMapping(value = "/{id}/savepoint")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(TASK_SAVEPOINT_ERROR)
    public Result<Object> taskSavePoint(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                        @Parameter(name = "projectCode", description = "PROJECT_CODE", required = true) @PathVariable long projectCode,
                                        @PathVariable(value = "id") Integer id) {
        return taskInstanceService.taskSavePoint(loginUser, projectCode, id);
    }

    /**
     * task stop, for stream task
     *
     * @param loginUser login user
     * @param projectCode project code
     * @param id task instance id
     * @return the result code and msg
     */
    @Operation(summary = "stop", description = "TASK_INSTANCE_STOP")
    @Parameters({
            @Parameter(name = "id", description = "TASK_INSTANCE_ID", required = true, schema = @Schema(implementation = int.class, example = "12"))
    })
    @PostMapping(value = "/{id}/stop")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(TASK_STOP_ERROR)
    public Result<Object> stopTask(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                   @Parameter(name = "projectCode", description = "PROJECT_CODE", required = true) @PathVariable long projectCode,
                                   @PathVariable(value = "id") Integer id) {
        return taskInstanceService.stopTask(loginUser, projectCode, id);
    }

    /**
     * change one task instance's state from FAILURE to FORCED_SUCCESS
     *
     * @param loginUser   login user
     * @param projectCode project code
     * @param id          task instance id
     * @return the result code and msg
     */
    @Operation(summary = "force-success", description = "FORCE_TASK_SUCCESS")
    @Parameters({
            @Parameter(name = "id", description = "TASK_INSTANCE_ID", required = true, schema = @Schema(implementation = int.class), example = "12")
    })
    @PostMapping(value = "/{id}/force-success", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiException(FORCE_TASK_SUCCESS_ERROR)
    public TaskInstanceSuccessResponse forceTaskSuccess(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                                        @Parameter(name = "projectCode", description = "PROJECT_CODE", required = true) @PathVariable long projectCode,
                                                        @PathVariable(value = "id") Integer id) {
        Result result = taskInstanceService.forceTaskSuccess(loginUser, projectCode, id);
        return new TaskInstanceSuccessResponse(result);
    }

    /**
     * query taskInstance by taskInstanceCode
     *
     * @param loginUser   login user
     * @param projectCode project code
     * @param taskInstanceId  taskInstance Id
     * @return the result code and msg
     */
    @Operation(summary = "queryOneTaskInstance", description = "QUERY_ONE_TASK_INSTANCE")
    @Parameters({
            @Parameter(name = "taskInstanceId", description = "TASK_INSTANCE_ID", required = true, schema = @Schema(implementation = Long.class), example = "1234567890")
    })
    @PostMapping(value = "/{taskInstanceId}", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiException(QUERY_TASK_INSTANCE_ERROR)
    public TaskInstance queryTaskInstanceByCode(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                                @Parameter(name = "projectCode", description = "PROJECT_CODE", required = true) @PathVariable long projectCode,
                                                @PathVariable(value = "taskInstanceId") Long taskInstanceId) {
        TaskInstance taskInstance = taskInstanceService.queryTaskInstanceById(loginUser, projectCode, taskInstanceId);
        return taskInstance;
    }
}
