pipeline {
    agent { docker { image 'maven:3.5.3' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}