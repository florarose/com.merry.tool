package vedio;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/3 14:18
 */
public class ImageUtil {

    /**
     * 压缩图片
     * @param in 压缩源
     * @param maxLength 压缩后的最大长度或宽度
     * @return 压缩后的数据
     * @throws IOException
     */
    public static byte[] compress(InputStream in, int maxLength)
            throws IOException {
        BufferedImage src = ImageIO.read(in);
        int old_w = src.getWidth(); // 得到源图宽
        int old_h = src.getHeight(); // 得到源图长
        int new_w = 0; // 新图的宽
        int new_h = 0; // 新图的长
        BufferedImage tempImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = tempImg.createGraphics(); // 从原图上取颜色绘制新图
        // tempImg = g.getDeviceConfiguration().createCompatibleImage(old_w,
        // old_h, Transparency.TRANSLUCENT);
        // g.dispose();
        g.fillRect(0, 0, old_w, old_h);
        // g.fillOval(0, 0, old_w, old_h);
        g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
        g.dispose();
        // 根据图片尺寸压缩比得到新图的尺寸
        if (old_w > old_h) {
            new_w = maxLength;
            new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
        } else {
            new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
            new_h = maxLength;
        }
        BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
        newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(newImg, "gif", os);
        byte[] bs = os.toByteArray();
        os.close();
        return bs;
    }

}
