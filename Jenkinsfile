@Library('jenkins-shared-library') _
pipeline {
    agent any
    parameters {
        string(name: 'ImageName', description: 'Name of the docker image', defaultValue: 'javaapp')
        string(name: 'ImageTag', description: 'Tag of the docker image', defaultValue: 'v1')
        string(name: 'AppName', description: 'App Name of the docker image', defaultValue: 'SpringBoot')
        

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
        // stage ('Integration Test: SonarQube'){
        //     steps{
        //         script{
        //             def SonarqubecredentialsId = 'sonar-token'
        //             qualityGateStatus(SonarqubecredentialsId)
        //         }
        //     }
        // }
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