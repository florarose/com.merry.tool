package autoDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ldt merry
 * @date 2020/2/19
 */
@SpringBootApplication
public class AutoDemoApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AutoDemoApplication.class);
        springApplication.run(args);
    }

}
