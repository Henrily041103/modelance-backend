@echo off

REM Prompt for Docker login
docker login
if %ERRORLEVEL% neq 0 (
    echo Docker login failed
    exit /b %ERRORLEVEL%
)

REM Use Maven Wrapper to clean and package the project
call mvnw.cmd clean package
if %ERRORLEVEL% neq 0 (
    echo Maven build failed
    exit /b %ERRORLEVEL%
)

REM Copy the Dockerfile to the target directory
copy Dockerfile target\
if %ERRORLEVEL% neq 0 (
    echo Failed to copy Dockerfile
    exit /b %ERRORLEVEL%
)

REM Change directory to target
cd target

REM Build the Docker image
docker build . --tag us-central1-docker.pkg.dev/modelance-84abf/modelance-docker/image:latest
if %ERRORLEVEL% neq 0 (
    echo Docker build failed
    exit /b %ERRORLEVEL%
)

REM Push the Docker image
docker push us-central1-docker.pkg.dev/modelance-84abf/modelance-docker/image:latest
if %ERRORLEVEL% neq 0 (
    echo Docker push failed
    exit /b %ERRORLEVEL%
)

REM Deploy to Google Cloud Run
gcloud run deploy modelance-backend --image us-central1-docker.pkg.dev/modelance-84abf/modelance-docker/image:latest
if %ERRORLEVEL% neq 0 (
    echo Google Cloud Run deployment failed
    exit /b %ERRORLEVEL%
)

REM Change back to the previous directory
cd ..
