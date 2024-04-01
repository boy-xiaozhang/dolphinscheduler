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

import org.apache.dolphinscheduler.workflow.engine.dag.DAG;
import org.apache.dolphinscheduler.workflow.engine.dag.ITaskIdentify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowExecutionDAG implements DAG<ITaskExecutionPlan, ITaskIdentify> {

    private final Map<ITaskIdentify, ITaskExecutionPlan> taskExecutionPlanMap;

    private final Map<ITaskIdentify, List<ITaskIdentify>> outdegreeMap;

    private final Map<ITaskIdentify, List<ITaskIdentify>> inDegredMap;

    public WorkflowExecutionDAG(List<ITaskExecutionPlan> tasks,
                                List<ITaskExecutionPlanChain> taskChains) {
        this.taskExecutionPlanMap = new HashMap<>();
        this.outdegreeMap = new HashMap<>();
        this.inDegredMap = new HashMap<>();

        for (ITaskExecutionPlan task : tasks) {
            ITaskIdentify identify = task.getTaskIdentify();
            if (taskExecutionPlanMap.containsKey(identify)) {
                throw new IllegalArgumentException("Duplicate task identify: " + identify);
            }
            taskExecutionPlanMap.put(identify, task);
        }
        for (ITaskExecutionPlanChain taskChain : taskChains) {
            ITaskExecutionPlan from = taskChain.getFrom();
            ITaskExecutionPlan to = taskChain.getTo();
            if (from == null) {
                continue;
            }
            if (to == null) {
                continue;
            }
            ITaskIdentify fromIdentify = from.getTaskIdentify();
            ITaskIdentify toIdentify = to.getTaskIdentify();
            List<ITaskIdentify> outDegrees =
                    outdegreeMap.computeIfAbsent(fromIdentify, k -> new ArrayList<>());
            if (outDegrees.contains(toIdentify)) {
                throw new IllegalArgumentException("Duplicate task chain: " + fromIdentify + " -> " + toIdentify);
            }
            outDegrees.add(toIdentify);
            List<ITaskIdentify> inDegrees =
                    inDegredMap.computeIfAbsent(toIdentify, k -> new ArrayList<>());
            if (inDegrees.contains(fromIdentify)) {
                throw new IllegalArgumentException("Duplicate task chain: " + fromIdentify + " -> " + toIdentify);
            }
            inDegrees.add(fromIdentify);
        }
    }

    @Override
    public List<ITaskExecutionPlan> getDirectPostNodes(ITaskExecutionPlan taskExecutionPlan) {
        if (taskExecutionPlan == null) {
            return getDirectPostNodesByIdentify(null);
        }
        return getDirectPostNodesByIdentify(taskExecutionPlan.getTaskIdentify());
    }

    @Override
    public List<ITaskExecutionPlan> getDirectPostNodesByIdentify(ITaskIdentify taskIdentify) {
        if (taskIdentify == null) {
            return taskExecutionPlanMap.values()
                    .stream()
                    .filter(task -> !inDegredMap.containsKey(task.getTaskIdentify()))
                    .collect(Collectors.toList());
        }
        return inDegredMap.getOrDefault(taskIdentify, Collections.emptyList())
                .stream()
                .map(taskExecutionPlanMap::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<ITaskExecutionPlan> getDirectPreNodes(ITaskExecutionPlan iTaskExecutionPlan) {
        if (iTaskExecutionPlan == null) {
            return getDirectPreNodesByIdentify(null);
        }
        return getDirectPreNodesByIdentify(iTaskExecutionPlan.getTaskIdentify());
    }

    @Override
    public List<ITaskExecutionPlan> getDirectPreNodesByIdentify(ITaskIdentify taskIdentify) {
        if (taskIdentify == null) {
            return taskExecutionPlanMap.values()
                    .stream()
                    .filter(task -> !outdegreeMap.containsKey(task.getTaskIdentify()))
                    .collect(Collectors.toList());
        }
        return outdegreeMap.getOrDefault(taskIdentify, Collections.emptyList())
                .stream()
                .map(taskExecutionPlanMap::get)
                .collect(Collectors.toList());
    }

    public List<ITaskExecutionPlan> getActiveTaskExecutionPlan() {
        return null;
    }

    @Override
    public ITaskExecutionPlan getDAGNode(ITaskIdentify taskIdentify) {
        return taskExecutionPlanMap.get(taskIdentify);
    }

}
