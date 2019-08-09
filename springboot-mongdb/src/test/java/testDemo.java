import com.mongdb.demo.PublishVisit;
import com.mongdb.demo.mongdbServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author liudongting
 * @date 2019/7/22 16:30
 */
@RunWith(SpringRunner.class)
public class testDemo {



    @Test
    public void test(){
        mongdbServiceImpl mp = new mongdbServiceImpl();
     List<PublishVisit>  dd = mp.findAll(PublishVisit.class);
    }
}
