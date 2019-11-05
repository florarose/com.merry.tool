
import com.study.thread.SchedulingConfig.ScheduleTaskApi;
import com.study.thread.ThreadApplication;
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
@SpringBootTest(classes =  ThreadApplication.class)
public class taskTest {

    @Autowired
    private ScheduleTaskApi scheduleTaskApi;

    @Test
    public void test01() {
        for(int i=0;i<10;i++){
            int  a = 0;
            int b = 06+i;
            int c = 16;
            StringBuilder sb = new StringBuilder();
            sb.append(a+" ");
            sb.append(b+" ");
            sb.append(c+" ");
            sb.append("* * ?");
            scheduleTaskApi.configureTasks(sb.toString(),i);
//            try {
//                Thread.sleep(800);
//            }catch (Exception e){
//
//            }
        }
    }

}
