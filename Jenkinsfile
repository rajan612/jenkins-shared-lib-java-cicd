@Library('mySharedLibrary') _
pipeline{
    
    agent any
    parameters {
        choice[name: 'action', choices:  'Create\nDelete', Description: 'Choose Create/Destroy'] 
    }
    
        stages{
            when {expression { params.action == 'create'} }
            stage ('git checkout'){
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
        //     stage ('Integration test'){
        //      when {expression { params.action == 'create'} }
        //         steps{
        //             script{
        //             mvnIntegrationTest()
        //             }
        //     }
        // }
            stage ('Static Code Analysis'){
                steps{
                    script{
                        staticCodeAnalysis()
                    }
            }
        }
}
}