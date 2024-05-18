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

import {defineComponent} from 'vue'
import {NButton, NFormItem, NGi, NGrid, NInput, NList, NListItem, NSpace} from "naive-ui";
import Styles from "@/views/data-server/dev/create/index.module.scss";
import {useI18n} from "vue-i18n";

export interface KeyValue {
    name: string;
    value: string;
    desc?: string; // 可选属性
}

const props = {
    dataList: {
        type: Array<KeyValue>,
        required: true
    }
}

const DynamicKvList = defineComponent({
    name: 'DynamicKeyValueList',
    props,
    emits: ['updateKeyValueDataList'],
    setup(props, ctx) {
        const onUpdateKeyValueDataList = () => {
            ctx.emit('updateKeyValueDataList')
        }

        return {
            onUpdateKeyValueDataList
        }
    },
    render() {
        const {onUpdateKeyValueDataList, dataList} = this;

        const {t} = useI18n()

        return (
            <div style={{
                width: '100%'
            }}>
                <NButton style={{width: '99.3%'}} dashed onClick={this.onUpdateKeyValueDataList}>
                    添加参数
                </NButton>
                <NList clickable hoverable style={{marginTop: '3px'}}>
                    {this.dataList?.map((item, index) => (
                        <NListItem key={index}>
                            <NGrid x-gap="12" cols="3">
                                <NGi>
                                    <NInput
                                        v-model:value={item.name}
                                        type="text"
                                        placeholder={t('common_global.key_name')}
                                        clearable
                                    />
                                </NGi>
                                <NGi>
                                    <NInput
                                        v-model:value={item.value}
                                        type="text"
                                        class={Styles.input_path}
                                        placeholder={t('common_global.value_name')}
                                        clearable
                                    />
                                </NGi>
                                <NGi>
                                    <NInput
                                        v-model:value={item.desc}
                                        type="text"
                                        class={Styles.input_desc}
                                        placeholder={t('common_global.desc_name')}
                                        clearable
                                    />
                                </NGi>
                            </NGrid>
                        </NListItem>
                    ))}
                </NList>
            </div>
        )
    }
})

export default DynamicKvList
