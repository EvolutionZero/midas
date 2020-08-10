%设置控制台UTF-8输出%
chcp 65001
java -Dmidas.threshold=30 -Dspring.datasource.hikari.maximum-pool-size=24 -Dfile.encoding=UTF-8 -jar ./midas.jar 2>error.log