pipeline {
    agent any

    stages {
        stage('Code pull') {
            when {
                expression {
                    String s = "NOT MERGED"
                    if(params.merged) {
                        s = "MERGED"
                    }
                    echo "PR CURRENT STATUS: ${params.current_status}"
                    echo "PR MERGED STATUS: $s"
                    return params.current_status == "closed" && params.merged == true
                }
            }
            steps {
                git 'https://github.com/RanaRanvijaySingh/FlutterStarter.git'
            }
        }
        stage('Build') {
            when {
                expression { return params.current_status == "closed" && params.merged == true }
            }
            steps {
                sh '''
            #!/bin/sh
            flutter build apk --debug
            '''
            }
        }
        stage('Test') {
            when {
                expression { return params.current_status == "closed" && params.merged == true }
            }
            steps {
                sh 'flutter test'
            }
        }
        stage('Publish') {
            when {
                expression { return params.current_status == "closed" && params.merged == true }
            }
            steps {
                appCenter apiToken: '794df02f8d5db84fa465fcb89811a5842a91ecf1',
                        ownerName: 'ranaranvijaysingh9-gmail.com',
                        appName: 'Flutter-Crystal-Cruise',
                        pathToApp: 'build/app/outputs/apk/debug/app-debug.apk',
                        distributionGroups: 'flutter-crystal-cruise'
            }
        }
    }
}
