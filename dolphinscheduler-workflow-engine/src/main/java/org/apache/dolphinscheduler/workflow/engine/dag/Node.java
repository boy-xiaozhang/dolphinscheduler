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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The node of the DAG.
 * <p>
 * The node contains the node name, the content of the node, the inDegrees and the outDegrees.
 * The inDegrees is the edge from other nodes to the current node, the outDegrees is the edge from the current
 * node to other nodes.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private NodeIdentify nodeIdentify;

    private NodeContext nodeContext;

}
