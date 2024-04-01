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

package org.apache.dolphinscheduler.workflow.engine.event;

import org.apache.dolphinscheduler.workflow.engine.workflow.ITaskExecutionPlan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskOperationEventOperator implements ITaskEventOperator<TaskOperationEvent> {

    @Override
    public void handleEvent(TaskOperationEvent event) {
        ITaskExecutionPlan taskExecutionPlan = event.getTaskExecutionPlan();
        TaskOperationEventType eventType = (TaskOperationEventType) event.getEventType();
        switch (eventType) {
            case FAILOVER:
                taskExecutionPlan.failoverTask(taskExecutionPlan.getActiveTaskExecutionRunnable().getIdentify());
                break;
            case START:
                taskExecutionPlan.start();
                break;
            case RETRY:
                taskExecutionPlan.retryTask(taskExecutionPlan.getActiveTaskExecutionRunnable().getIdentify());
            case KILL:
                taskExecutionPlan.killTask(taskExecutionPlan.getActiveTaskExecutionRunnable().getIdentify());
                break;
            case PAUSE:
                taskExecutionPlan.pauseTask(taskExecutionPlan.getActiveTaskExecutionRunnable().getIdentify());
                break;
            default:
                log.error("Unknown TaskOperationType for event: {}", event);
        }
    }
}
