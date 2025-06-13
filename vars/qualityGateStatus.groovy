def call(String credentialsId) {
    def ceTaskId = ''
    withSonarQubeEnv(credentialsId: credentialsId) {
def output = sh(script: 'mvn clean verify sonar:sonar', returnStdout: true)
echo "=== Maven Output ==="
echo output

// Try both known patterns for ceTaskId
def matcher = output =~ /More information at https?:\/\/.*\/task\?id=([a-z0-9\-]+)/

if (matcher?.find()) {
    ceTaskId = matcher[0][1]
    echo "✅ Extracted ceTaskId: ${ceTaskId}"
} else {
    error("❌ Could not extract ceTaskId from Maven output. Check if Sonar is running and reachable.")
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
