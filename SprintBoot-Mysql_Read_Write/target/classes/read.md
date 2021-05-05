# 读写分离
## 切换数据源需要用到类AbstractRoutingDataSource

# 思路：
在做项目的时候实现过mybatis数据源的动态切换。基于原来的方案，用aop来拦截dao层方法，根据方法名称就可以判断要执行的sql类型，动态切换主从数据源。
1. 1.mybatis和数据源配置
2. 数据源切换
   切换数据源需要用到类AbstractRoutingDataSource；targetDataSources用一个map来存储配置的数据源，defaultTargetDataSource默认的数据源。
   项目启动时targetDataSources中的值会放到resolvedDataSources，key默认为targetDataSources中的key,可以实现resolveSpecifiedLookupKey()方法处理。
   resolvedDefaultDataSource会被赋值给defaultTargetDataSource，因此如果defaultTargetDataSource没有配启动会报错 。
   在需要与mysql交互时检索resolvedDataSources中的数据源，通过抽象determineCurrentLookupKey()获取当前数据源的key,因此实现这个方法可以实现数据源的切换。
3. 数据源加载：MybatisConfiguration 
4. 
    