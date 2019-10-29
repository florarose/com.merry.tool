
import com.liquibase.demo.LiquibaseApplication;
import com.liquibase.demo.model.User;
import com.liquibase.demo.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author liudongting
 * @date 2019/8/20 14:58
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =  LiquibaseApplication.class)
public class UserTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void test01() {
        List<User> userList=     userService.list();
        System.out.println(userList.size());
    }

}
