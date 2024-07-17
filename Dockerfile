FROM openjdk:8
ADD target/*.jar deploy/api-gateway.jar
ENTRYPOINT ["java","-jar","deploy/api-gateway.jar"]
EXPOSE 9092