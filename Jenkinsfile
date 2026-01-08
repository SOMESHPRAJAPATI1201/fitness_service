pipeline {
  agent any

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
