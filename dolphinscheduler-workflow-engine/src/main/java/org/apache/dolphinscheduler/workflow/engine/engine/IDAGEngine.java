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

import org.apache.dolphinscheduler.workflow.engine.dag.ITaskIdentify;
import org.apache.dolphinscheduler.workflow.engine.workflow.ITaskExecutionRunnableIdentify;
import org.apache.dolphinscheduler.workflow.engine.workflow.IWorkflowExecutionDAG;

/**
 * The IDAGEngine is responsible for triggering, killing, pausing, and finalizing task in {@link org.apache.dolphinscheduler.workflow.engine.dag.WorkflowDAG}.
 * <p>All DAG operation should directly use the method in IDAGEngine, new {@link IWorkflowExecutionDAG} should be triggered by new IDAGEngine.
 */
public interface IDAGEngine {

    /**
     * Start the DAGEngine, will trigger the start tasks.
     */
    void start();

    /**
     * Trigger the tasks which are post of the given task.
     * <P> If there are no task after the given taskNode, will try to finish the WorkflowExecutionRunnable(Send a task chain end event).
     *
     * @param taskIdentify the parent task identify
     */
    void triggerNextTasks(ITaskIdentify taskIdentify);

    /**
     * Trigger the given task.
     *
     * @param taskIdentify task name
     */
    void triggerTask(ITaskIdentify taskIdentify);

    /**
     * Failover the given task.
     *
     * @param taskInstanceId taskInstanceId
     */
    void failoverTask(ITaskExecutionRunnableIdentify taskInstanceId);

    /**
     * Retry the given task.
     *
     * @param taskInstanceId taskInstanceId
     */
    void retryTask(ITaskExecutionRunnableIdentify taskInstanceId);

    void pause();

    /**
     * Pause the given task.
     */
    void pauseTask(ITaskExecutionRunnableIdentify taskExecutionIdentify);

    void kill();

    /**
     * Kill the given task.
     */
    void killTask(ITaskExecutionRunnableIdentify taskExecutionIdentify);

}
