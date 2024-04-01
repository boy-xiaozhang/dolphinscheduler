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

import org.apache.dolphinscheduler.workflow.engine.dag.ITaskIdentify;

public class TaskExecutionRunnableFactory implements ITaskExecutionRunnableFactory {

    private final ITaskExecutionContextFactory taskExecutionContextFactory;

    private ITaskExecutionRunnableDelegateFactory taskExecutionRunnableDelegateFactory =
            DefaultTaskExecutionRunnableDelegateFactory.getInstance();

    public TaskExecutionRunnableFactory(ITaskExecutionContextFactory taskExecutionContextFactory) {
        this.taskExecutionContextFactory = taskExecutionContextFactory;
    }

    public TaskExecutionRunnableFactory withTaskExecutionRunnableDelegateFactory(ITaskExecutionRunnableDelegateFactory taskExecutionRunnableDelegateFactory) {
        this.taskExecutionRunnableDelegateFactory = taskExecutionRunnableDelegateFactory;
        return this;
    }

    @Override
    public ITaskExecutionRunnable createTaskExecutionRunnable(ITaskIdentify taskIdentify,
                                                              IWorkflowExecutionContext workflowExecutionContext) {
        ITaskExecutionContext taskExecutionContext =
                taskExecutionContextFactory.createTaskExecutionContext(taskIdentify, workflowExecutionContext);
        ITaskExecutionRunnableDelegate taskExecutionRunnableDelegate = taskExecutionRunnableDelegateFactory
                .createTaskExecutionRunnable(taskIdentify, workflowExecutionContext);
        return new TaskExecutionRunnable(taskExecutionContext, taskExecutionRunnableDelegate);
    }

    @Override
    public ITaskExecutionRunnable createFailoverTaskExecutionRunnable(ITaskExecutionRunnable taskExecutionRunnable,
                                                                      IWorkflowExecutionContext workflowExecutionContext) {
        return null;
    }

    @Override
    public ITaskExecutionRunnable createRetryTaskExecutionRunnable(ITaskExecutionRunnable taskExecutionRunnable,
                                                                   IWorkflowExecutionContext workflowExecutionContext) {
        return null;
    }
}
