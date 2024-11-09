# 使用较小的 Ubuntu 基础镜像
FROM ubuntu:latest AS builder

# 更新并安装必要工具（findutils 和 OpenJDK 21）
RUN apt-get update && apt-get install -y --no-install-recommends openjdk-21-jdk findutils && \
    rm -rf /var/lib/apt/lists/*  # 清理缓存减少镜像大小

# 设置环境变量，简化路径配置
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# 设置工作目录
WORKDIR /app

# 复制项目文件到构建镜像
COPY . .

# 授予 gradlew 脚本执行权限
RUN chmod +x ./gradlew

# 使用 Gradle Wrapper 构建项目，并禁用文件系统监视功能
RUN ./gradlew clean build --no-daemon -Dorg.gradle.vfs.watch=false -x test

# 第二阶段：运行时镜像，使用更小的基础镜像
FROM openjdk:21-jre-slim

# 设置工作目录
WORKDIR /app

# 复制构建阶段生成的 JAR 文件到运行镜像
COPY --from=builder /app/build/libs/Email_Service_Team9-0.0.1-SNAPSHOT.jar /app/app.jar

# 暴露 8080 端口
EXPOSE 8080

# 运行应用程序
CMD ["java", "-jar", "/app/app.jar"]