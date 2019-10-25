package com.tool.qrcode;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;


public class QrcodeDemo {
    /**
     * url 是二维码存放的内容
     * @param url
     * @param response
     */
    public void qrcode(String url, HttpServletResponse response) {
        try {
            url = URLDecoder.decode(url, "utf8");
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            ByteArrayOutputStream bout =
                    QRCode.from(url)
                            .withSize(250, 250)
                            .to(ImageType.PNG)
                            .stream();
            OutputStream out = new FileOutputStream("qr-code.png");
            bout.writeTo(response.getOutputStream());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
