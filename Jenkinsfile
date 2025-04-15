pipeline {
    agent any

    tools {
        maven 'Maven 3.8.5'
        jdk 'JDK 17'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=true"
    }

    triggers {
        cron('39 23 * * *')  // Her akşam 23:40'da otomatik çalışır
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/sezginmert/techcareerNetProject.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Allure Report') {
            steps {
                sh 'mvn allure:report'
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure includeProperties: false, jdk: '', reportBuildPolicy: 'ALWAYS'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
        failure {
            mail to: 'you@example.com',
                 subject: "❌ Build Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Something went wrong!\n\nCheck it here: ${env.BUILD_URL}"
        }
    }
}
