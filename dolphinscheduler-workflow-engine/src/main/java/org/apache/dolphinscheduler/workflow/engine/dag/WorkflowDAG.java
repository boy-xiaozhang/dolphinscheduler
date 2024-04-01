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

package org.apache.dolphinscheduler.workflow.engine.dag;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The IWorkflowDAG represent the DAG of a workflow.
 */
public class WorkflowDAG implements DAG<ITask, ITaskIdentify> {

    private final Map<ITaskIdentify, ITask> dagNodeMap;

    private final Map<ITaskIdentify, Set<ITaskIdentify>> outdegreeMap;

    private final Map<ITaskIdentify, Set<ITaskIdentify>> inDegredMap;

    public WorkflowDAG(List<ITask> tasks,
                       List<ITaskChain> taskChains) {
        this.dagNodeMap = new HashMap<>();
        this.outdegreeMap = new HashMap<>();
        this.inDegredMap = new HashMap<>();

        for (ITask task : tasks) {
            ITaskIdentify identify = task.getIdentify();
            if (dagNodeMap.containsKey(identify)) {
                throw new IllegalArgumentException("Duplicate task identify: " + identify);
            }
            dagNodeMap.put(identify, task);
        }
        for (ITaskChain taskChain : taskChains) {
            ITask from = taskChain.getFrom();
            ITask to = taskChain.getTo();
            if (from == null) {
                continue;
            }
            if (to == null) {
                continue;
            }
            ITaskIdentify fromIdentify = from.getIdentify();
            ITaskIdentify toIdentify = to.getIdentify();
            Set<ITaskIdentify> outDegrees = outdegreeMap.computeIfAbsent(fromIdentify, k -> new HashSet<>());
            if (outDegrees.contains(toIdentify)) {
                throw new IllegalArgumentException("Duplicate task chain: " + fromIdentify + " -> " + toIdentify);
            }
            outDegrees.add(toIdentify);
            Set<ITaskIdentify> inDegrees = inDegredMap.computeIfAbsent(toIdentify, k -> new HashSet<>());
            if (inDegrees.contains(fromIdentify)) {
                throw new IllegalArgumentException("Duplicate task chain: " + fromIdentify + " -> " + toIdentify);
            }
            inDegrees.add(fromIdentify);
        }
    }

    @Override
    public List<ITask> getDirectPostNodes(ITask iTask) {
        if (iTask == null) {
            return getDirectPostNodesByIdentify(null);
        }
        return getDirectPostNodesByIdentify(iTask.getIdentify());
    }

    @Override
    public List<ITask> getDirectPostNodesByIdentify(ITaskIdentify taskIdentify) {
        if (taskIdentify == null) {
            return dagNodeMap.values()
                    .stream()
                    .filter(task -> !inDegredMap.containsKey(task.getIdentify()))
                    .collect(Collectors.toList());
        }
        return outdegreeMap.getOrDefault(taskIdentify, Collections.emptySet())
                .stream()
                .map(dagNodeMap::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<ITask> getDirectPreNodes(ITask iTask) {
        if (iTask == null) {
            return getDirectPreNodesByIdentify(null);
        }
        return getDirectPreNodesByIdentify(iTask.getIdentify());
    }

    @Override
    public List<ITask> getDirectPreNodesByIdentify(ITaskIdentify taskIdentify) {
        if (taskIdentify == null) {
            return dagNodeMap.values()
                    .stream()
                    .filter(task -> !outdegreeMap.containsKey(taskIdentify))
                    .collect(Collectors.toList());
        }
        return inDegredMap.getOrDefault(taskIdentify, Collections.emptySet())
                .stream()
                .map(dagNodeMap::get)
                .collect(Collectors.toList());
    }

    @Override
    public ITask getDAGNode(ITaskIdentify taskIdentify) {
        return dagNodeMap.get(taskIdentify);
    }
}
