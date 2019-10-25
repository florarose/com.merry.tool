import com.study.verification.Captcha;
import com.study.verification.GifCaptcha;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;

public class demo {

    /**
     * 把生成的code放入图片格式 src 中，即可
     * @param args
     */
    public static void main(String[] args) {
        // 生成文字验证码
        Captcha captcha = new GifCaptcha(146, 33, 6);

        // 生成图片验证码
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        captcha.out(outputStream);
        String text = captcha.text();
        // 对字节数组Base64编码cffffffffffffffffffffffffffffffffffffffffffffffffffffffffll'
        BASE64Encoder encoder = new BASE64Encoder();
        String code = "data:image/jpeg;base64,"+encoder.encode(outputStream.toByteArray());
        System.out.println(code);
    }
}
