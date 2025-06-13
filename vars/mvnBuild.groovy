def call(String path = '.') {
    dir(path) {
        sh 'mvn clean install'
    }
}