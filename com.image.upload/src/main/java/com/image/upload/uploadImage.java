package com.image.upload;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

public class uploadImage {

    public static final Logger logger = LoggerFactory.getLogger(uploadImage.class);

    private String path;

    private String imageUrlPrefix;  //config.json 中配置图片的访问路径，图片上传到图片服务器上
    /*
     图片上传的方法
     */
    public String uploadLocalImages(MultipartFile file) {
        InputStream fis = null;
        OutputStream outputStream = null;
        try {
            InputStream input = null;
            input = file.getInputStream();
            BufferedImage bufImg = ImageIO.read(input);//把图片读入到内存中
            //压缩代码
            bufImg = Thumbnails.of(bufImg).size(bufImg.getWidth(), bufImg.getHeight()).outputQuality(1.0).asBufferedImage();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufImg, "jpg", imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            MultipartFile multipartFile = new MockMultipartFile("temp.jpg","temp.jpg","", is);

            String fileName = multipartFile.getOriginalFilename();
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
            fileName = new Date().getTime() + "." + prefix;
            fis = multipartFile.getInputStream();
            outputStream = new FileOutputStream(path + "/" + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream, 1024);
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bos.close();
            fis.close();
            String url = imageUrlPrefix+"/" + fileName;
            return  url;
        } catch (Exception e) {
            logger.error("图片上传失败");
            e.printStackTrace();
        }
        return null;
    }
}
