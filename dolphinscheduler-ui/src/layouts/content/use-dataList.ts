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

import {reactive, h} from 'vue'
import {NEllipsis, NIcon} from 'naive-ui'
import {useI18n} from 'vue-i18n'
import {
    HomeOutlined,
    ProfileOutlined,
    FolderOutlined,
    DatabaseOutlined,
    DesktopOutlined,
    SafetyCertificateOutlined,
    UserOutlined,
    LogoutOutlined,
    FundProjectionScreenOutlined,
    PartitionOutlined,
    SettingOutlined,
    FileSearchOutlined,
    RobotOutlined,
    AppstoreOutlined,
    UsergroupAddOutlined,
    UserAddOutlined,
    WarningOutlined,
    InfoCircleOutlined,
    ControlOutlined,
    SlackOutlined,
    EnvironmentOutlined,
    KeyOutlined,
    SafetyOutlined,
    GroupOutlined,
    ContainerOutlined,
    ApartmentOutlined,
    BarsOutlined,
    CloudServerOutlined,
    ClusterOutlined
} from '@vicons/antd'
import {useRoute} from 'vue-router'
import {useUserStore} from '@/store/user/user'
import {timezoneList} from '@/common/timezone'
import type {UserInfoRes} from '@/service/modules/users/types'

