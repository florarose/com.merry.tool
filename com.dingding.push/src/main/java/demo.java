import com.dingding.push.entity.SendResult;
import com.dingding.push.utils.DingtalkChatbotClient;
import com.dingding.push.utils.TextMessage;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.util.ArrayList;
import java.util.List;

public class demo {

    private static DingtalkChatbotClient client = new DingtalkChatbotClient();
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=b9a9053ce65240db150bec9c805ad516c65dec75cda8ea962646df12cf9fa5a2";

    public static void dingding(String text,String [] phones) throws Exception{

        TextMessage textMessage = new TextMessage(text);
        List<String> atMobiles = new ArrayList<String>();
        if(null !=phones && !"".equals(phones)){
            for(String phone:phones){
                atMobiles.add(phone);
            }
            textMessage.setAtMobiles(atMobiles);
        }
        if(atMobiles.size()>0){
            textMessage.setIsAtAll(false);
        }else {
            textMessage.setIsAtAll(true);
        }
        SendResult result = client.send(WEBHOOK_TOKEN, textMessage);
    }
    public  static  void main(String [] arrgs) {
        String phones1[] = {"17835059864"};
        try {
            dingding("美好的一天,因为你有你",phones1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
