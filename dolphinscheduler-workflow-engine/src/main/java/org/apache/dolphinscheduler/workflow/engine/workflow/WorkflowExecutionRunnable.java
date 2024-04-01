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

package org.apache.dolphinscheduler.workflow.engine.workflow;

import org.apache.dolphinscheduler.workflow.engine.engine.IDAGEngine;
import org.apache.dolphinscheduler.workflow.engine.event.IEventRepository;

import lombok.Getter;

@Getter
public class WorkflowExecutionRunnable implements IWorkflowExecutionRunnable {

    private final IWorkflowExecutionContext workflowExecutionContext;

    private final IDAGEngine dagEngine;

    private final IWorkflowExecutionRunnableDelegate workflowExecutionRunnableDelegate;

    private WorkflowExecutionRunnableStatus workflowExecutionRunnableStatus;

    public WorkflowExecutionRunnable(IWorkflowExecutionContext workflowExecutionContext,
                                     IDAGEngine dagEngine,
                                     IWorkflowExecutionRunnableDelegate workflowExecutionRunnableDelegate) {
        this.workflowExecutionContext = workflowExecutionContext;
        this.dagEngine = dagEngine;
        this.workflowExecutionRunnableDelegate = workflowExecutionRunnableDelegate;
        this.workflowExecutionRunnableStatus = WorkflowExecutionRunnableStatus.CREATED;
    }

    @Override
    public IWorkflowExecutionRunnableIdentify getIdentity() {
        return workflowExecutionContext.getIdentify();
    }

    public void start() {
        if (workflowExecutionRunnableStatus != WorkflowExecutionRunnableStatus.CREATED) {
            throw new UnsupportedOperationException("The current status is not CREATED, cannot start.");
        }
        workflowExecutionRunnableDelegate.beforeStart();
        // Set the current status to RUNNING
        workflowExecutionRunnableStatus = WorkflowExecutionRunnableStatus.RUNNING;
        dagEngine.start();
        workflowExecutionRunnableDelegate.afterStart();
    }

    @Override
    public void pause() {
        if (workflowExecutionRunnableStatus != WorkflowExecutionRunnableStatus.RUNNING) {
            throw new UnsupportedOperationException("The current status is not RUNNING, cannot pause.");
        }
        workflowExecutionRunnableDelegate.beforePause();
        // Set the current status to PAUSING
        workflowExecutionRunnableStatus = WorkflowExecutionRunnableStatus.PAUSING;
        dagEngine.pause();
        workflowExecutionRunnableDelegate.afterPause();
    }

    @Override
    public void kill() {
        if (workflowExecutionRunnableStatus != WorkflowExecutionRunnableStatus.RUNNING
                && workflowExecutionRunnableStatus != WorkflowExecutionRunnableStatus.PAUSING) {
            throw new UnsupportedOperationException("The current status is not RUNNING, cannot kill.");
        }
        workflowExecutionRunnableDelegate.beforeKill();
        // Set the current status to KILLING
        workflowExecutionRunnableStatus = WorkflowExecutionRunnableStatus.KILLING;
        dagEngine.kill();
        workflowExecutionRunnableDelegate.afterKill();
    }

    @Override
    public IEventRepository getEventRepository() {
        return workflowExecutionContext.getEventRepository();
    }

}
