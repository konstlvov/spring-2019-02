FROM openjdk:11-oraclelinux7
WORKDIR /app
COPY . /app
RUN chmod -R 777 /app
CMD java -Dfile.encoding=UTF-8 -jar booklib-0.1.0.jar
