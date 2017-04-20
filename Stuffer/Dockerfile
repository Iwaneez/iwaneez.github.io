FROM openjdk:8

EXPOSE 8080

COPY target/stuffer*.jar /home/stuBro/stuffer.jar

CMD java -jar /home/stuBro/stuffer.jar -Drun.arguments="--SPRING_PROFILES_ACTIVE=dev"