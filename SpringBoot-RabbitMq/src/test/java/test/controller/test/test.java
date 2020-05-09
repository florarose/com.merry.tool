package test.controller.test;

import com.alibaba.fastjson.JSONObject;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/30 19:42
 */
public class test {
    public static void main(String[] args) throws IOException, ParseException {
        Constants.useOfficial();
        // Constants.useSandbox();测试环境中使用Push服务不会影响线上用户 测试环境暂只支持iOS，不支持Android
        Map<String, String> params = new HashMap<>();
        params.put("badgeNum","1");
        params.put("openUrl","1");
        String messagePayload = "jisuanji";
        String title = " 06 14:099";
        String packname ="com.imagjs.android.HNSJYTYJ";
        String xmRegId = "Ce8WiCxOSqhkcueqLMsj2OeiCC+VrstFP0zPxYMYlqtyNqxIORPGX3RRBFhCwEE4";
        Sender sender = new Sender("6gK/xP7N1CbKUdT3fQJ/2Q==");
        Random random = new Random();
       final int s = random.nextInt(10000000) % (10000000 - 1 + 1) + 1;
        com.xiaomi.xmpush.server.Message message2 = new com.xiaomi.xmpush.server.Message.Builder()
                .title(title)
                .description(messagePayload)
                .payload(JSONObject.toJSONString(params))
                .restrictedPackageName(packname)
//                 .notifyId(s)
                .passThrough(1)  // 消息使用透传方式
                .notifyType(1)     // 使用默认提示音提示
                .extra("badgeNum", params.get("badgeNum"))
                .extra("openUrl", params.get("openUrl"))
                .build();
        Result result2 = sender.send(message2, xmRegId, 3); //根据regID，发送消息到指定设备上
        System.out.println(result2);
    }
}
