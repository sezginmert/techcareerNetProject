pipeline {
	agent any
    tools {
		maven 'Maven 3.9.9'
        jdk 'JDK 17'
    }
    environment {
		MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }
    triggers {
		cron('49 23 * * *')  // Her akşam 23:49'da otomatik çalışır
    }
    stages {
		stage('Checkout') {
			steps {
				git branch: 'main', url: 'https://github.com/sezginmert/techcareerNetProject.git'
            }
        }

        stage('Install Dependencies') {
			steps {
				sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run All Tests') {
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

        stage('Git Push if Tests Passed') {
			when {
				expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
				withCredentials([usernamePassword(credentialsId: 'github-credentials-id', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
					sh '''
                        git config --global user.email "jenkins@yourcompany.com"
                        git config --global user.name "Jenkins CI"
                        git add .
                        git commit -m "✅ All tests passed - auto commit from Jenkins"
                        git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/sezginmert/techcareerNetProject.git
                    '''
                }
            }
        }
    }

    post {
		always {
			junit '**/target/surefire-reports/*.xml'
        }

        failure {
			mail to: 'sezginmertt@gmail.com',
                 subject: "❌ Test Run Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Some tests failed.\n\nCheck details: ${env.BUILD_URL}"
        }
    }
}
