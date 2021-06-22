#!groovy

properties ([
    [$class: 'GitLabConnectionProperty', gitLabConnection: 'htw'],
    buildDiscarder(logRotator(numToKeepStr: '10')),
    ])

timeout(time: 60, unit: 'MINUTES') {

ansiColor('css') {

stage ('Build_Client') {

    node {


        checkout scm

        try {

            dir("client") {
                sh "pwd"
                sh 'npm install'
                sh 'npm run build'
            }

            echo "\u2713 success"
            currentBuild.result = 'SUCCESS'

        } catch (any) {
            echo "\u274C failure"
            currentBuild.result = 'FAILURE'
            throw any //rethrow exception to prevent the build from
proceeding
        } finally {
            mail()
        }

    }
}

stage ('Build_Server') {

    node {


        checkout scm

        try {

            dir("server/backend-wka/backend-wka") {
                sh "pwd"
                sh "chmod +x gradlew"
                sh './gradlew assemble'
            }

            echo "\u2713 success"
            currentBuild.result = 'SUCCESS'

        } catch (any) {
            echo "\u274C failure"
            currentBuild.result = 'FAILURE'
            throw any //rethrow exception to prevent the build from
proceeding
        } finally {
            mail()
        }

    }
}

stage ('Test_Client') {

    node {


        checkout scm

        try {

            dir("client") {
                sh "pwd"
                //sh 'ng test --code-coverage --watch=false --browsers=ChromeHeadless'
              
            }

            echo "\u2713 success"
            currentBuild.result = 'SUCCESS'

        } catch (any) {
            echo "\u274C failure"
            currentBuild.result = 'FAILURE'
            throw any //rethrow exception to prevent the build from
proceeding
        } finally {
            mail()
        }

    }
}

stage ('Test_Server') {

    node {


        checkout scm

        try {

            dir("server/backend-wka/backend-wka") {
                sh "pwd"
                sh "chmod +x gradlew"
                sh './gradlew test'
            }

            echo "\u2713 success"
            currentBuild.result = 'SUCCESS'

        } catch (any) {
            echo "\u274C failure"
            currentBuild.result = 'FAILURE'
            throw any //rethrow exception to prevent the build from
proceeding
        } finally {
            mail()
        }

    }
}
stage ('Sonar - client') {

    node {


        checkout scm

        try {

            dir("client") {
                sh "pwd"
                sh 'npm run sonar-scanner'
            }

            echo "\u2713 success"
            currentBuild.result = 'SUCCESS'

        } catch (any) {
            echo "\u274C failure"
            currentBuild.result = 'FAILURE'
            throw any //rethrow exception to prevent the build from
proceeding
        } finally {
            mail()
        }

    }
}
//Nicht ausführbar
stage ('Sonar-server') {

    node {


        checkout scm

        try {

            dir("server/backend-wka/backend-wka") {
                sh "pwd"
                //sh "chmod +x gradlew"
                //sh './gradlew sonarqube'
                
            }

            echo "\u2713 success"
            currentBuild.result = 'SUCCESS'

        } catch (any) {
            echo "\u274C failure"
            currentBuild.result = 'FAILURE'
            throw any //rethrow exception to prevent the build from
proceeding
        } finally {
            mail()
        }

    }
}






}
}

@NonCPS
def mail() {

    def subject = '${DEFAULT_SUBJECT}'

    // nur fehlerhafte Builds melden
    if (currentBuild.result == "SUCCESS") {

        if (currentBuild.previousBuild != null &&
            currentBuild.previousBuild.result != 'SUCCESS') {
            subject = "Job '${JOB_NAME}' (${BUILD_NUMBER}) - back to normal"
        } else {
            return
        }

    }

    def attachLog = (currentBuild.result != "SUCCESS")
    emailext(body: '${DEFAULT_CONTENT}', attachLog: attachLog,
            replyTo: '$DEFAULT_REPLYTO', subject: subject,
            recipientProviders: [[$class: 'FailingTestSuspectsRecipientProvider'],
                                 [$class: 'DevelopersRecipientProvider'],
                                 [$class: 'CulpritsRecipientProvider']])
}
//test

// vim: ft=groovy

