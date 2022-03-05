FROM openjdk:17
ADD target/projects-0.0.1-SNAPSHOT.jar incorta-projects.jar
ENTRYPOINT ["java", "-jar", "incorta-projects.jar"]