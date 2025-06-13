def call(String credentialsId) {
    withSonarQubeEnv(credentialsId: credentialsId) {
        sh "mvn clean verify sonar:sonar"
    }

    sleep 10

    // Poll latest analysis by project key
    def response = httpRequest(
        url: "http://54.227.45.93:9000/api/project_analyses/search?project=minikube-sample",
        authentication: credentialsId
    )
    def parsed = readJSON text: response.content
    def analysisId = parsed.analyses[0].analysisKey

    def gateResponse = httpRequest(
        url: "http://54.227.45.93:9000/api/qualitygates/project_status?analysisId=${analysisId}",
        authentication: credentialsId
    )
    def gateStatus = readJSON text: gateResponse.content

    echo "Sonar Quality Gate: ${gateStatus.projectStatus.status}"
    if (gateStatus.projectStatus.status != "OK") {
        error("Quality Gate failed.")
    }
}
