                                 #Jmeter的初步认识
                                 
## 一、 接口测试
  ###  一般的测试包含七步：
  1. 新建请求
      ![jemete1](https://yqfile.alicdn.com/485a163f67f25e4580611a2a96a2e2745139af0c.png)
  2. 添加线程组
      ![jemter2](https://yqfile.alicdn.com/bf5587bb520b5079d5d426352a20dfdea59b6b54.png)
  3. 添加http请求
      ![jemter4](https://yqfile.alicdn.com/d23e0184ccc22775ef40f3a127852460cbdf6cb2.png)
  4. 配置http参数
      ![jemter7](https://yqfile.alicdn.com/e4ae3413d2e225fa3a08a758ac40804e29b136c8.png)
  5. 添加cookie管理、header管理
      ![jemter3](https://yqfile.alicdn.com/0b91fa862704c641711c4e00b06bbc45feacf321.png)
  6. 添加结果树
     ![jemter5](https://yqfile.alicdn.com/31df68550a6f0f631d21f963cab7e374e5ce506e.png)
  7. 在点击运行
     ![jemter6](https://yqfile.alicdn.com/03afdfd33c588aefcf497181fcaefc6d41edb51e.png)
  8. 查看运行结果
     ![jemter8](https://yqfile.alicdn.com/ab33ae135891e7c25938ff7fd21308f4569939ec.png)
     参数在前后端分离一般用json格式，也可以选择html（HTMl Source Formatted）或者其他
 
## 二、 压力测试 
   1. 配置线程组
       ![xingneng_ceshi_](https://yqfile.alicdn.com/02e6cdbe6a18962a03d34fdd47dfc9fac43669a9.png)       
       参数说明：
       
       -. 线程数：虚拟用户数。一个虚拟用户占用一个进程或线程。多少虚拟用户就设置多少个线程数。
       
       -. Rame-Up Period: 表示线程数多长时间全部启动。即准备时长：如果线程数是15，准备时长为3，，也就是每秒钟启动5个线程。
       
       -. 循环次数：这个设置不会改变并发数，可以延长并发时间。总请求数=线程数*循环次数
       
       -. 调度器：设置压测的启动时间、结束时间、持续时间和启动延迟时间。
           