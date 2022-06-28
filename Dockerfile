FROM adoptopenjdk/openjdk11:ubi
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
VOLUME /tmp 
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]