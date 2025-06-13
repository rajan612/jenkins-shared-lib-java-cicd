def call(credentialsId) {
    withSonarQubeEnv(credentialsId: credentialsId) {
        withMaven(maven: 'your-maven-install-name') {
            sh 'mvn verify sonar:sonar'
        }
    }

    waitForQualityGate abortPipeline: true
}