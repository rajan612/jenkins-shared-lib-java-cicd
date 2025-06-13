def call(credentialsId) {
    withSonarQubeEnv('rvashisht-sonarqube') {
        sh 'mvn clean verify sonar:sonar'
    }
    
    // Must follow sonar:sonar
waitForQualityGate abortPipeline: true
}