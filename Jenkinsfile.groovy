pipeline {
    agent any

    stages {
        stage('GIT PULL') {
            steps {
                git branch: "develop", url: 'https://github.com/RanaRanvijaySingh/FlutterStarter.git'
            }
        }
        stage('TEST') {
            steps {
                sh 'flutter test'
            }
        }
        stage('BUILD') {
            steps {
                sh '''
                  #!/bin/sh
                  ls
                  '''
            }
        }
        stage('DISTRIBUTE') {
            steps {
                appCenter apiToken: '794df02f8d5db84fa465fcb89811a5842a91ecf1',
                        ownerName: 'hucygixy@digital10network.com',
                        appName: 'flutter-demo',
                        pathToApp: 'build/app/outputs/apk/debug/app-debug.apk',
                        distributionGroups: 'AlphaTester'
            }
        }
    }
}
