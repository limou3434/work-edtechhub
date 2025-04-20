# 基础镜像
FROM openjdk:17-jdk-slim

# 编译代码(请在本地直接使用 ./mvnw clean package -DskipTests 进行编译避免容器过大)
COPY ./*.jar ./app.jar

# 运行端口
EXPOSE 8131

# 启动命令
CMD ["java", "-jar", "app.jar"]
