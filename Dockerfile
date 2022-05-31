#load the image from either local or docker hub
FROM openjdk:17-alpine

#create a directory where the application will be placed
RUN mkdir /app

#copy the compiled jar file to the /app directory
COPY target/*.jar /app/

#set up default enviroment variables
ENV SERVER_PORT=8080 \
    DB_URL=jdbc:postgresql://localhost:5432/video_uploads \
#     POSTGRES_HOST_AUTH_METHOD=trust \
    DB_USER=user \
    DB_PASSWORD=password \
    MAX_LOG_ROTATION_SIZE=10MB

    
#set the current working directory
WORKDIR /app

#start the app
ENTRYPOINT ["/bin/sh","-c","exec java -Dserver.port=${SERVER_PORT} -Dspring.datasource.url=${DB_URL} -Dspring.datasource.username=${DB_USER} -Dspring.datasource.password=${DB_PASSWORD} -Dlogging.logback.rollingpolicy.max-file-size=${MAX_LOG_ROTATION_SIZE} -Duser.timezone=Africa/Kampala -jar video-upload-challenge-0.0.1.jar" ]