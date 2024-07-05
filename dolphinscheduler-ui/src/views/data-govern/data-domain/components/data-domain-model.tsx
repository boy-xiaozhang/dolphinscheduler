import {
    defineComponent,
    getCurrentInstance,
    PropType,
    toRefs,
    watch
} from 'vue'
import Modal from '@/components/modal'
import {NForm, NFormItem, NInput, NSelect} from 'naive-ui'
import {useModal} from '@/views/data-govern/data-domain/components/use-modal'
import {useI18n} from 'vue-i18n'

const DataDomainModel = defineComponent({
    name: 'DataDomainModel',
    props: {
        showModalRef: {
            type: Boolean as PropType<boolean>,
            default: false
        },
        statusRef: {
            type: Number as PropType<number>,
            default: 0
        },
        row: {
            type: Object as PropType<any>,
            default: {}
        }
    },
    emits: ['cancelModal', 'confirmModal'],
    setup(props, ctx) {
        const {variables, handleValidate, getTreeData} = useModal(props, ctx)
        const {t} = useI18n()

        const cancelModal = () => {
            if (props.statusRef === 0) {
                variables.model.domainEnMark = ''
                variables.model.alertInstanceIds = []
                variables.model.description = ''
            }
            ctx.emit('cancelModal', props.showModalRef)
        }

        const confirmModal = () => {
            handleValidate(props.statusRef)
        }

        const trim = getCurrentInstance()?.appContext.config.globalProperties.trim

        watch(
            () => props.showModalRef,
            () => {
                props.showModalRef && getTreeData()
            }
        )

        watch(
            () => props.statusRef,
            () => {
                if (props.statusRef === 0) {
                    variables.model.groupName = ''
                    variables.model.alertInstanceIds = []
                    variables.model.description = ''
                } else {
                    variables.model.id = props.row.id
                    variables.model.groupName = props.row.groupName
                    variables.model.alertInstanceIds = props.row.alertInstanceIds
                        .split(',')
                        .map((item: string) => Number(item))
                    variables.model.description = props.row.description
                }
            }
        )

        watch(
            () => props.row,
            () => {
                variables.model.id = props.row.id
                variables.model.groupName = props.row.groupName
                variables.model.alertInstanceIds = props.row.alertInstanceIds
                    .split(',')
                    .map((item: string) => Number(item))
                variables.model.description = props.row.description
            }
        )

        return {t, ...toRefs(variables), cancelModal, confirmModal, trim}
    },
    render() {
        const {t} = this
        return (
            <div>
                <Modal
                    title={
                        this.statusRef === 0
                            ? t('data_govern.data_domain.create')
                            : t('data_govern.data_domain.edit')
                    }
                    show={this.showModalRef}
                    onCancel={this.cancelModal}
                    onConfirm={this.confirmModal}
                    confirmDisabled={
                        !this.model.domainEnMark || this.model.alertInstanceIds.length < 1
                    }
                    confirmLoading={this.saving}
                >
                    {{
                        default: () => (
                            <NForm
                                model={this.model}
                                rules={this.rules}
                                ref='dataDomainFormRef'
                            >
                                <NFormItem
                                    label={t('data_govern.data_domain.domain_en_mark')}
                                    path='domainName'
                                >
                                    <NInput
                                        allowInput={this.trim}
                                        placeholder={t(
                                            'data_govern.data_domain.domain_en_mark_tips'
                                        )}
                                        v-model={[this.model.domainEnMark, 'value']}
                                    />
                                </NFormItem>
                                <NFormItem
                                    label={t('data_govern.data_domain.domain_en_name')}
                                    path='domainName'
                                >
                                    <NInput
                                        allowInput={this.trim}
                                        placeholder={t(
                                            'data_govern.data_domain.domain_en_name_tips'
                                        )}
                                        v-model={[this.model.domainEnName, 'value']}
                                    />
                                </NFormItem>
                                <NFormItem
                                    label={t('data_govern.data_domain.domain_name')}
                                    path='domainName'
                                >
                                    <NInput
                                        allowInput={this.trim}
                                        placeholder={t(
                                            'data_govern.data_domain.domain_name_tips'
                                        )}
                                        v-model={[this.model.domainName, 'value']}
                                    />
                                </NFormItem>
                                <NFormItem
                                    label={t('data_govern.data_domain.domain_parent')}
                                    path='domainParentId'
                                >
                                    <NSelect
                                        placeholder={t('data_govern.data_domain.domain_parent_tips')}
                                        v-model={[this.model.domainParentId, 'value']}
                                    />
                                </NFormItem>
                                <NFormItem
                                    label={t('data_govern.data_domain.domain_person')}
                                    path='domainPerson'
                                >
                                    <NSelect
                                        placeholder={t(
                                            'data_govern.data_domain.domain_person_tips'
                                        )}
                                        v-model={[this.model.domainPerson, 'value']}
                                    />
                                </NFormItem>
                                <NFormItem
                                    label={t('data_govern.data_domain.domain_desc')}
                                    path='description'
                                >
                                    <NInput
                                        type='textarea'
                                        placeholder={t(
                                            'data_govern.data_domain.domain_desc_tips'
                                        )}
                                        v-model={[this.model.domainDesc, 'value']}
                                    />
                                </NFormItem>
                            </NForm>
                        )
                    }}
                </Modal>
            </div>
        )
    }
})

export default DataDomainModel
