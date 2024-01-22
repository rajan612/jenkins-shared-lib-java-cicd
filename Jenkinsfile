@Library('mySharedLibrary') _
pipeline{
    
    agent any
    parameters {
        choice(name: 'action', choices: 'Create\nDelete', description: 'Choose Create/Destroy') 
    }
    
        stages{
          
            stage ('git checkout'){
                when { expression { params.action == 'create'} }
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
             when { expression { params.action == 'create'} }
                steps{
                    script{
                    mvnIntegrationTest()
                    }
            }
        }
            stage ('Static Code Analysis'){
                steps{
                 when { expression { params.action == 'create'} }
                    script{
                        def SonarQubecredentialsId = 'Jenkinsnew'
                        staticCodeAnalysis(SonarQubecredentialsId)
                    }
            }
        }
            stage ('Quality Gate Status Check: SonarQube'){
             when { expression { params.action == 'create'} }
                steps{
                    script{
                        def SonarQubecredentialsId = 'Jenkinsnew'
                        qualityGateStatus(SonarQubecredentialsId)
                    }
            }
        }

}
}