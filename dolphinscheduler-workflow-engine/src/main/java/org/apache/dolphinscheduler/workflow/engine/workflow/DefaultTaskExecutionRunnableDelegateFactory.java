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

public class DefaultTaskExecutionRunnableDelegateFactory implements ITaskExecutionRunnableDelegateFactory {

    private static final DefaultTaskExecutionRunnableDelegateFactory INSTANCE =
            new DefaultTaskExecutionRunnableDelegateFactory();

    private DefaultTaskExecutionRunnableDelegateFactory() {
    }

    public static DefaultTaskExecutionRunnableDelegateFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public ITaskExecutionRunnableDelegate createTaskExecutionRunnable(ITaskIdentify taskIdentify,
                                                                      IWorkflowExecutionContext workflowExecutionContext) {
        return new DefaultTaskExecutionRunnableDelegate();
    }

    @Override
    public ITaskExecutionRunnableDelegate createFailoverTaskExecutionRunnable(ITaskExecutionRunnable taskExecutionRunnable,
                                                                              IWorkflowExecutionContext workflowExecutionContext) {
        return new DefaultTaskExecutionRunnableDelegate();
    }

    @Override
    public ITaskExecutionRunnableDelegate createRetryTaskExecutionRunnable(ITaskExecutionRunnable taskExecutionRunnable,
                                                                           IWorkflowExecutionContext workflowExecutionContext) {
        return new DefaultTaskExecutionRunnableDelegate();
    }
}
