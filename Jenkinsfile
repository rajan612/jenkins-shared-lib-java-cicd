pipeline {
    
    agent any {
    
        stages {
    
            stage ('git checkout')

            steps{
                script{
                    git 'https://github.com/rajan612/jenkins-shared-lib-java-cicd.git'
                }
            }
        }
    }
}