FROM openjdk:17.0.2-slim-bullseye
ADD ./target/daily-hot.jar /app.jar
CMD ["--server.port=8080"]
ENV spring.profiles.active="prod"
LABEL maintainer=admin@lilu.org.cn
ENV TZ=Asia/Shanghai
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
