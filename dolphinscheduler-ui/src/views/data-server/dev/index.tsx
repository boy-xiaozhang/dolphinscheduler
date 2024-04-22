import {defineComponent, getCurrentInstance, onMounted, toRefs, watch} from 'vue'
import {NButton, NButtonGroup, NDataTable, NDatePicker, NIcon, NPagination, NSelect, NSpace} from 'naive-ui'
import Card from '@/components/card'
import {useI18n} from 'vue-i18n'
import {useTable} from './use-table'
import Search from "@/components/input-search";
import {SearchOutlined} from "@vicons/antd";
import type {Router} from 'vue-router'
import {useRoute, useRouter} from "vue-router";


const Dev = defineComponent({
    name: 'dev',
    setup() {
        const {t, variables, getTableData, createColumns} = useTable()
        const router: Router = useRouter()
        const route = useRoute()
        //todo update dynamic project code.
        const projectCode = 12725389903008

        const requestTableData = () => {
            getTableData({
                pageSize: variables.pageSize,
                pageNo: variables.page,
                ruleType: variables.ruleType,
                state: variables.state,
                searchVa11l: variables.searchVal,
                datePickerRange: variables.datePickerRange
            })
        }

        const onUpdatePageSize = () => {
            variables.page = 1
            requestTableData()
        }

        const onSearch = () => {
            variables.page = 1
            requestTableData()
        }

        const trim = getCurrentInstance()?.appContext.config.globalProperties.trim

        onMounted(() => {
            createColumns(variables)
            requestTableData()
        })

        const createDefinition = () => {
            router.push({
                path: `/data-server/${projectCode}/dev/create`
            })
        }

        watch(useI18n().locale, () => {
            createColumns(variables)
        })

        return {
            t,
            ...toRefs(variables),
            requestTableData,
            createDefinition,
            onUpdatePageSize,
            onSearch,
            trim
        }
    },
    render() {
        const {t, requestTableData, onUpdatePageSize, onSearch, loadingRef} = this

        return (
            <NSpace vertical>
                <Card>
                    <NSpace justify='space-between'>
                        <NSpace>
                            <NButtonGroup size='small'>
                                <NButton type='primary' class='btn-create-directory' onClick={this.createDefinition}>
                                    {t('data_server.dev.createServer')}
                                </NButton>
                            </NButtonGroup>
                        </NSpace>
                        <NSpace>
                            <Search
                                v-model:value={this.searchVal}
                                placeholder={t('data_server.dev.apiName')}
                                onSearch={onSearch}
                            />
                            <NSelect
                                v-model={[this.ruleType, 'value']}
                                size='small'
                                options={[
                                    {
                                        value: 0,
                                        label: t('data_server.apiType.get')
                                    },
                                    {
                                        value: 1,
                                        label: t('data_server.apiType.post')
                                    },
                                    {
                                        value: 2,
                                        label: t('data_server.apiType.put')
                                    },
                                    {
                                        value: 3,
                                        label: t('data_server.apiType.delete')
                                    },
                                    {
                                        value: 4,
                                        label: t('data_server.apiType.download')
                                    },
                                    {
                                        value: 5,
                                        label: t('data_server.apiType.upload')
                                    }
                                ]}
                                placeholder={t('data_server.dev.apiType')}
                                style={{width: '180px'}}
                                clearable
                            />
                            <NSelect
                                v-model={[this.state, 'value']}
                                size='small'
                                options={[
                                    {
                                        value: 0,
                                        label: t('data_quality.task_result.undone')
                                    },
                                    {
                                        value: 1,
                                        label: t('data_quality.task_result.success')
                                    },
                                    {
                                        value: 2,
                                        label: t('data_quality.task_result.failure')
                                    }
                                ]}
                                placeholder={t('common_global.data_domain')}
                                style={{width: '180px'}}
                                clearable
                            />
                            <NSelect
                                v-model={[this.state, 'value']}
                                size='small'
                                options={[
                                    {
                                        value: 0,
                                        label: t('data_server.apiStatus.dev')
                                    },
                                    {
                                        value: 1,
                                        label: t('data_server.apiStatus.maintenance')
                                    },
                                    {
                                        value: 2,
                                        label: t('data_server.apiStatus.release')
                                    },
                                    {
                                        value: 3,
                                        label: t('data_server.apiStatus.no_release')
                                    }
                                ]}
                                placeholder={t('data_server.dev.apiStatus')}
                                style={{width: '180px'}}
                                clearable
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
                <Card title={t('menu.data_server')}>
                    <NSpace vertical>
                        <NDataTable
                            loading={loadingRef}
                            columns={this.columns}
                            data={this.tableData}
                            scrollX={this.tableWidth}
                        />
                        <NSpace justify='center'>
                            <NPagination
                                v-model:page={this.page}
                                v-model:page-size={this.pageSize}
                                page-count={this.totalPage}
                                show-size-picker
                                page-sizes={[10, 30, 50]}
                                show-quick-jumper
                                onUpdatePage={requestTableData}
                                onUpdatePageSize={onUpdatePageSize}
                            />
                        </NSpace>
                    </NSpace>
                </Card>
            </NSpace>
        )
    }
})

export default Dev
