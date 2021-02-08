FROM maven:3.6.3-openjdk-8
VOLUME /tmp
ADD ./target/midas.jar midas.jar
ENTRYPOINT ["java","-Dmidas.threshold=4", "-Dspring.datasource.hikari.maximum-pool-size=24", "-Dfile.encoding=UTF-8","-jar","/midas.jar"]