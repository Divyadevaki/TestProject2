		
		pipeline {
    agent { label "Slavelbl" }

    tools {
        maven "mvnbuildtool"
    }

    environment {
        AWS_REGION = 'us-east-1'
        ECR_REPO   = '054728709811.dkr.ecr.us-east-1.amazonaws.com/mypipelinerepo'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Divyadevaki/TestProject2.git'
            }
        }

        stage('Code-Build') {
            steps {
                sh "mvn clean install"
            }
        }

        stage('Docker-Build') {
            steps {
                script {
                    echo "Building Docker image and pushing to ECR..."
                    withAWS(region: "${env.AWS_REGION}", credentials: 'awscred_ecr') {
                        // Build image
                        sh 'docker build -t myimage1:latest .'

                        // Login to ECR
                        sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REPO}"

                        // Tag image
                        sh "docker tag myimage1:latest ${ECR_REPO}:latest"

                        // Push image
                        sh "docker push ${ECR_REPO}:latest"
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    echo "Pulling image from ECR for deployment...."
                    withAWS(region: "${env.AWS_REGION}", credentials: 'awscred_ecr') {
                        // Login to ECR
                        sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REPO}"

                        // Pull image
                        sh "docker pull ${ECR_REPO}:latest"
                        
                        // run the container
                        sh 'docker stop mycontainer1 || true'
                        sh 'docker rm mycontainer1 || true'
                        sh 'docker run -d -p "80:8080" --name mycontainer1 ${ECR_REPO}:latest' 
                    }
                }
            }
        }
    }
     post {
        success {
            emailext (
                to: 'divyavelautham@gmail.com',
                subject: "✅ SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """Jenkins Pipeline!
Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' completed successfully.
Check console output: ${env.BUILD_URL}"""
            )
        }
        failure {
            emailext (
                to: 'divyavelautham@gmail.com',
                subject: "❌ FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """Jenkins Pipeline!
Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' failed.
Check console output: ${env.BUILD_URL}"""
            )
        }
        always {
            echo "Pipeline finished, email notification sent"
        }
    }
}
