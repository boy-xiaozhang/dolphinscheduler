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

package org.apache.dolphinscheduler.workflow.engine.engine;

import org.apache.dolphinscheduler.workflow.engine.event.WorkflowOperationEvent;
import org.apache.dolphinscheduler.workflow.engine.exception.WorkflowExecuteRunnableNotFoundException;
import org.apache.dolphinscheduler.workflow.engine.workflow.IWorkflowExecutionContext;
import org.apache.dolphinscheduler.workflow.engine.workflow.IWorkflowExecutionRunnable;
import org.apache.dolphinscheduler.workflow.engine.workflow.IWorkflowExecutionRunnableIdentify;
import org.apache.dolphinscheduler.workflow.engine.workflow.IWorkflowExecutionRunnableRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkflowEngine implements IWorkflowEngine {

    private final IWorkflowExecutionRunnableRepository workflowExecuteRunnableRepository;

    private final IEventEngine eventEngine;

    public WorkflowEngine(IWorkflowExecutionRunnableRepository workflowExecuteRunnableRepository,
                          IEventEngine eventEngine) {
        this.workflowExecuteRunnableRepository = workflowExecuteRunnableRepository;
        this.eventEngine = eventEngine;
    }

    @Override
    public void start() {
        eventEngine.start();
    }

    @Override
    public void triggerWorkflow(IWorkflowExecutionRunnable workflowExecuteRunnable) {
        IWorkflowExecutionContext workflowExecutionContext = workflowExecuteRunnable.getWorkflowExecutionContext();
        IWorkflowExecutionRunnableIdentify workflowExecutionIdentify = workflowExecutionContext.getIdentify();
        log.info("Triggering WorkflowExecutionRunnable: {}", workflowExecutionIdentify);
        workflowExecuteRunnableRepository.storeWorkflowExecutionRunnable(workflowExecuteRunnable);
        workflowExecuteRunnable
                .storeEventToTail(WorkflowOperationEvent.triggerEvent(workflowExecutionIdentify));
    }

    @Override
    public void pauseWorkflow(IWorkflowExecutionRunnableIdentify workflowExecutionRunnableIdentify) {
        IWorkflowExecutionRunnable workflowExecuteRunnable =
                workflowExecuteRunnableRepository.getWorkflowExecutionRunnable(workflowExecutionRunnableIdentify);
        if (workflowExecuteRunnable == null) {
            throw new WorkflowExecuteRunnableNotFoundException(workflowExecutionRunnableIdentify);
        }
        log.info("Pausing WorkflowExecutionRunnable: {}",
                workflowExecuteRunnable.getWorkflowExecutionContext().getIdentify());
        workflowExecuteRunnable
                .storeEventToTail(WorkflowOperationEvent.pauseEvent(workflowExecuteRunnable.getIdentity()));
    }

    @Override
    public void killWorkflow(IWorkflowExecutionRunnableIdentify workflowExecutionRunnableIdentify) {
        IWorkflowExecutionRunnable workflowExecuteRunnable =
                workflowExecuteRunnableRepository.getWorkflowExecutionRunnable(workflowExecutionRunnableIdentify);
        if (workflowExecuteRunnable == null) {
            throw new WorkflowExecuteRunnableNotFoundException(workflowExecutionRunnableIdentify);
        }
        log.info("Killing WorkflowExecutionRunnable: {}",
                workflowExecuteRunnable.getWorkflowExecutionContext().getIdentify());
        workflowExecuteRunnable
                .storeEventToTail(WorkflowOperationEvent.killEvent(workflowExecuteRunnable.getIdentity()));
    }

    @Override
    public void finalizeWorkflow(IWorkflowExecutionRunnableIdentify workflowExecutionRunnableIdentify) {
        IWorkflowExecutionRunnable workflowExecutionRunnable =
                workflowExecuteRunnableRepository.getWorkflowExecutionRunnable(workflowExecutionRunnableIdentify);
        if (workflowExecutionRunnable == null) {
            return;
        }
        // todo: If the workflowExecutionRunnable is not finished, we cannot finalize it.
        log.info("Finalizing WorkflowExecutionRunnable: {}", workflowExecutionRunnable.getIdentity());
        workflowExecuteRunnableRepository.removeWorkflowExecutionRunnable(workflowExecutionRunnableIdentify);
    }

    @Override
    public void shutdown() {
        eventEngine.shutdown();
    }

}
