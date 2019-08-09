## 一、 执行命令，删除原有的docker
        ```
            sudo yum remove docker \
                              docker-client \
                              docker-client-latest \
                              docker-common \
                              docker-latest \
                              docker-latest-logrotate \
                              docker-logrotate \
                              docker-selinux \
                              docker-engine-selinux \
                              docker-engine
         ```
         sudo yum install docker-ce
         sudo systemctl start docker
         docker --version
         
         安装maven,和java
         docker pull hub.c.163.com/wuxukun/maven-aliyun:3-jdk-8 
         
         FROM hub.c.163.com/wuxukun/maven-aliyun:3-jdk-8
         
         ADD pom.xml /tmp/build/
         
         ADD src /tmp/build/src
                 #构建应用
         RUN cd /tmp/build && mvn clean package \
                 #拷贝编译结果到指定目录
                 && mv target/*.jar /app.jar \
                 #清理编译痕迹
                 && cd / && rm -rf /tmp/build
         
         VOLUME /tmp
         EXPOSE 8080
         ENTRYPOINT ["java","-jar","/app.jar"]