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

import org.apache.dolphinscheduler.workflow.engine.workflow.IWorkflowExecutionRunnableIdentify;

public class WorkflowOperationEvent implements IWorkflowEvent, ISyncEvent {

    private final IWorkflowExecutionRunnableIdentify workflowExecutionRunnableIdentify;
    private final WorkflowOperationEventType workflowOperationEventType;

    private WorkflowOperationEvent(IWorkflowExecutionRunnableIdentify workflowExecutionRunnableIdentify,
                                   WorkflowOperationEventType workflowOperationEventType) {
        this.workflowExecutionRunnableIdentify = workflowExecutionRunnableIdentify;
        this.workflowOperationEventType = workflowOperationEventType;
    }

    public static WorkflowOperationEvent of(IWorkflowExecutionRunnableIdentify workflowExecutionIdentify,
                                            WorkflowOperationEventType workflowOperationEventType) {
        return new WorkflowOperationEvent(workflowExecutionIdentify, workflowOperationEventType);
    }

    public static WorkflowOperationEvent triggerEvent(IWorkflowExecutionRunnableIdentify workflowExecutionIdentify) {
        return of(workflowExecutionIdentify, WorkflowOperationEventType.TRIGGER);
    }

    public static WorkflowOperationEvent pauseEvent(IWorkflowExecutionRunnableIdentify workflowExecutionIdentify) {
        return of(workflowExecutionIdentify, WorkflowOperationEventType.PAUSE);
    }

    public static WorkflowOperationEvent killEvent(IWorkflowExecutionRunnableIdentify workflowExecutionIdentify) {
        return of(workflowExecutionIdentify, WorkflowOperationEventType.KILL);
    }

    @Override
    public IEventType getEventType() {
        return workflowOperationEventType;
    }

    @Override
    public Class getEventOperatorClass() {
        return WorkflowOperationEventOperator.class;
    }

    @Override
    public IWorkflowExecutionRunnableIdentify getWorkflowExecutionRunnableIdentify() {
        return workflowExecutionRunnableIdentify;
    }

}
