package com.study.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/2 10:42
 */
@SpringBootApplication
public class WebsocketDemo {
    public static void main(String[] args) {
        try {
            SpringApplication.run(WebsocketDemo.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }


//        try {
//            SpringApplication bootstrap = new SpringApplication(ScratchApplication.class);
//            bootstrap.setBanner(new Banner() {
//                @Override
//                public void printBanner(Environment environment, Class<?> aClass, PrintStream printStream) {
//                    // 比如打印一个我们喜欢的ASCII Arts字符画
//                }
//            });
//            bootstrap.setBanner(new ResourceBanner(new ClassPathResource("banner.txt")));
//            bootstrap.setBannerMode(Banner.Mode.CONSOLE);
//            // 其他定制设置...
//            bootstrap.run(args);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
