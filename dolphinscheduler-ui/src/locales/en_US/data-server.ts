export default {
    dev: {
        createServer: 'create server',
        editServer: 'edit server',
        deleteServer: 'delete server',
        apiName: 'api name',
        apiType: 'api type',
        apiPath: 'api path',
        apiSecretKey: 'secretKey',
        apiStatus: 'api status',
        apiAuthor: 'api author',
        apiCreateTime: 'create time',
        apiUpdateTime: 'update time',
        apiDesc: 'api desc',
        apiLevel: 'level',
        apiDomain: 'domain',
        actions: 'actions'
    },
    apiType: {
        put: 'PUT',
        get: 'GET',
        post: 'POST',
        delete: 'DELETE',
        download: 'DOWNLOAD',
        upload: 'UPLOAD'
    },
    apiStatus: {
        dev: 'dev',
        maintenance: 'maintenance',
        release: 'release',
        no_release: 'no_release'
    },
    monitor: {},
    agency: {},
    error: {
        api_name_npe: 'api name not support is null',
        api_path_npe: 'api path not support is null',
        api_domain_npe: 'api domain not support is null',
        api_type_npe: 'api type not support is null'
    }
}
