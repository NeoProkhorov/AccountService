FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY  --from=builder /app/target/*.jar *.jar
EXPOSE 18001
ENTRYPOINT ["java", "-jar", "*.jar"]