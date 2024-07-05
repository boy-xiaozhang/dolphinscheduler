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
  path: '/system',
  name: 'system',
  meta: { title: '系统设置' },
  redirect: { name: 'tenant-manage' },
  component: () => import('@/layouts/content'),
  children: [
    {
      path: '/system/tenant-manage',
      name: 'tenant-manage',
      component: components['system-tenant-manage'],
      meta: {
        title: '租户管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/user-manage',
      name: 'user-manage',
      component: components['system-user-manage'],
      meta: {
        title: '用户管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/data-source',
      name: 'datasource-list',
      component: components['datasource-list'],
      meta: {
        title: '数据源管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/alarm-group-manage',
      name: 'alarm-group-manage',
      component: components['system-alarm-group-manage'],
      meta: {
        title: '告警组管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/worker-group-manage',
      name: 'worker-group-manage',
      component: components['system-worker-group-manage'],
      meta: {
        title: 'Worker分组管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/yarn-queue-manage',
      name: 'yarn-queue-manage',
      component: components['system-yarn-queue-manage'],
      meta: {
        title: 'Yarn队列管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/environment-manage',
      name: 'environment-manage',
      component: components['system-environment-manage'],
      meta: {
        title: '环境管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/cluster-manage',
      name: 'cluster-manage',
      component: components['system-cluster-manage'],
      meta: {
        title: '集群管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/token-manage',
      name: 'token-manage',
      component: components['system-token-manage'],
      meta: {
        title: '令牌管理管理',
        activeMenu: 'system',
        showSide: true,
        auth: []
      }
    },
    {
      path: '/system/alarm-instance-manage',
      name: 'alarm-instance-manage',
      component: components['system-alarm-instance-manage'],
      meta: {
        title: '告警实例管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    },
    {
      path: '/system/k8s-namespace-manage',
      name: 'k8s-namespace-manage',
      component: components['system-k8s-namespace-manage'],
      meta: {
        title: 'K8S命名空间管理',
        activeMenu: 'system',
        showSide: true,
        auth: ['ADMIN_USER']
      }
    }
  ]
}
