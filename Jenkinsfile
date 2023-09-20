pipeline{

    agent any

    stages{

        stage('Build Jar'){
            steps{
                sh "mvn clean package -DskipTests"
            }
        }

        stage('Build Docker Image'){
            steps{
                sh "docker build -t=maddysk/selenium ."
            }            
        }  

        stage('Push Image'){
            steps{
                sh "docker push maddysk/selenium ."
            }            
        }                

    }

}