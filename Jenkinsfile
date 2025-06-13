@Library('jenkins-shared-library') _
pipeline {
    agent any

    parameters {
        string(name: 'ImageName', defaultValue: 'javaapp', description: 'Docker image name')
        string(name: 'ImageTag', defaultValue: 'v1', description: 'Docker image tag')
        string(name: 'AppName', defaultValue: 'SpringBoot', description: 'App name')
    }

    stages {
        stage('Clean Workspace') {
            steps {
                script {
                    cleanWs() // standard Jenkins built-in
                }
            }
        }

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

        stage('Maven Test') {
            steps {
                script {
                    mvnTest()
                }
            }
        }

        stage('Maven Build') {
            steps {
                script {
                    mvnBuild()
                }
            }
        }

        stage('Static Code Analysis: SonarQube') {
            steps {
                script {
                    def SonarqubecredentialsId = 'sonarqube-token'
                    staticCodeAnalysis(SonarqubecredentialsId)
                }
            }
        }

        stage('Integration Test: SonarQube') {
            steps {
                script {
                    def SonarqubecredentialsId = 'sonarqube-token'
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
