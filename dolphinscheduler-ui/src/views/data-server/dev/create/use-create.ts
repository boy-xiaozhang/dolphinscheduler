import {useI18n} from "vue-i18n";
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";

/**
 * @Author Jegger
 * @Date 2024/4/20 20:19
 * @Version 1.0
 * @Description
 **/
export function useCreate() {
    const {t} = useI18n()

    const router = useRouter()

    const variables = reactive({
        apiName: ref(null),
        apiType: ref(),
        apiPath: ref(null),
        apiDesc: ref(null),
        apiDomain: ref(),
        apiLevel: ref(),
        apiDatasource: ref(),
        apiCode: ref(null),
        apiLastResult: ref(null)
    })


    return {
        t,
        router,
        variables
    }
}