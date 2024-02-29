FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/minio-test-app-1.0-SNAPSHOT.jar /app/minio-example.jar

CMD ["java", "-jar", "minio-example.jar"]
