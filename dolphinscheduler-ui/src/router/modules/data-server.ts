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
import type { Component } from 'vue'
import utils from '@/utils'

// All TSX files under the views folder automatically generate mapping relationship
const modules = import.meta.glob('/src/views/**/**.tsx')
const components: { [key: string]: Component } = utils.mapping(modules)

export default {
    path: '/data-server',
    name: 'data-server',
    meta: { title: '数据服务' },
    redirect: { name: 'data-server-dev' },
    component: () => import('@/layouts/content'),
    children: [
        {
            path: '/data-server/dev',
            name: 'data-server-dev',
            component: components['data-server-dev'],
            meta: {
                title: '数据服务-dev',
                activeMenu: 'data-server',
                showSide: true,
                auth: []
            }
        },
        {
            path: '/data-server/agency',
            name: 'data-server-agency',
            component: components['data-server-agency'],
            meta: {
                title: '数据服务-agency',
                activeMenu: 'data-server',
                showSide: true,
                auth: []
            }
        },
        {
            path: '/data-server/monitor',
            name: 'data-server-monitor',
            component: components['data-server-monitor'],
            meta: {
                title: '数据服务-monitor',
                activeMenu: 'data-server',
                showSide: true,
                auth: []
            }
        },
        {
            path: '/data-server/:projectCode/dev/create',
            name: 'data-server-create',
            component: components['data-server-dev-create'],
            meta: {
                title: '创建数据服务',
                activeMenu: 'data-server',
                activeSide: '/data-server/:projectCode/dev',
                showSide: true,
                auth: []
            }
        },
    ]
}
