import {useI18n} from "vue-i18n";
import {useRouter} from "vue-router";
import {h, reactive, ref} from "vue";
import {calculateTableWidth, COLUMN_WIDTH_CONFIG, DefaultTableWidth} from "@/common/column-width-config";
import type {ResultItem, ResultListRes} from "@/service/modules/data-quality/types";
import ButtonLink from "@/components/button-link";
import {NEllipsis, NTag} from "naive-ui";
import {format} from "date-fns";
import {parseTime} from "@/common/common";
import {useAsyncState} from "@vueuse/core";
import {queryExecuteResultListPaging} from "@/service/modules/data-quality";

/**
 * @Author Jegger
 * @Date 2024/4/20 17:05
 * @Version 1.0
 * @Description
 **/
export function useTable() {
    const { t } = useI18n()
    const router = useRouter()

    const variables = reactive({
        columns: [],
        tableWidth: DefaultTableWidth,
        tableData: [],
        page: ref(1),
        pageSize: ref(10),
        ruleType: ref(null),
        state: ref(null),
        searchVal: ref(null),
        datePickerRange: ref(null),
        totalPage: ref(1),
        loadingRef: ref(false)
    })

    const createColumns = (variables: any) => {
        variables.columns = [
            {
                title: '序号',
                key: 'index',
                render: (row: any, index: number) => index + 1,
                ...COLUMN_WIDTH_CONFIG['index']
            },
            {
                title: t('data_server.dev.apiName'),
                key: 'apiName',
                ...COLUMN_WIDTH_CONFIG['userName']
            },
            {
                title: t('data_server.dev.apiType'),
                key: 'apiType',
                ...COLUMN_WIDTH_CONFIG['userName'],
                render: (row: ResultItem) =>
                    h(
                        ButtonLink,
                        {
                            onClick: () =>
                                void router.push({
                                    name: 'workflow-instance-detail',
                                    params: {
                                        projectCode: row.projectCode,
                                        id: row.processInstanceId
                                    },
                                    query: { code: row.processDefinitionCode }
                                })
                        },
                        {
                            default: () =>
                                h(
                                    NEllipsis,
                                    COLUMN_WIDTH_CONFIG['linkEllipsis'],
                                    () => row.processInstanceName
                                )
                        }
                    )
            },
            {
                title: t('data_server.dev.apiPath'),
                key: 'apiPath',
                ...COLUMN_WIDTH_CONFIG['note']
            },
            {
                title: t('data_server.dev.apiDesc'),
                key: 'apiDesc',
                ...COLUMN_WIDTH_CONFIG['note']
            },
            {
                title: t('data_server.dev.apiSecretKey'),
                key: 'apiSecretKey',
                width: 140
            },
            {
                title: t('data_server.dev.apiStatus'),
                key: 'apiStatus',
                ...COLUMN_WIDTH_CONFIG['type']
            },
            {
                title: t('data_server.dev.apiDomain'),
                key: 'apiDomain',
                ...COLUMN_WIDTH_CONFIG['type']
            },
            {
                title: t('data_server.dev.apiLevel'),
                key: 'apiLevel',
                width: 120
            },
            {
                title: t('data_server.dev.apiAuthor'),
                key: 'apiAuthor',
                ...COLUMN_WIDTH_CONFIG['userName']
            },
            {
                title: t('data_server.dev.apiCreateTime'),
                key: 'createTime',
                ...COLUMN_WIDTH_CONFIG['time']
            },
            {
                title: t('data_server.dev.actions'),
                key: 'actions',
                ...COLUMN_WIDTH_CONFIG['operation'](4),
            }
        ]
        if (variables.tableWidth) {
            variables.tableWidth = calculateTableWidth(variables.columns)
        }
    }

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

        const { state } = useAsyncState(
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
        getTableData,
        createColumns
    }
}