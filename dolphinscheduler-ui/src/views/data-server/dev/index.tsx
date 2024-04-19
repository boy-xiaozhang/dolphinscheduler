import {defineComponent} from "vue";
import {NButton, NButtonGroup, NSpace} from "naive-ui";
import Card from '@/components/card'
import {useI18n} from "vue-i18n";

const Dev = defineComponent({
    name: 'dev',
    render() {
        const {t} = useI18n()

        return (
            <NSpace vertical>
                <Card>
                    <NButtonGroup size='small'>
                        <NButton
                            type='primary'
                            class='btn-create-directory'
                        >
                            {t('data_server.dev.createServer')}
                        </NButton>
                    </NButtonGroup>
                </Card>
            </NSpace>
        )
    }
})

export default Dev