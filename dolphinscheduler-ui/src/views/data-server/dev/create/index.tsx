/**
 * @Author Jegger
 * @Date 2024/4/20 19:20
 * @Version 1.0
 * @Description
 **/
import {defineComponent, ref, toRefs} from 'vue'
import {NButton, NButtonGroup, NInput, NSelect, NSpace, NTabs, NTabPane} from "naive-ui";
import Card from "@/components/card";
import Styles from './index.module.scss'
import {useCreate} from "@/views/data-server/dev/create/use-create";
import MonacoEditor from '@/components/monaco-editor-v2'
import {useThemeStore} from "@/store/theme/theme";


export default defineComponent({
    name: 'DataServerCreate',

    setup() {
        const {t, variables} = useCreate()
        const theme = useThemeStore()

        return {
            t,
            theme,
            ...toRefs(variables),
        }
    },
    render() {
        const {t, theme} = this

        return (
            <NSpace vertical>
                <Card title={this.apiName || t('data_server.dev.createServer')}>
                    {{
                        default: () => (
                            <NSpace vertical>
                                <NSpace vertical>
                                    <div class={Styles.formClass}>
                                        <NInput
                                            v-model:value={this.apiName}
                                            type="text"
                                            class={Styles.input_name}
                                            placeholder={t('data_server.dev.apiName')}
                                            clearable
                                        />
                                        <NInput
                                            v-model:value={this.apiDesc}
                                            type="text"
                                            class={Styles.input_desc}
                                            placeholder={t('data_server.dev.apiDesc')}
                                            clearable
                                        />
                                    </div>
                                    <div class={Styles.formClass}>
                                        <NSelect
                                            v-model:value={[this.apiDomain, 'value']}
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
                                            placeholder={t('common_global.data_domain')}
                                            class={Styles.input_select}
                                            clearable
                                        />
                                        <NSelect
                                            v-model:value={[this.apiDatasource, 'value']}
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
                                            placeholder={t('common_global.data_source')}
                                            class={Styles.input_select}
                                            clearable
                                        />
                                        <NSelect
                                            v-model:value={[this.apiType, 'value']}
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
                                            class={Styles.input_select}
                                            clearable
                                        />
                                        <NSelect
                                            v-model:value={[this.apiLevel, 'value']}
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
                                            placeholder={t('data_server.dev.apiLevel')}
                                            class={Styles.input_select}
                                            clearable
                                        />
                                        <NInput
                                            v-model:value={this.apiPath}
                                            type="text"
                                            class={Styles.input_path}
                                            placeholder={t('data_server.dev.apiPath')}
                                            clearable
                                        />
                                    </div>
                                </NSpace>
                                <div>
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
                                    v-model={[this.apiCode, 'value']}
                                    options={{
                                        language: 'sql',
                                        contextmenu: false // 禁用右键菜单
                                    }}
                                />
                                <NSpace vertical>
                                    <NTabs animated>
                                        <NTabPane name="chap1" tab="控制台">
                                            <div class={Styles.log_view}>
                                                log log log
                                            </div>
                                        </NTabPane>
                                        <NTabPane name="chap2" tab="参数管理">
                                            <div class={Styles.log_view}>
                                                lo123g lo123g lo123g
                                            </div>
                                        </NTabPane>
                                    </NTabs>
                                </NSpace>
                            </NSpace>
                        ),
                        'header-extra': () => (
                            <NButtonGroup size='small'>
                                <NSpace>
                                    <NButton type='primary' class='btn-create-directory'>
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
