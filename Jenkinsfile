pipeline {
  agent any
  stages {
    stage('error') {
      parallel {
        stage('error') {
          steps {
            sh 'mvn clean install'
            error 'Build failed'
          }
        }

        stage('Start') {
          steps {
            echo 'OFFF WE GOOOOO'
          }
        }

      }
    }

  }
}