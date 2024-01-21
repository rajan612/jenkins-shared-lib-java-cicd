@Library('mySharedLibrary') _
pipeline{
    
    agent any
    
        stages{
    
            stage ('git checkout'){
                steps{
                gitCheckout {
                    branch: "master",
                    url: "https://github.com/rajan612/jenkins-shared-lib-java-cicd.git"
            }
            }
        }
}
}