FROM adoptopenjdk:11-jre-hotspot

COPY target/com.creditcard.processor-0.0.1-SNAPSHOT.jar credit-card-processor-1.0.0.jar

CMD ["java", "-jar", "/credit-card-processor-1.0.0.jar"]