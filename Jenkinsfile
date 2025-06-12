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
        stage ('Maven Test'){
            steps{
                script{
                    mvnTest()
                }
            }
        }
        stage ('Maven Build'){
            steps{
                script{
                    mvnBuild()
                }
            }
        }
        stage ('Static Code Analysis:Sonarqube'){
            steps{
                script{
                    def SonarqubecredentialsId = 'sonar-token'
                    staticCodeAnalysis(SonarqubecredentialsId)
                }
            }
        }
        stage ('Quality Gate status check: SonarQube'){
            steps{
                script{
                    def SonarqubecredentialsId = 'sonar-token'
                    qualityGateStatus(SonarqubecredentialsId)
                }
            }
        }
    }
}