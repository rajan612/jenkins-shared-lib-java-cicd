@Library('mySharedLibrary') _
pipeline{
    
    agent any
    parameters {
        choice(name: 'action', choices: 'Create\nDelete', description: 'Choose Create/Destroy') 
        string(name: 'ImageName', description: "Name of the docker build", defaultValue: 'javaapp')
        string(name: 'ImageTag', description: "Tag of the docker build", defaultValue: 'v1')
        string(name: 'AppName', description: "Name of the Application", defaultValue: 'springboot')
    }
    
        stages{
          
            stage ('git checkout'){
                when { expression { params.action == 'Create'} }
                steps{
                gitCheckout (
                    branch: "master",
                    url: "https://github.com/rajan612/jenkins-shared-lib-java-cicd.git"
                )
            }
        }
            stage ('Unit Test'){
                steps{
                    script{
                    mvnTest()
                    }
            }
        }
            stage ('Integration test Maven'){
             when { expression { params.action == 'Create'} }
                steps{
                    script{
                    mvnIntegrationTest()
                    }
            }
        }
            stage ('Static Code Analysis'){
             when { expression { params.action == 'Create'} }
                steps{             
                    script{
                        def SonarQubecredentialsId = 'Jenkinsnew'
                        staticCodeAnalysis(SonarQubecredentialsId)
                    }
            }
        }
            stage ('Quality Gate Status Check: SonarQube'){
             when { expression { params.action == 'Create'} }
                steps{

                    script{
                        def SonarQubecredentialsId = 'Jenkinsnew'
                        qualityGateStatus(SonarQubecredentialsId)
                    }
            }
        }
            stage ('Maven Build: maven'){
             when { expression { params.action == 'Create'} }
                steps{

                    script{
                        mvnBuild()

            }
            }
            }
            stage ('Docker Image Build'){
             when { expression { params.action == 'Create'} }
                steps{

                    script{
                        dockerBuild("${params.ImageName}","${params.ImageTag}","${params.AppName}")

            }
            }
            }
}
}