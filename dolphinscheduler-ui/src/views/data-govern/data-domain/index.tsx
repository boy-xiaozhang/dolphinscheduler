import {
    defineComponent,
    getCurrentInstance,
    onMounted,
    toRefs,
    watch
} from 'vue'
import {
    NSpace,
    NDatePicker,
    NButton,
    NIcon,
    NButtonGroup,
    NGrid,
    NGi, NInput, NTree, TreeOption
} from 'naive-ui'
import {SearchOutlined} from '@vicons/antd'
import {useI18n} from 'vue-i18n'
import {useTable} from '@/views/data-govern/data-domain/use-table'
import Card from '@/components/card'
import Search from '@/components/input-search'
import DataDomainModel from "./components/data-domain-model";

const TaskResult = defineComponent({
    name: 'data-domain',
    setup() {
        const {t, variables, getTableData} = useTable()

        const requestTableData = () => {
            getTableData({
                pageSize: variables.pageSize,
                pageNo: variables.page,
                ruleType: variables.ruleType,
                state: variables.state,
                searchVal: variables.searchVal,
                datePickerRange: variables.datePickerRange
            })
        }

        const treeData: TreeOption[] = [
            {
                label: '生产域',
                key: '0',
                children: [
                    {
                        label: '制造域',
                        key: '0-0',
                    },
                    {
                        label: '质量域',
                        key: '0-1',
                    },
                    {
                        label: '仓储物流域',
                        key: '0-2',
                    }
                ]
            }
        ]

        const onUpdatePageSize = () => {
            variables.page = 1
            requestTableData()
        }

        const onSearch = () => {
            variables.page = 1
            requestTableData()
        }

        const onCancelModal = () => {
            variables.showModalRef = false
        }

        const onConfirmModal = () => {
            variables.showModalRef = false
        }

        const onHandlerModelChange = () => {
            variables.showModalRef = true
        }


        const trim = getCurrentInstance()?.appContext.config.globalProperties.trim


        onMounted(() => {
            requestTableData()
        })

        watch(useI18n().locale, () => {
        })

        return {
            t,
            treeData,
            ...toRefs(variables),
            requestTableData,
            onUpdatePageSize,
            onCancelModal,
            onConfirmModal,
            onHandlerModelChange,
            onSearch,
            trim
        }
    },
    render() {
        const {
            t,
            requestTableData,
            treeData,
            onSearch,
            onConfirmModal,
            onCancelModal,
            onHandlerModelChange,
            loadingRef
        } = this

        return (
            <NSpace vertical>
                <Card style={{height: '50px'}}>
                    <NSpace justify='space-between'>
                        <NSpace>
                            <NButtonGroup size='small'>
                                <NButton type='primary' class='btn-create-directory'
                                         onClick={onHandlerModelChange}>
                                    {t('data_govern.data_domain.create')}
                                </NButton>
                            </NButtonGroup>
                        </NSpace>
                        <NSpace>
                            <Search
                                v-model:value={this.searchVal}
                                placeholder={t('data_govern.data_domain.domain_name')}
                                onSearch={onSearch}
                            />
                            <Search
                                v-model:value={this.searchVal}
                                placeholder={t('data_govern.data_domain.create_user')}
                                onSearch={onSearch}
                            />
                            <NDatePicker
                                v-model={[this.datePickerRange, 'value']}
                                type='datetimerange'
                                size='small'
                                start-placeholder={t('monitor.audit_log.start_time')}
                                end-placeholder={t('monitor.audit_log.end_time')}
                                clearable
                            />
                            <NButton size='small' type='primary' onClick={onSearch}>
                                <NIcon>
                                    <SearchOutlined/>
                                </NIcon>
                            </NButton>
                        </NSpace>
                    </NSpace>
                </Card>
                <Card style={{
                    height: 'calc(100vh - 129px)'
                }} title={t('menu.data_domain')}>
                    <NGrid cols={24}>
                        <NGi style={{paddingRight: '10px'}} span={3}>
                            <NInput placeholder="搜索" v-model:value={this.pattern}/>
                            <NTree
                                show-irrelevant-nodes={this.showIrrelevantNodes}
                                pattern={this.pattern}
                                data={treeData}
                                block-line
                            />
                        </NGi>
                        <NGi span={21}>
                            域资产详情
                        </NGi>
                    </NGrid>
                </Card>
                <DataDomainModel
                    showModalRef={this.showModalRef}
                    statusRef={this.statusRef}
                    row={this.row}
                    onCancelModal={onCancelModal}
                    onConfirmModal={onConfirmModal}
                />
            </NSpace>
        )
    }
})

export default TaskResult
