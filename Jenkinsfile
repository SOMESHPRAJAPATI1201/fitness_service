pipeline {
  agent {
    kubernetes {
      label 'Test-App'
      yaml """
apiVersion: v1
kind: Pod
spec:
  serviceAccountName: jenkins
  restartPolicy: Never
  containers:
  - name: docker
    image: docker:25.0.5 # Getting image from public Docker Hub
    command:
    - sleep
    args:
    - 99d
    env:
      - name: DOCKER_HOST
        value: tcp://localhost:2375
    resources:
      requests:
        cpu: 2
        memory: 2Gi
      limits:
        cpu: 2
        memory: 4Gi
  - name: jfrogcli
    image: jfrog/jfrog-cli:2.56.1
    command: ['cat']
    tty: true
    env:
      - name: DOCKER_HOST
        value: tcp://localhost:2375
    resources:
      requests:
        cpu: 1
        memory: 1Gi
      limits:
        cpu: 1
        memory: 1Gi
  - name: sonarcli
    image: sonarsource/sonar-scanner-cli:5.0
    command: ['cat']
    tty: true
    resources:
      requests:
        cpu: 2
        memory: 2Gi
      limits:
        cpu: 2
        memory: 2Gi
  - name: awscli
    image: amazon/aws-cli:2.15.0
    command: ['cat']
    tty: true
  - name: terraform
    image: hashicorp/terraform:1.7.5
    command: ['cat']
    tty: true
  - name: docker-daemon
    image: docker:25.0.5-dind
    resources:
      requests:
        cpu: 2
        memory: "4Gi"
      limits:
        cpu: 2
        memory: "8Gi"
    securityContext:
      privileged: true
    env:
      - name: DOCKER_TLS_CERTDIR
        value: ""
      - name: DOCKER_DRIVER
        value: overlay2

"""
    }
  }

  environment {
    ENV_PROD = 'master'
    ENV_STAG = 'staging'
    ENV_DEV = 'develop'
    ENV_TEST = 'test'

    PROD_ENVIRONMENT = 'prod'
    STAG_ENVIRONMENT = 'staging'
    DEV_ENVIRONMENT = 'dev'
    TEST_ENVIRONMENT = 'test'

    PROD_ACCOUNT = '111222333444'
    STAG_ACCOUNT = '111222333444'
    DEV_ACCOUNT = '555666777888'
    TEST_ACCOUNT = '555666777888'

    BLUE_BUILD_URL = "${BUILD_URL}".replace('/test-app/job/', '/test-app/organizations/jenkins/').replace('/test-app/job/', '/test-app/detail/')
  }

  stages {
    stage('Set Branch Variables') {
      steps {
        script {
            println "Branch: ${env.GIT_BRANCH}"

            skipRemainingStages = false
            runPlaywrightStage = false

            switch (env.GIT_BRANCH) {
              case env.ENV_PROD:
                println 'Deploying to prod!'
                ENVIRONMENT = "${env.PROD_ENVIRONMENT}"
                ACCOUNT = "${env.PROD_ACCOUNT}"
                TAG = 'prod'
                DOCKER_TAG = 'test-app-prod'
//                 DB_SECRETS = "${env.PROD_APP_DB_SECRET}"
                skipRemainingStages = false
                break
              case env.ENV_STAG:
                println 'Deploying to staging!'
                ENVIRONMENT = "${env.STAG_ENVIRONMENT}"
                ACCOUNT = "${env.STAG_ACCOUNT}"
                TAG = 'staging'
                DOCKER_TAG = 'test-app-staging'
//                 DB_SECRETS = "${env.STAG_APP_DB_SECRET}"
                skipRemainingStages = false
                break
              case env.ENV_DEV:
                println 'Deploying to dev!'
                ENVIRONMENT = "${env.DEV_ENVIRONMENT}"
                ACCOUNT = "${env.DEV_ACCOUNT}"
                TAG = 'dev'
                DOCKER_TAG = 'test-app-dev'
//                 DB_SECRETS = "${env.DEV_APP_DB_SECRET}"
                runPlaywrightStage = true
                break
              case env.ENV_TEST:
                println 'Deploying to test!'
                ENVIRONMENT = "${env.TEST_ENVIRONMENT}"
                ACCOUNT = "${env.TEST_ACCOUNT}"
                TAG = 'test'
                DOCKER_TAG = 'test-app-test'
//                 DB_SECRETS = "${env.TEST_APP_DB_SECRET}"
                runPlaywrightStage = true
                break
              default:
                println 'Not a branch that we want to deploy!'
                skipRemainingStages = true
            }
        }
      }
    }
    stage('Checkout'){
      steps {
        checkout scm
      }
    }
    stage('SonarQube Analysis') {
      when {
        expression {
          !skipRemainingStages
        }
      }
      steps {
        container('sonarcli'){
          script{
              withSonarQubeEnv('SonarQube'){
                sh '''

                '''
            }
          }
        }
      }
    }
    stage('AWS Assume Role') {
      when {
        expression {
          !skipRemainingStages
        }
      }
      steps {
        container('awscli') {
          sh """

          """
//           script {
//             env.AWS_ACCESS_KEY_ID = sh(returnStdout: true, script: 'cut -f1 /tmp/role-creds.txt').trim()
//             env.AWS_SECRET_ACCESS_KEY = sh(returnStdout: true, script: 'cut -f3 /tmp/role-creds.txt').trim()
//             env.AWS_SESSION_TOKEN = sh(returnStdout: true, script: 'cut -f4 /tmp/role-creds.txt').trim()
//           }
        }
      }
    }
    stage('Playwright UI Test') {
      when {
        expression {
          runPlaywrightStage
        }
      }
      steps {
          container('docker') {
            sh """

            """
          }
        }
      }
    stage('Parallel') {
      when {
        expression {
          !skipRemainingStages
        }
      }
      failFast true
      parallel {
        stage('Build Docker Image') {
          steps {
              container('docker') {
                println "Docker Tag: $DOCKER_TAG"
                sh """

                """
              }
            }
          }
        }
      }
    stage('jfrogpush') {
      when {
        expression {
          !skipRemainingStages
        }
      }
      steps {
        container('jfrogcli') {
              sh """

            """
          }
        }
      }
    stage('Push to Fargate ECS') {
      steps {
        container('awscli') {
            sh """

              """
        }
      }
    }
  }
  post {
    failure {
      slackSend color: 'danger', message: ":no_entry: *BUILD FAILED* :no_entry: \n\nJob: $JOB_NAME#$BUILD_NUMBER.\n\nURL: ${BLUE_BUILD_URL}"
    }
    success {
      slackSend color: 'good', message: ":white_check_mark: *BUILD SUCCESSFUL* :white_check_mark: \n\nJob: $JOB_NAME#$BUILD_NUMBER.\n\nURL: ${BLUE_BUILD_URL}"
    }
    aborted {
      slackSend color: 'warning', message: ":warning: *BUILD ABORTED* :warning: \n\nJob: $JOB_NAME#$BUILD_NUMBER.\n\nURL: ${BLUE_BUILD_URL}"
    }
  }
}
