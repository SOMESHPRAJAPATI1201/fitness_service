pipeline {
  agent {
    kubernetes {
      label 'Test-APP'
      yaml """
apiVersion: v1
kind: Pod
spec:
  serviceAccountName: jenkins
  restartPolicy: Never
  containers:
  - name: node
    image: node:24
    command: ['cat']
    tty: true
    resources:
      requests:
        cpu: 1
        memory: .5Gi
      limits:
        cpu: 2
        memory: 1Gi

"""
    }
  }


  stages {
    stage('Dummy Test') {
      steps {
        sh '''
          echo "Running on node:"
          uname -a

          echo "Creating dummy file..."
          echo "Hello from Jenkins" > dummy.txt

          echo "Listing files:"
          ls -la

          echo "Reading dummy file:"
          cat dummy.txt
        '''
      }
    }
  }
}
