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

        stage ('Clean Workspace') {
            steps {
                script {
                    cleanWs()
                }
            }
        }

        stage('Maven Test') {
            steps {
                script {
                    mvnTest('sample')  // if pom.xml is inside "sample/" folder
                }
            }
        }

        stage('Maven Build') {
            steps {
                script {
                    mvnBuild('sample') // same here
                }
            }
        }

        stage('Static Code Analysis: SonarQube') {
            steps {
                script {
                    def SonarqubecredentialsId = 'sonar-token'
                    staticCodeAnalysis(SonarqubecredentialsId)
                }
            }
        }

        stage('Integration Test: SonarQube') {
            steps {
                script {
                    def SonarqubecredentialsId = 'sonar-token'
                    qualityGateStatus(SonarqubecredentialsId)
                }
            }
        }

        stage('Docker Image Build') {
            steps {
                script {
                    dockerBuild("${params.ImageName}", "${params.ImageTag}", "${params.AppName}")
                }
            }
        }
    }
}
