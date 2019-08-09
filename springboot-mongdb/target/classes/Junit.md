## 一、junit基本参数介绍

 参数 | 解释 
 ------- | ------- 
 @BeforeClass | 在单元测试类中执行一次，在所有测试方法前执行一次 |
 @AfterClass  | 在单元测试类中执行一次，在所有测试方法后执行一次，通常在其中写上销毀和释放资源的代码 |
 @Before      | 在每个测试方法前执行，一股用来初始化方法（比如我们在測试别的方法时，类中与其 他测试方法共享的值已经被改变，为了保证测试结果的有效性，我们会在@Before注解 的方法中重置数据）|
 @After       | 释放资源 ，对于每一个测试方法都要执行一次
 @Test{timeout =1000) | 测试方法执行超过1000室秒后算超时，测试将失败
 @Test(expected=Exception.class) | 测试方法期里得到的异常类，如果方法执行设有抛出指走的异常，则测试失败
 @lgnore(i!not ready yet，，）| 执行測试时将忽K掉此方法，如果用于修饰类，则忽15整个类 编写一股测试用例
 @Test| 编写一般测试用例
 @RunWith| 在JUnit中有很多个Runner,他们负责堝用你的测试代码，每一^Runne谢有各自的待 殊功能，你要根据需要选择不同的Runner来运行你的測试代码。如果我们只是简单的做 音通Java测试，不涉及Spring Web顷目，你可以省略@RunWith注解，这祥系统会目动 使用默认Flunn6「来运行你的代码。
## 二、 各个参数的执行顺序
  - 一个JUnit4的单元测试用例执行顺序为： 
  
     @BeforeClass -> @Before -> @Test -> @After -> @AfterClass; 
     
  - 每一个测试方法的调用顺序为： 
  
     @Before -> @Test -> @After; 
## 三、Springboot 单元测试

   - ### 依赖
      ```
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
          </dependency>
      ```
   - ### Controller 测试
      ```
          @RunWith(SpringRunner.class)
          //ItodoApplication springboot的启动类
          @SpringBootTest(classes = ItodoApplication.class)
          @AutoConfigureMockMvc
          public class ApplicationTests {
              @Autowired
              private MockMvc mvc;
          
              // 注入Spring容器
              @Autowired
              private WebApplicationContext wac;
          
              @Before
              public void setupMockMvc(){
                  // 初始化MockMvc对象
                  mvc = MockMvcBuilders.webAppContextSetup(wac).build();
              }
              @Test(timeout = 100000)
              public void testUserController() throws Exception {
                  //ObjectMapper 是一个可以重复使用的对象
                  String exposeHeaders = "access-control-expose-headers";
                  String allowMethods = "Access-Control-Allow-Methods";
                  String allowHeaders = "Access-Control-Allow-Headers";
                  ObjectMapper mapper = new ObjectMapper();
                  String jsonString = "{\"todoOpenId\":\"oKiWu4iI0wKFxmiE-BLiZ1kud26Q\"}";
                  //将JSON字符串值转换成 Girl对象里的属性值
                  WxUserView girl = mapper.readValue(jsonString, WxUserView.class);
                  MvcResult result = mvc.perform(
                          //这里是post请求，如果get,替换即可。
                          MockMvcRequestBuilders.post("/api/wxUser/detailUser")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .header("authToken","wx")
                                  .header("Origin","chrome-extension://mdbgchaihbacjfjeikflfbelidihhmfn")
                                  .header(exposeHeaders,"111")
                                  .header(allowHeaders,"222")
                                  .header(allowMethods,"3333333333")
                                   //post请求传参数
                                 .content(mapper.writeValueAsString(girl))
                                 //get请求，传参数用这个
                                  //.param();
                          .accept(MediaType.APPLICATION_JSON)   //断言返回结果是json
                  )
                          .andExpect(MockMvcResultMatchers.status().isOk())
                          .andReturn();
                  MockHttpServletResponse response = result.getResponse();
                           //拿到请求返回码
                           int status = response.getStatus();
                          //拿到结果
                           String contentAsString = response.getContentAsString();
                  System.out.println(result);
              }
          }
     ```  
     - ### Service 测试
      ```
          @RunWith(SpringRunner.class)
          @SpringBootTest(classes  =ItodoApplication.class)
          public class ServiceTest {
          
              @Autowired
              private WxUserServiceImpl wxUserService;
          
              @Test
              public void usertest(){
                  WxUser wxUser = wxUserService.getWxUserByOpenId("oKiWu4iI0wKFxmiE-BLiZ1kud26Q");
              }
          }

      ```
## 四、@MockBean  和 @SpyBean
   
   - 在写测试时，对于一些应用的外部依赖需要进行一些Mock 处理，比如：Redis 等;对于这些外部依赖，统一在配置层完成 Mock;
   - @MockBean:mock的是本地的代码（自己写的代码），对于储存在库中并且是以 Bean 的形式装配到代码中的类无能为力；而且会导致spirngboot多次重启，因为会导致applicationContext的缓存失效。
   - @SpyBean:会监听一个Bean 中某些特定的方法，并在调用这些方法时给出指定的映射。
## 五、@Profile(value = "dev")
   -   这个注解，注解到类上，用于在不同的环境使用
     