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

import {useI18n} from 'vue-i18n'
import {h, reactive, ref} from 'vue'
import {useAsyncState} from '@vueuse/core'
import {queryExecuteResultListPaging} from '@/service/modules/data-quality'
import {format} from 'date-fns'
import {
    DefaultTableWidth
} from '@/common/column-width-config'
import type {
    ResultListRes
} from '@/service/modules/data-quality/types'
import {parseTime} from '@/common/common'
import {useRouter} from 'vue-router'

export function useTable() {
    const {t} = useI18n()

    const router = useRouter()

    const variables = reactive({
        columns: [],
        tableWidth: DefaultTableWidth,
        pattern: ref(''),
        showIrrelevantNodes: ref(true),
        tableData: [],
        page: ref(1),
        pageSize: ref(10),
        ruleType: ref(null),
        state: ref(null),
        searchVal: ref(null),
        datePickerRange: ref(null),
        totalPage: ref(1),
        loadingRef: ref(false),
        statusRef: ref(0),
        row: {},
        showModalRef: ref(false)
    })

    const getTableData = (params: any) => {
        if (variables.loadingRef) return
        variables.loadingRef = true
        const data = {
            pageSize: params.pageSize,
            pageNo: params.pageNo,
            ruleType: params.ruleType,
            state: params.state,
            searchVal: params.searchVal,
            startDate: params.datePickerRange
                ? format(parseTime(params.datePickerRange[0]), 'yyyy-MM-dd HH:mm:ss')
                : '',
            endDate: params.datePickerRange
                ? format(parseTime(params.datePickerRange[1]), 'yyyy-MM-dd HH:mm:ss')
                : ''
        }

        const {state} = useAsyncState(
            queryExecuteResultListPaging(data).then((res: ResultListRes) => {
                variables.totalPage = res.totalPage
                variables.tableData = res.totalList.map((item, unused) => {
                    return {
                        ...item
                    }
                }) as any

                variables.loadingRef = false
            }),
            {}
        )

        return state
    }

    return {
        t,
        variables,
        getTableData
    }
}
