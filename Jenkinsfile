pipeline {
	agent any
    environment {
		MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }
    triggers {
		githubPush()
    }
    stages {
		stage('Checkout') {
			steps {
				git 'https://github.com/sezginmert/techcareerNetProject.git'
            }
        }
        stage('Build and Run Tests') {
			steps {
				sh 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }
        stage('Allure Report') {
			steps {
				sh 'mvn allure:report'
            }
        }
        stage('Publish Allure Report') {
			steps {
				allure([
                    includeProperties: false,
                    jdk: '',
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
    post {
		success {
			echo '✅ Build succeeded. Pushing changes...'
            // Eğer sonuçlara göre Git işlemi yapılacaksa burada işlem yazılabilir.
        }
        failure {
			mail to: 'sezginmertt@gmail.com',
                 subject: "❌ Build Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Build failed. Check the logs at: ${env.BUILD_URL}"
        }
    }
}
