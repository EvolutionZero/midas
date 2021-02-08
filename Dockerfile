FROM maven:3.6.3-openjdk-8
VOLUME /tmp
ADD ./target/midas.jar midas.jar
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
ENTRYPOINT ["java","-Dmidas.threshold=4", "-Dspring.datasource.hikari.maximum-pool-size=24", "-Dfile.encoding=UTF-8","-jar","/midas.jar"]