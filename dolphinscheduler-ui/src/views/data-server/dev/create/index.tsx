/**
 * @Author Jegger
 * @Date 2024/4/20 19:20
 * @Version 1.0
 * @Description
 **/
import {defineComponent, onMounted, PropType, ref, toRefs, watch} from 'vue'
import {
    NButton,
    NButtonGroup,
    NInput,
    NSelect,
    NSpace,
    NTabs,
    NTabPane,
    NForm,
    NFormItem,
    NGrid,
    NGi,
} from "naive-ui";
import Card from "@/components/card";
import Styles from './index.module.scss'
import {useCreate} from "@/views/data-server/dev/create/use-create";
import MonacoEditor from '@/components/monaco-editor-v2'
import {useThemeStore} from "@/store/theme/theme";
import {useI18n} from "vue-i18n";
import DynamicKvList from "@/views/data-server/components/dynamic-kv-list";


export default defineComponent({
    name: 'DataServerCreate',
    props: {
        //标识是否修改
        statusRef: {
            type: Number as PropType<number>,
            default: 0
        },
        //标识数据
        row: {
            type: Object as PropType<any>,
            default: {}
        }
    },

    emits: ['cancelModal', 'confirmModal'],

    setup() {
        const {t, variables, paramsHeaderColumn, handleValidate} = useCreate()
        const theme = useThemeStore()


        onMounted(() => {
            paramsHeaderColumn(variables)
        })

        const onSubmit = async () => {
            console.log('submit')
            handleValidate(0)
        }

        const onTestRun = () => {
            console.log('running code....')
        }

        const onParamsList = () => {
            variables.model.apiParams.push({
                desc: undefined, name: "", value: ""
            })
        }

        const formatterCode = () => {
            console.log('formatter code....')
        }

        watch(useI18n().locale, () => {
            paramsHeaderColumn(variables)
        })

        return {
            t,
            onSubmit,
            theme,
            paramsHeaderColumn,
            onParamsList,
            ...toRefs(variables),
        }
    },
    render() {
        const {t, theme, onParamsList} = this

        return (
            <NSpace vertical>
                <Card title={this.model.apiName || t('data_server.dev.createServer')}>
                    {{
                        default: () => (
                            <NSpace vertical>
                                <NForm
                                    model={this.model}
                                    rules={this.rules}
                                    ref='apiFormRef'
                                    label-placement="left"
                                    require-mark-placement='left'
                                    size='medium'
                                >
                                    <NSpace vertical>
                                        <NGrid x-gap="12" cols="3">
                                            <NGi>
                                                <NFormItem
                                                    path='apiName'
                                                >
                                                    <NInput
                                                        v-model:value={this.model.apiName}
                                                        type="text"
                                                        placeholder={t('data_server.dev.apiName') + ('-必填')}
                                                        clearable
                                                    />
                                                </NFormItem>
                                            </NGi>
                                            <NGi>
                                                <NFormItem
                                                    path='apiPath'
                                                    label-width='800'
                                                >
                                                    <NInput
                                                        v-model:value={this.model.apiPath}
                                                        type="text"
                                                        class={Styles.input_path}
                                                        placeholder={t('data_server.dev.apiPath') + ('-必填')}
                                                        clearable
                                                    />
                                                </NFormItem>
                                            </NGi>
                                            <NGi>
                                                <NFormItem
                                                    path='apiDesc'
                                                >
                                                    <NInput
                                                        v-model:value={this.model.apiDesc}
                                                        type="text"
                                                        class={Styles.input_desc}
                                                        placeholder={t('data_server.dev.apiDesc')}
                                                        clearable
                                                    />
                                                </NFormItem>
                                            </NGi>
                                        </NGrid>
                                        <NGrid x-gap="12" cols="4" style={{
                                            marginTop: '-5px'
                                        }}>
                                            <NGi>
                                                <NFormItem
                                                    path='apiDomain'
                                                >
                                                    <NSelect
                                                        v-model:value={[this.model.apiDomain, 'value']}
                                                        options={[]}
                                                        placeholder={t('common_global.data_domain') + ('-必填')}
                                                        class={Styles.input_select}
                                                        clearable
                                                    />
                                                </NFormItem>
                                            </NGi>
                                            <NGi>
                                                <NFormItem
                                                    path='apiDatasource'
                                                >
                                                    <NSelect
                                                        v-model:value={[this.model.apiDatasource, 'value']}
                                                        options={[]}
                                                        placeholder={t('common_global.data_source') + ('-必填')}
                                                        class={Styles.input_select}
                                                        clearable
                                                    />
                                                </NFormItem>
                                            </NGi>
                                            <NGi>
                                                <NFormItem
                                                    path='apiType'
                                                >
                                                    <NSelect
                                                        v-model:value={[this.model.apiType, 'value']}
                                                        options={[]}
                                                        placeholder={t('data_server.dev.apiType') + ('-必填')}
                                                        class={Styles.input_select}
                                                        clearable
                                                    />
                                                </NFormItem>
                                            </NGi>
                                            <NGi>
                                                <NFormItem
                                                    path='apiLevel'
                                                >
                                                    <NSelect
                                                        v-model:value={[this.model.apiLevel, 'value']}
                                                        options={[]}
                                                        placeholder={t('data_server.dev.apiLevel')}
                                                        class={Styles.input_select}
                                                        clearable
                                                    />
                                                </NFormItem>
                                            </NGi>
                                        </NGrid>
                                    </NSpace>
                                </NForm>
                                <div style={{
                                    marginTop: '-20px'
                                }}>
                                    <NSpace justify="end">
                                        <NButtonGroup size='small'>
                                            <NSpace>
                                                <NButton type='primary' class='btn-create-directory'>
                                                    {t('common_global.test_run')}
                                                </NButton>
                                                <NButton type='primary' class='btn-create-directory'>
                                                    {t('common_global.formatter_code')}
                                                </NButton>
                                            </NSpace>
                                        </NButtonGroup>
                                    </NSpace>
                                </div>
                                <MonacoEditor
                                    v-model={[this.model.apiCode, 'value']}
                                    options={{
                                        language: 'sql',
                                        contextmenu: false // 禁用右键菜单
                                    }}
                                />
                                <NSpace vertical>
                                    <NTabs animated>
                                        <NTabPane name="chap1" tab="控制台">
                                            <div class={Styles.log_view}>
                                            </div>
                                        </NTabPane>
                                        <NTabPane name="chap2" tab="参数管理">
                                            <div class={Styles.log_view}>
                                                <NTabs animated>
                                                    <NTabPane name="chap1" tab="Params">
                                                        <DynamicKvList onUpdateKeyValueDataList={this.onParamsList}
                                                                       dataList={this.model.apiParams}/>
                                                    </NTabPane>
                                                    <NTabPane name="chap2" tab="Header">
                                                        header
                                                    </NTabPane>
                                                    <NTabPane name="chap3" tab="Body">
                                                        body
                                                    </NTabPane>
                                                </NTabs>
                                            </div>
                                        </NTabPane>
                                    </NTabs>
                                </NSpace>
                            </NSpace>
                        ),
                        'header-extra': () => (
                            <NButtonGroup size='small'>
                                <NSpace>
                                    <NButton type='primary' class='btn-create-directory' onClick={this.onSubmit}>
                                        {t('common_global.save')}
                                    </NButton>
                                    <NButton type='primary' class='btn-create-directory'>
                                        {t('common_global.cancel')}
                                    </NButton>
                                </NSpace>
                            </NButtonGroup>
                        )
                    }}
                </Card>
            </NSpace>
        )
    }
})
