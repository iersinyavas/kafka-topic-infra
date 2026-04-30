pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    environment {
        GITHUB_USERNAME = credentials('github_username')
        GITHUB_PASSWORD = credentials('github_token')
        DB_USERNAME = credentials('db_username')
        DB_PASSWORD = credentials('db_password')
    }

    stages {
        stage('Build Maven') {
            steps {
                checkout scmGit(
                        branches: [[name: '*/master']],
                        extensions: [],
                        userRemoteConfigs: [[
                                                    url          : 'https://github.com/iersinyavas/kafka-topic-infra',
                                                    credentialsId: 'github_token'
                                            ]]
                )
                sh 'mvn clean install -DskipTests'
            }
        }


        /*stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }*/


        stage('Docker Image') {
            steps {
                sh 'docker build  -t iersinyavas/kafka-topic-infra:latest  .'
            }
        }


        stage('Docker Image to DockerHub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub_token', variable: 'dockerhub_token')]) {

                        sh 'docker login -u iersinyavas -p ${dockerhub_token}'
                        sh 'docker image push iersinyavas/kafka-topic-infra:latest'
                    }
                }
            }
        }

        /* stage('Deploy') {
                   steps {
                       sh '''
                           docker stop order-service || true
                           docker rm order-service || true
                           docker run -d \
                             --name order-service \
                             --network econverse-network \
                             -e DB_USERNAME=${DB_USERNAME} \
                             -e DB_PASSWORD=${DB_PASSWORD} \
                             -p 8090:8090 \
                             iersinyavas/order-service:latest
                       '''
                   }
               } */
        stage('Deploy') {
            steps {
                withCredentials([file(credentialsId: 'econverse_env', variable: 'ENV_FILE'),
                                 usernamePassword(
                                         credentialsId: 'github_token',
                                         usernameVariable: 'GITHUB_USERNAME',
                                         passwordVariable: 'GITHUB_PASSWORD'
                                 )]) {
                    sh '''
                       rm -rf infra
                       git clone https://${GITHUB_USERNAME}:${GITHUB_PASSWORD}@github.com/iersinyavas/econverse-infra.git infra
       
                       cp $ENV_FILE infra/.env
       
                       cd infra
                       docker-compose pull order-service
                       docker-compose -p econverse-infra up -d --no-deps --force-recreate kafka-topic-infra
                   '''
                }
            }
        }

        stage('Docker Image to Clean') {
            steps {
                sh 'docker image prune -f'
            }
        }
    }
}
