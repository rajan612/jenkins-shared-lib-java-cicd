def call(String credentialsId) {
    def ceTaskId = ''
    withSonarQubeEnv(credentialsId: credentialsId) {
        def output = sh(script: 'mvn clean verify sonar:sonar', returnStdout: true)
        def matcher = output =~ /ceTaskId=([a-z0-9\\-]+)/
        if (matcher) {
            ceTaskId = matcher[0][1]
            echo "Extracted ceTaskId: ${ceTaskId}"
        } else {
            error("Could not extract ceTaskId from Maven output.")
        }
    }

    sleep 10 // give SonarQube time to register

    def status = ''
    def analysisId = ''

    while (true) {
        def ceResponse = httpRequest(
            url: "http://54.227.45.93:9000/api/ce/task?id=${ceTaskId}",
            authentication: credentialsId
        )
        def ceJson = readJSON text: ceResponse.content
        status = ceJson.task.status
        analysisId = ceJson.task.analysisId
        echo "Current task status: ${status}"
        if (status != "PENDING" && status != "IN_PROGRESS") {
            break
        }
        sleep 5
    }

    def gateResponse = httpRequest(
        url: "http://54.227.45.93:9000/api/qualitygates/project_status?analysisId=${analysisId}",
        authentication: credentialsId
    )
    def gateJson = readJSON text: gateResponse.content
    def gateStatus = gateJson.projectStatus.status

    echo "Quality Gate Result: ${gateStatus}"
    if (gateStatus != "OK") {
        error("Quality Gate failed with status: ${gateStatus}")
    }
}
