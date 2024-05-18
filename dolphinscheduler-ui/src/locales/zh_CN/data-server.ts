export default {
    dev: {
        createServer: '创建数据服务',
        editServer: '编辑数据服务',
        deleteServer: '删除数据服务',
        apiName: '服务名称',
        apiType: '服务类型',
        apiPath: '服务地址',
        apiSecretKey: '服务令牌',
        apiStatus: '服务状态',
        apiAuthor: '负责人',
        apiCreateTime: '创建时间',
        apiUpdateTime: '修改时间',
        apiDesc: '服务描述',
        apiLevel: '接口保密等级',
        apiDomain: '数据域',
        actions: '操作'
    },
    apiType: {
        put: 'PUT',
        get: 'GET',
        post: 'POST',
        delete: 'DELETE',
        download: '下载',
        upload: '上传'
    },
    apiStatus: {
        dev: '开发中',
        maintenance: '维护',
        release: '已发布',
        no_release: '未发布'
    },
    monitor: {},
    agency: {},
    error: {
        api_name_npe: '接口名称不能为空',
        api_path_npe: '接口地址不能为空',
        api_domain_npe: '接口数据域不能为空',
        api_type_npe: '接口类型不能为空'
    }
}
