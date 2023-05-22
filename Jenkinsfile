pipeline {
  agent any
  stages {
    stage('Build Backend') {
      steps {
        sh 'mvn clean install -U'
        echo 'Building finished'
      }
    }

  }
}