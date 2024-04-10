pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Build Maven Project') {
            steps {
                sh 'mvn --version'
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Subhajitgarai/Hateos_SpringBoot']])
                sh 'java --version'
                sh 'docker --version'
                sh 'mvn clean install'
            }
        }
        stage('Build the docker image'){
            steps{
                script{
                    sh 'docker build -t royalsubha/springboottrial .'
                }
            }
        }
        stage('Push into docker hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u royalsubha -p ${dockerhubpwd}'
                    }
                    sh 'docker push royalsubha/springboottrial'
                }
            }
        }
        stage('deploy k8s'){
            steps{
                script{
                    kubeconfig(credentialsId: 'mykubeconfig', serverUrl: 'https://192.168.49.2:8443'){
                        sh 'kubectl delete service/springboot-crud-svc --ignore-not-found=true'
                        sh 'kubectl delete deployment.apps/springboot-crud-deployment --ignore-not-found=true'
                        sh 'kubectl apply -f mysql-secret.yaml'
                        sh 'kubectl apply -f mysql-storage.yaml'
                        sh 'kubectl apply -f mysql-deployment.yaml'
                        sh 'kubectl apply -f app-deployment.yaml'
                        sh 'kubectl get pods'
                    }
                }
            }
        }
    }
}
