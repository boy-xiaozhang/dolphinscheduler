import {useI18n} from "vue-i18n";
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {calculateTableWidth, COLUMN_WIDTH_CONFIG, DefaultTableWidth} from "@/common/column-width-config";
import {KeyValue} from "@/views/data-server/components/dynamic-kv-list";

/**
 * @Author Jegger
 * @Date 2024/4/20 20:19
 * @Version 1.0
 * @Description
 **/

export function useCreate() {
    const {t} = useI18n()

    const router = useRouter()

    const status = reactive({
        saving: false,
        testing: false,
        loading: false
    })


    /**
     * 变量
     */
    const variables = reactive({
        columns: [],
        paramsData: [],
        tableWidth: DefaultTableWidth,
        headerData: [],
        apiFormRef: ref(),
        rules: {
            apiName: {
                required: true,
                message: t('data_server.error.api_name_npe')
            },
            apiPath: {
                required: true,
                message: t('data_server.error.api_path_npe')
            },
            apiType: {
                required: true,
                message: t('data_server.error.api_type_npe')
            },
            apiDomain: {
                required: true,
                message: t('data_server.error.api_domain_npe')
            },
            apiDatasource: {
                required: true,
                message: t('common_global.data_source_npe')
            }
        },
        model: {
            apiName: ref(null),
            apiType: ref(),
            apiPath: ref(null),
            apiDesc: ref(null),
            apiDomain: ref(),
            apiLevel: ref(),
            apiDatasource: ref(),
            apiCode: ref(null),
            apiLastResult: ref(null),
            apiParams: ref<KeyValue[]>([]),
            apiHeader: ref(null),
            apiBody: ref(null)
        }
    })
    /**
     * 重置表单参数
     */
    const reset = () => {
        variables.model.apiName = null;
        variables.model.apiType = null;
        variables.model.apiPath = null;
        variables.model.apiDesc = null;
        variables.model.apiDomain = null;
        variables.model.apiLevel = null;
        variables.model.apiDatasource = null;
        variables.model.apiCode = null;
        variables.model.apiLastResult = null;
        variables.model.apiParams = [];
        variables.model.apiHeader = null;
        variables.model.apiBody = null;
    }


    const paramsHeaderColumn = (variables: any) => {
        variables.columns = [
            {
                title: 'name',
                key: 'paramName',
                render: (row: any, index: number) => index + 1,
                ...COLUMN_WIDTH_CONFIG['index']
            },
            {
                title: 'value',
                key: 'paramValue',
                ...COLUMN_WIDTH_CONFIG['userName']
            },
            {
                title: t('common_global.desc'),
                key: 'paramDesc',
                ...COLUMN_WIDTH_CONFIG['userName']
            }
        ]
        if (variables.tableWidth) {
            variables.tableWidth = calculateTableWidth(variables.columns)
        }
    }

    const handleValidate = async (statusRef: number) => {
        await variables.apiFormRef.validate()

        if (variables.model.apiCode === null || variables.model.apiCode === undefined) {
            alert('请写接口代码')
        }

        await createOrUpdate(statusRef)

        console.log('数据发送完毕,清理Form....')

        reset();
    }

    const createOrUpdate = async (statusRef: number) => {

    }


    return {
        t,
        router,
        variables,
        handleValidate,
        paramsHeaderColumn
    }
}