export function useDataList() {
    const {t} = useI18n()
    const route = useRoute()
    const userStore = useUserStore()

    const renderIcon = (icon: any) => {
        return () => h(NIcon, null, {default: () => h(icon)})
    }

    const localesOptions = [
        {
            label: 'English',
            key: 'en_US'
        },
        {
            label: '中文',
            key: 'zh_CN'
        }
    ]

    const timezoneOptions = () =>
        timezoneList.map((item) => ({label: item, value: item}))

    const state = reactive({
        isShowSide: false,
        localesOptions,
        timezoneOptions: timezoneOptions(),
        userDropdownOptions: [],
        menuOptions: [],
        headerMenuOptions: [],
        sideMenuOptions: []
    })

    const changeMenuOption = (state: any) => {
        const projectCode = route.params.projectCode || ''
        const projectName = route.query.projectName || ''
        state.menuOptions = [
            {
                label: () => h(NEllipsis, null, {default: () => t('menu.home')}),
                key: 'home',
                icon: renderIcon(HomeOutlined)
            },
            {
                label: () => h(NEllipsis, null, {default: () => t('menu.project')}),
                key: 'projects',
                icon: renderIcon(ProfileOutlined),
                children: [
                    {
                        label: t('menu.project') + (projectName ? `[${projectName}]` : ''),
                        key: `/projects/${projectCode}`,
                        icon: renderIcon(FundProjectionScreenOutlined),
                        payload: {projectName: projectName},
                        children: [
                            {
                                label: t('menu.project_overview'),
                                key: `/projects/${projectCode}`,
                                payload: {projectName: projectName}
                            },
                            {
                                label: t('menu.project_parameter'),
                                key: `/projects/${projectCode}/parameter`,
                                payload: {projectName: projectName}
                            },
                            {
                                label: t('menu.project_preferences'),
                                key: `/projects/${projectCode}/preferences`,
                                payload: {projectName: projectName}
                            }
                        ]
                    },
                    {
                        label: t('menu.workflow'),
                        key: 'workflow',
                        icon: renderIcon(PartitionOutlined),
                        children: [
                            {
                                label: t('menu.workflow_relation'),
                                key: `/projects/${projectCode}/workflow/relation`,
                                payload: {projectName: projectName}
                            },
                            {
                                label: t('menu.workflow_definition'),
                                key: `/projects/${projectCode}/workflow-definition`,
                                payload: {projectName: projectName}
                            },
                            {
                                label: t('menu.workflow_instance'),
                                key: `/projects/${projectCode}/workflow/instances`,
                                payload: {projectName: projectName}
                            },
                            {
                                label: t('menu.workflow_timing'),
                                key: `/projects/${projectCode}/workflow/timings`,
                                payload: {projectName: projectName}
                            }
                        ]
                    },
                    {
                        label: t('menu.task'),
                        key: 'task',
                        icon: renderIcon(SettingOutlined),
                        children: [
                            {
                                label: t('menu.task_definition'),
                                key: `/projects/${projectCode}/task/definitions`,
                                payload: {projectName: projectName}
                            },
                            {
                                label: t('menu.task_instance'),
                                key: `/projects/${projectCode}/task/instances`,
                                payload: {projectName: projectName}
                            }
                        ]
                    }
                ]
            },
            {
                label: () => h(NEllipsis, null, {default: () => t('menu.resources')}),
                key: 'resource',
                icon: renderIcon(FolderOutlined),
                children: [
                    {
                        label: t('menu.file_manage'),
                        key: '/resource/file-manage',
                        icon: renderIcon(FileSearchOutlined)
                    },
                    {
                        label: t('menu.udf_manage'),
                        key: 'udf-manage',
                        icon: renderIcon(RobotOutlined),
                        children: [
                            {
                                label: t('menu.resource_manage'),
                                key: '/resource/resource-manage'
                            },
                            {
                                label: t('menu.function_manage'),
                                key: '/resource/function-manage'
                            }
                        ]
                    },
                    {
                        label: t('menu.task_group_manage'),
                        key: 'task-group-manage',
                        icon: renderIcon(GroupOutlined),
                        children: [
                            {
                                label: t('menu.task_group_option'),
                                key: '/resource/task-group-option'
                            },
                            {
                                label: t('menu.task_group_queue'),
                                key: '/resource/task-group-queue'
                            }
                        ]
                    }
                ]
            },
            {
                label: () =>
                    h(NEllipsis, null, {default: () => t('menu.data_server')}),
                key: 'data-server',
                icon: renderIcon(ContainerOutlined),
                children: [
                    {
                        label: t('menu.data_server_dev'),
                        key: '/data-server/dev',
                        icon: renderIcon(ApartmentOutlined)
                    },
                    {
                        label: t('menu.data_server_agency'),
                        key: '/data-server/agency',
                        icon: renderIcon(BarsOutlined)
                    },
                    {
                        label: t('menu.data_server_monitor'),
                        key: '/data-server/monitor',
                        icon: renderIcon(BarsOutlined)
                    }
                ]
            },
            {
                label: () =>
                    h(NEllipsis, null, {default: () => t('menu.data_governance')}),
                key: 'data-govern',
                icon: renderIcon(DatabaseOutlined),
                children: [
                    {
                        label: t('menu.data_domain'),
                        key: '/data-govern/data-domain',
                        icon: renderIcon(ApartmentOutlined)
                    },
                    {
                        label: () =>
                            h(NEllipsis, null, {default: () => t('menu.data_quality')}),
                        key: '/data-govern/data-quality',
                        icon: renderIcon(ContainerOutlined),
                        children: [
                            {
                                label: t('menu.task_result'),
                                key: '/data-govern/data-quality/task-result',
                                icon: renderIcon(ApartmentOutlined)
                            },
                            {
                                label: t('menu.rule'),
                                key: '/data-govern/data-quality/rule',
                                icon: renderIcon(BarsOutlined)
                            }
                        ]
                    }
                ]
            },
            {
                label: () => h(NEllipsis, null, {default: () => t('menu.monitor')}),
                key: 'monitor',
                icon: renderIcon(DesktopOutlined),
                children: [
                    {
                        label: t('menu.service_manage'),
                        key: 'service-manage',
                        icon: renderIcon(AppstoreOutlined),
                        children: [
                            {
                                label: t('menu.master'),
                                key: '/monitor/master'
                            },
                            {
                                label: t('menu.worker'),
                                key: '/monitor/worker'
                            },
                            {
                                label: t('menu.db'),
                                key: '/monitor/db'
                            }
                        ]
                    },
                    {
                        label: t('menu.statistical_manage'),
                        key: 'statistical-manage',
                        icon: renderIcon(AppstoreOutlined),
                        children: [
                            {
                                label: t('menu.statistics'),
                                key: '/monitor/statistics'
                            },
                            {
                                label: t('menu.audit_log'),
                                key: '/monitor/audit-log'
                            }
                        ]
                    }
                ]
            },
            {
                label: () => h(NEllipsis, null, {default: () => t('menu.system')}),
                key: 'system',
                icon: renderIcon(SafetyCertificateOutlined),
                children: (userStore.getUserInfo as UserInfoRes).userType === 'ADMIN_USER' ? [
                    {
                        label: t('menu.tenant_manage'),
                        key: '/system/tenant-manage',
                        icon: renderIcon(UsergroupAddOutlined)
                    },
                    {
                        label: t('menu.user_manage'),
                        key: '/system/user-manage',
                        icon: renderIcon(UserAddOutlined)
                    },
                    {
                        label: t('menu.datasource'),
                        key: '/system/data-source',
                        icon: renderIcon(UserAddOutlined)
                    },
                    {
                        label: t('menu.alarm_group_manage'),
                        key: '/system/alarm-group-manage',
                        icon: renderIcon(WarningOutlined)
                    },
                    {
                        label: t('menu.alarm_instance_manage'),
                        key: '/system/alarm-instance-manage',
                        icon: renderIcon(InfoCircleOutlined)
                    },
                    {
                        label: t('menu.worker_group_manage'),
                        key: '/system/worker-group-manage',
                        icon: renderIcon(ControlOutlined)
                    },
                    {
                        label: t('menu.yarn_queue_manage'),
                        key: '/system/yarn-queue-manage',
                        icon: renderIcon(SlackOutlined)
                    },
                    {
                        label: t('menu.environment_manage'),
                        key: '/system/environment-manage',
                        icon: renderIcon(EnvironmentOutlined)
                    },
                    {
                        label: t('menu.cluster_manage'),
                        key: '/system/cluster-manage',
                        icon: renderIcon(ClusterOutlined)
                    },
                    {
                        label: t('menu.k8s_namespace_manage'),
                        key: '/system/k8s-namespace-manage',
                        icon: renderIcon(CloudServerOutlined)
                    },
                    {
                        label: t('menu.token_manage'),
                        key: '/system/token-manage',
                        icon: renderIcon(SafetyOutlined)
                    }
                ] : [{
                    label: t('menu.token_manage'),
                    key: '/system/token-manage',
                    icon: renderIcon(SafetyOutlined)
                }
                ]
            }
        ]
    }

    const changeHeaderMenuOptions = (state: any) => {
        state.headerMenuOptions = state.menuOptions.map(
            (item: { label: string; key: string; icon: any }) => {
                return {
                    label: item.label,
                    key: item.key,
                    icon: item.icon
                }
            }
        )
    }

    const changeUserDropdown = (state: any) => {
        state.userDropdownOptions = [
            {
                label: t('user_dropdown.profile'),
                key: 'profile',
                icon: renderIcon(UserOutlined)
            },
            {
                label: t('user_dropdown.password'),
                key: 'password',
                icon: renderIcon(KeyOutlined),
                disabled: userStore.getSecurityConfigType !== 'PASSWORD'
            },
            {
                label: t('user_dropdown.logout'),
                key: 'logout',
                icon: renderIcon(LogoutOutlined)
            }
        ]
    }

    return {
        state,
        changeHeaderMenuOptions,
        changeMenuOption,
        changeUserDropdown
    }
}
