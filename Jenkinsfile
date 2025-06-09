@Library('jenkins-shared-library') _
pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                script {
                    gitCheckout(
                        branch: "master",
                        url: "https://github.com/rajan612/jenkins-shared-lib-java-cicd.git"
                    )
                }
            }
        }
    }
}
