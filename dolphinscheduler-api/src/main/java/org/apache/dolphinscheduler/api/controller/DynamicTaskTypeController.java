/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.dolphinscheduler.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dolphinscheduler.api.base.controller.BaseController;
import org.apache.dolphinscheduler.api.configuration.DynamicTaskTypeConfiguration;
import org.apache.dolphinscheduler.api.dto.taskType.DynamicTaskInfo;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.exceptions.ApiException;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.constants.Constants;
import org.apache.dolphinscheduler.dao.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.apache.dolphinscheduler.api.enums.Status.LIST_TASK_TYPE_ERROR;

/**
 * dynamic task type controller
 */
@Tag(name = "DYNAMIC_TASK_TYPE")
@RestController
@RequestMapping("/dynamic")
public class DynamicTaskTypeController extends BaseController {

    @Resource
    private DynamicTaskTypeConfiguration dynamicTaskTypeConfiguration;

    /**
     * get dynamic task category list
     *
     * @param loginUser login user
     * @return dynamic task category list
     */
    @Operation(summary = "listTaskCategories", description = "LIST_TASK_TYPE_CATEGORIES")
    @GetMapping(value = "/taskCategories")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(LIST_TASK_TYPE_ERROR)
    public Result listDynamicTaskCategories(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser) {
        List<String> taskCategories = dynamicTaskTypeConfiguration.getTaskCategories();
        return success(Status.SUCCESS.getMsg(), taskCategories);
    }

    /**
     * get dynamic task category list
     *
     * @param loginUser login user
     * @return dynamic task category list
     */
    @Operation(summary = "listDynamicTaskTypes", description = "LIST_DYNAMIC_TASK_TYPES")
    @GetMapping(value = "/{taskCategory}/taskTypes")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(LIST_TASK_TYPE_ERROR)
    public Result listDynamicTaskTypes(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                       @PathVariable("taskCategory") String taskCategory) {
        List<DynamicTaskInfo> taskTypes = dynamicTaskTypeConfiguration.getTaskTypesByCategory(taskCategory);
        return success(Status.SUCCESS.getMsg(), taskTypes);
    }

}
