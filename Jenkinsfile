pipeline {
    agent any

    environment {
        RECIPIENT = 'sezginmertt@yahoo.com'
        TARGET_BRANCH = 'main'
        REPO_URL = 'https://github.com/sezginmert/techcareerNetProject.git'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: "${env.GIT_CREDENTIALS_ID}", url: "${env.REPO_URL}", branch: "${env.TARGET_BRANCH}"
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Running Maven tests with @google'
                bat 'mvn clean install -Dgroups=test01'
            }
        }

        stage('Push to GitHub') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                echo 'Tests passed, pushing to GitHub...'
                withCredentials([usernamePassword(credentialsId: "${env.GIT_CREDENTIALS_ID}", usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
                    bat """
                        git config --global user.email "jenkins@example.com"
                        git config --global user.name "Jenkins CI"
                        git remote set-url origin https://${env.GIT_USER}:${env.GIT_PASS}@github.com/oguzhanmelihguclu/techcareerNetProject1.git
                        git add .
                        git commit -m "Automated commit by Jenkins after successful build" || echo "Nothing to commit"
                        git push origin ${env.TARGET_BRANCH}
                    """
                }
            }
        }
    }

    post {
        failure {
            mail to: "${env.RECIPIENT}",
                 subject: "🚨 Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """\
Build başarısız oldu.

Detaylar: ${env.BUILD_URL}console
"""
        }
    }
}