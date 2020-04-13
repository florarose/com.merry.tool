package vedio;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/13 11:14
 */
public class VideoUtil {
    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @param file  源视频文件
     * @param framefile  截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchPic(File file, String framefile) throws Exception{
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        ff.start();
        int lenght = ff.getLengthInFrames();

        File targetFile = new File(framefile);
        int i = 0;
        Frame frame = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            frame = ff.grabFrame();
            if ((i > 10) && (frame.image != null)) {
                break;
            }
            i++;
        }

        String imgSuffix = "jpg";
        if(framefile.indexOf('.') != -1){
            String[] arr = framefile.split("\\.");
            if(arr.length>=2){
                imgSuffix = arr[1];
            }
        }

        Java2DFrameConverter converter =new Java2DFrameConverter();
        BufferedImage srcBi =converter.getBufferedImage(frame);
        int owidth = srcBi.getWidth();
        int oheight = srcBi.getHeight();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
        try {
            ImageIO.write(bi, imgSuffix, targetFile);
        }catch (Exception e) {
            e.printStackTrace();
        }
        ff.stop();
    }

    /**
     * 获取视频时长，单位为秒
     * @param file
     * @return 时长（s）
     */
    public static Long getVideoTime(File file){
        Long times = 0L;
        try {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
            ff.start();
            times = ff.getLengthInTime()/(1000*1000);
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static void main(String[] args) {
//        File file = new File("C:\\Imag.mov");
//        System.out.println(getVideoTime(file)) ;
//        try {
//            fetchPic(file,"D:\\aa");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        System.out.println(-1 << (Integer.SIZE - 3));
        System.out.println(0 << (Integer.SIZE - 3));
        System.out.println(1 << (Integer.SIZE - 3));
      ExecutorService producerTheadPool = Executors.newFixedThreadPool(1);
      producerTheadPool.execute(null);
    }

    /**
     *   （当前线程等于停止状态，任务为null,工作队列不为空）三个条件有一个不满足，
     */
//    public void add(){
//        if (rs >= SHUTDOWN &&
//                ! (rs == SHUTDOWN &&
//                        firstTask == null &&
//                        ! workQueue.isEmpty())
//
//        )
//            d
//            return false;
//    }
}
