pipeline {
    agent any
    tools {
        maven 'Maven 3.6.0'
        jdk 'Jdk11'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
            post {
                success {
                    echo 'Build done!'
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo deploy stage ...'
                //sh 'scp -o StrictHostKeyChecking=no target/functional-cicd-demo.jar gians@192.168.0.5:/Users/gians/Desktop/jenkins-test'
                //sh 'scp -o StrictHostKeyChecking=no -r target/lib gians@192.168.0.5:/Users/gians/Desktop/jenkins-test'
            }
            post {
                success {
                    echo 'Deploy done!'
                }
            }
        }
    }
}