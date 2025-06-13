@Library('jenkins-shared-library') _
pipeline {
    agent any
    parameters {
        String(Name: 'ImageName', Description: 'Name of the docker image', default: 'javaapp')
        String(Name: 'ImageTag', Description: 'Tag of the docker image', default: 'v1')
        String(Name: 'AppName', Description: 'App Name of the docker image', default: 'SpringBoot')
        

    }
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
        stage ('Integration Test: SonarQube'){
            steps{
                script{
                    def SonarqubecredentialsId = 'sonar-token'
                    qualityGateStatus(SonarqubecredentialsId)
                }
            }
        }
        stage ('Mvn Build: Maven'){
            steps{
                script{
                    mvnBuild()
                }
            }
        }
        stage ('Docker Image Build'){
            steps{
                script{
                    dockerBuild("${params.ImageName}", "${params.ImageTag}", "${params.AppName}")
                }
            }
        }
    }
}