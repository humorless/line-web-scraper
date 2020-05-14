FROM openjdk:8-alpine

COPY target/uberjar/line-web-scraper.jar /line-web-scraper/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/line-web-scraper/app.jar"]
