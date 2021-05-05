# 读写分离
## 读写分离的原因:
   数据库写入效率要低于读取效率，一般系统中数据读取频率高于写入频率，单个数据库实例在写入的时候会影响读取性能。 
## 实现方式
   主要基于mysql的主从复制，通过路由的方式使应用对数据库的写请求只在master上进行，读请求在slave上进行。 
## 路由的方式有两种：
   ### 1、代理
   在应用和数据库之间增加代理层，代理层接收应用对数据库的请求，根据不同请求类型转发到不同的实例，在实现读写分离的同时可以实现负载均衡。
   ![](https://cdn.jsdelivr.net/gh/florarose/florarose.github.io//ima/20200911110129.png)
   #### 常用的读写分离中间件：
   amoeba，MySQL-Proxy
   ### 2、应用内路由 
   在应用程序中实现，针对不同的请求类型去不同的实例执行sql
   ![](https://cdn.jsdelivr.net/gh/florarose/florarose.github.io//ima/20200911110519.png)
   