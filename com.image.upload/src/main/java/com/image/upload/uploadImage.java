package com.image.upload;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.IOUtils;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 图片上传的方法
 */
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
    private String upload;
    private String show;
    /**
     * 压缩图片上传
     * @param image
     * @return
     */
    public String uploadImage(MultipartFile image){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String filePath = upload + sdf.format(new Date());
        File imgFolder = new File(filePath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        StringBuilder thumImgUrl = new StringBuilder();
        thumImgUrl.append(show);

        String imgName = UUID.randomUUID().toString() + ".jpg";

        try {
            byte[] ysbytes;
            try {
                ysbytes = compressImage(image.getInputStream(), 400);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(image.getOriginalFilename() + "图片压缩失败...",e);
                imgName += "_" + image.getOriginalFilename().replaceAll(" ", "");
                ysbytes = image.getBytes();
            }

            IOUtils.write(ysbytes, new FileOutputStream(new File(imgFolder, imgName)));

            thumImgUrl.append("/").append(imgName);
            logger.info("上传缩略图片："+thumImgUrl.toString());
            return thumImgUrl.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 图片压缩
     * @param fileStream
     * @param ppi
     * @return
     */
    public static byte[] compressImage(InputStream fileStream, int ppi) throws Exception {
        byte[] smallImage = null;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Thumbnails.of(fileStream).size(ppi, ppi).outputFormat("jpg").toOutputStream(out);
            smallImage = out.toByteArray();
            return smallImage;
        } catch (Exception e) {
            throw new Exception("图片压缩失败...");
        }
    }


    /**
     *  富文本图片上传
     * @param request
     * @param image
     * @return
     * @throws IOException
     */
    public String uploadImage(HttpServletRequest request, MultipartFile image) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String filePath = "/images/" + sdf.format(new Date());
        String imageFolderPath = request.getServletContext().getRealPath(filePath);
        File imageFolder = new File(imageFolderPath);
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }

        StringBuilder imageUrl= new StringBuilder();
        imageUrl.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(filePath);

        String imageName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imageFolder, imageName)));
            imageUrl.append("/").append(imageName);
            return imageUrl.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败!";
    }
}
