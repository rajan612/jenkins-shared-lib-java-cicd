def call(String credentialsId) {
    withSonarQubeEnv(credentialsId: credentialsId) {
        sh "mvn clean verify sonar:sonar"
    }

    sleep 10

    withCredentials([string(credentialsId: credentialsId, variable: 'SONAR_TOKEN')]) {
        def response = sh(
            script: """curl -s -H "Authorization: Bearer \$SONAR_TOKEN" \
              "http://54.227.45.93:9000/api/project_analyses/search?project=minikube-sample" """,
            returnStdout: true
        ).trim()

        def parsed = readJSON text: response
        if (!parsed.analyses || !parsed.analyses[0]?.key) {
            error "No analyses found for project minikube-sample"
        }
        def analysisId = parsed.analyses[0].key

        def gateResponse = sh(
            script: """curl -s -H "Authorization: Bearer \$SONAR_TOKEN" \
              "http://54.227.45.93:9000/api/qualitygates/project_status?analysisId=${analysisId}" """,
            returnStdout: true
        ).trim()

        def gateStatus = readJSON text: gateResponse

        echo "Sonar Quality Gate: ${gateStatus.projectStatus.status}"
        if (gateStatus.projectStatus.status != "OK") {
            error("❌ Quality Gate failed.")
        }
    }
}
