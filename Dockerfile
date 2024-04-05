FROM openjdk:17
EXPOSE 8080
ADD target/HeatosTrial-0.0.1-SNAPSHOT.jar HeatosTrial-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/HeatosTrial-0.0.1-SNAPSHOT.jar"]