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

import org.apache.dolphinscheduler.workflow.engine.dag.ITask;
import org.apache.dolphinscheduler.workflow.engine.dag.ITaskIdentify;

import java.util.List;

/**
 * The task execution plan interface which represents the task with its execution plan.
 * The task execution plan contains the task identify, task and the list of task execution runnables.
 */
public interface ITaskExecutionPlan {

    void start();

    void failoverTask(ITaskExecutionRunnableIdentify taskExecutionRunnableIdentify);

    void retryTask(ITaskExecutionRunnableIdentify taskExecutionRunnableIdentify);

    void pauseTask(ITaskExecutionRunnableIdentify taskExecutionIdentify);

    void killTask(ITaskExecutionRunnableIdentify taskExecutionIdentify);

    ITaskIdentify getTaskIdentify();

    ITaskExecutionRunnable getActiveTaskExecutionRunnable();

    ITask getTask();

    List<ITaskExecutionRunnable> getTaskExecutionRunnableList();

    ITaskExecutionRunnable getTaskExecutionRunnable(ITaskExecutionRunnableIdentify taskExecutionRunnableIdentify);

    ITaskExecutionRunnable storeTaskExecutionRunnable(ITaskExecutionRunnable taskExecutionRunnable);
}
