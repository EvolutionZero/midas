FROM maven:3.6.3-openjdk-8
VOLUME /tmp
RUN package -Dmaven.test.skip=true
ADD ./target/midas.jar midas.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dmidas.threshold=4 -Dspring.datasource.hikari.maximum-pool-size=24 -Dfile.encoding=UTF-8","-jar","/midas.jar"]