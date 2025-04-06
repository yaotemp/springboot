@echo off
echo 启动Spring Boot应用，开启远程调试端口5005
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" 