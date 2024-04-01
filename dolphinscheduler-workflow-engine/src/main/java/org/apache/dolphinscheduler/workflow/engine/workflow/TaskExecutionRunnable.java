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

import org.apache.dolphinscheduler.workflow.engine.event.IEventRepository;

public class TaskExecutionRunnable implements ITaskExecutionRunnable {

    private final ITaskExecutionContext taskExecutionContext;

    private final ITaskExecutionRunnableDelegate taskExecutionRunnableDelegate;

    public TaskExecutionRunnable(ITaskExecutionContext taskExecutionContext,
                                 ITaskExecutionRunnableDelegate taskExecutionRunnableDelegate) {
        this.taskExecutionContext = taskExecutionContext;
        this.taskExecutionRunnableDelegate = taskExecutionRunnableDelegate;
    }

    @Override
    public void start() {
        taskExecutionRunnableDelegate.beforeStart();

        taskExecutionRunnableDelegate.afterStart();

    }

    @Override
    public void pause() {
        taskExecutionRunnableDelegate.beforePause();
        taskExecutionRunnableDelegate.afterPause();

    }

    @Override
    public void kill() {
        taskExecutionRunnableDelegate.beforeKill();
        taskExecutionRunnableDelegate.afterKill();

    }

    @Override
    public boolean isReadyToTrigger(String taskNodeName) {
        return false;
    }

    @Override
    public ITaskExecutionRunnableIdentify getIdentify() {
        return taskExecutionContext.getIdentify();
    }

    @Override
    public ITaskExecutionContext getTaskExecutionContext() {
        return taskExecutionContext;
    }

    @Override
    public IEventRepository getEventRepository() {
        return taskExecutionContext.getEventRepository();
    }
}
