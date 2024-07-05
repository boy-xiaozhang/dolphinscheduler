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

package org.apache.dolphinscheduler.server.worker.runner.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskInstanceOperationFunctionManager {

    @Autowired
    private TaskInstanceKillOperationFunctionWorker taskInstanceKillOperationFunction;

    @Autowired
    private UpdateWorkflowHostOperationFunctionWorker updateWorkflowHostOperationFunction;

    @Autowired
    private TaskInstanceDispatchOperationFunctionWorker taskInstanceDispatchOperationFunction;

    @Autowired
    private TaskInstancePauseOperationFunctionWorker taskInstancePauseOperationFunction;

    public TaskInstanceOperationFunctionManager(
                                                TaskInstanceKillOperationFunctionWorker taskInstanceKillOperationFunction,
                                                UpdateWorkflowHostOperationFunctionWorker updateWorkflowHostOperationFunction,
                                                TaskInstanceDispatchOperationFunctionWorker taskInstanceDispatchOperationFunction,
                                                TaskInstancePauseOperationFunctionWorker taskInstancePauseOperationFunction) {
        this.taskInstanceKillOperationFunction = taskInstanceKillOperationFunction;
        this.updateWorkflowHostOperationFunction = updateWorkflowHostOperationFunction;
        this.taskInstanceDispatchOperationFunction = taskInstanceDispatchOperationFunction;
        this.taskInstancePauseOperationFunction = taskInstancePauseOperationFunction;
    }

    public TaskInstanceKillOperationFunctionWorker getTaskInstanceKillOperationFunction() {
        return taskInstanceKillOperationFunction;
    }

    public UpdateWorkflowHostOperationFunctionWorker getUpdateWorkflowHostOperationFunction() {
        return updateWorkflowHostOperationFunction;
    }

    public TaskInstanceDispatchOperationFunctionWorker getTaskInstanceDispatchOperationFunction() {
        return taskInstanceDispatchOperationFunction;
    }

    public TaskInstancePauseOperationFunctionWorker getTaskInstancePauseOperationFunction() {
        return taskInstancePauseOperationFunction;
    }

}
