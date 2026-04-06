FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y ffmpeg
COPY ./build/libs/han-geureut-0.0.1-SNAPSHOT.jar han-geureut.jar
ENTRYPOINT ["java", "-jar", "han-geureut.jar"]
