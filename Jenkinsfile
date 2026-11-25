
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'M3'
    }

    environment {
        DOCKER_IMAGE = 'aktiwarigla/git-test-service'
        PATH = "/usr/local/bin:/opt/homebrew/bin:/usr/bin:/bin:/usr/sbin:/sbin"
    }

    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'

                checkout scm
            }
        }

        stage('Environment') {
            steps {
                echo 'Checking development environment...'

                sh 'echo $PATH'
                sh 'java -version'
                sh 'mvn -version'

                sh 'which docker'
                sh 'docker --version'
                sh 'docker info'
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot application...'

                sh 'mvn clean compile'
            }
        }

        stage('Unit Testing') {
            steps {
                echo 'Running unit tests...'

                sh 'mvn test'
            }

            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Creating executable JAR...'

                sh 'mvn package -DskipTests'

                sh 'ls -lh target/myDokerJar.jar'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'

                sh '''
                    docker build \
                        -t $DOCKER_IMAGE:$BUILD_NUMBER \
                        -t $DOCKER_IMAGE:latest .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_TOKEN'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_TOKEN" | docker login \
                            -u "$DOCKER_USER" \
                            --password-stdin

                        docker push $DOCKER_IMAGE:$BUILD_NUMBER

                        docker push $DOCKER_IMAGE:latest

                        docker logout
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: CI/CD Pipeline completed successfully!'
            echo "Docker Image: ${env.DOCKER_IMAGE}:${env.BUILD_NUMBER}"
            echo "Latest Image: ${env.DOCKER_IMAGE}:latest"
        }

        failure {
            echo 'FAILURE: CI/CD Pipeline failed. Check Console Output.'
        }

        always {
            echo 'Jenkins Pipeline execution completed.'
        }
    }
}
