import type {Component} from 'vue'
import utils from '@/utils'

// All TSX files under the views folder automatically generate mapping relationship
const modules = import.meta.glob('/src/views/**/**.tsx')
const components: { [key: string]: Component } = utils.mapping(modules)
//数据域,数据标准,数据质量,标签字典,指标术语,标签管理,数据建模,元数据,数据安全,
export default {
    path: '/data-govern',
    name: 'data-govern',
    meta: {title: '治理中心'},
    redirect: {name: 'data-govern-data-domain'},
    component: () => import('@/layouts/content'),
    children: [
        {
            path: '/data-govern/data-domain',
            name: 'data-govern-data-domain',
            component: components['data-govern-data-domain'],
            meta: {
                title: '数据治理-data-domain',
                activeMenu: 'data-govern',
                showSide: true,
                auth: []
            }
        },
        {
            path: '/data-govern/data-quality',
            name: 'data-govern-data-quality',
            component: components['data-govern-data-quality'],
            meta: {
                title: '数据治理-data-quality',
                activeMenu: 'data-govern',
                showSide: true,
                auth: []
            },
        },
        {
            path: '/data-govern/data-quality/task-result',
            name: 'task-result',
            component: components['data-govern-data-quality-task-result'],
            meta: {
                title: '数据质量-task-result',
                activeMenu: 'data-govern',
                showSide: true,
                auth: []
            }
        },
        {
            path: '/data-govern/data-quality/rule',
            name: 'data-quality-rule',
            component: components['data-govern-data-quality-rule'],
            meta: {
                title: '数据质量-rule',
                activeMenu: 'data-govern',
                showSide: true,
                auth: []
            }
        }
    ]
}
