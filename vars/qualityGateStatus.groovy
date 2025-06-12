def call(credentialsId) {
    withSonarQubeEnv(credentialsId: credentialsId) {
        sh 'mvn clean verify sonar:sonar'
    }
    
    // Must follow sonar:sonar
    waitForQualityGate abortPipeline: true, credentialsId: credentialsId
}