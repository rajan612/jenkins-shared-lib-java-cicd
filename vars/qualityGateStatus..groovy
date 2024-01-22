def cell(credentialsId){
    waitForQualityGate abortPipeline: false, credentialsId: credentialsId
}

