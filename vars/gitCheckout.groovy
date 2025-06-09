def call(Map params) {
    Checkout {
        [
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[
            url: params.repo,
            credentialsId: credentialsId
        ]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        submoduleCfg: []
    ])
        ]
    }

}