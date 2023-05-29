FROM openjdk:17
ADD target/university-timetable.jar university-timetable.jar
ENTRYPOINT ["java", "-jar","university-timetable.jar"]
EXPOSE 8080