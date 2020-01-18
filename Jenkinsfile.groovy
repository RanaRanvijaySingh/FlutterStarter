pipeline {
    agent any

    stages {
        stage('Code pull') {
            when {
                expression { return params.current_status == "closed" && params.merged == true }
            }
            steps {
                // Get some code from a GitHub repository
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
                appCenter apiToken: 'f51cf29ba6b2234a84c999bc37348db84624c615',
                        ownerName: 'ranaranvijaysingh9-gmail.com',
                        appName: 'Flutter-Crystal-Cruise',
                        pathToApp: 'build/app/outputs/apk/debug/app-debug.apk',
                        distributionGroups: 'flutter-crystal-cruise'
            }
        }
    }
}
