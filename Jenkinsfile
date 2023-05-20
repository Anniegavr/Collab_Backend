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

        stage('clone') {
          steps {
            mail(subject: 'Build Collab Backend', body: 'We\'re building', charset: 'UTF-8', from: 'Jenkins', to: 'agavrilita@inthergroup.com')
          }
        }

      }
    }

  }
}