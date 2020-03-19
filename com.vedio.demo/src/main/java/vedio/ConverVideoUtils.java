package vedio;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author ldt merry
 * @date 2020/3/6
 */
public class ConverVideoUtils {

//    @Autowired
//    private AppProperties appProperties;

    protected final Logger logger = Logger.getLogger(ConverVideoUtils.class);


    /**
     * 转换视频格式
     *
     * @param sourceVideoPath 视频地址
     * @return
     */
    public String beginConver(String sourceVideoPath) {
        //转码格式
        String targetExtension = ".mp4";
        //是否删除原文
        Boolean isDeleteResult = false;
        File fi = new File(sourceVideoPath);
        String fileName = fi.getName();
        //文件名不带扩展名
        String fileRealName = fileName.substring(0, fileName.lastIndexOf("."));
        logger.info("接收到文件(" + sourceVideoPath + ")需要转换");
        if (!checkfile(sourceVideoPath)) {
            logger.error(sourceVideoPath + "文件不存在" + " ");
            return "";
        }
        long beginTime = System.currentTimeMillis();
        logger.info("开始转文件(" + sourceVideoPath + ")");
        String path = process(fileRealName, sourceVideoPath, targetExtension, isDeleteResult);
        if (StringUtils.isNotEmpty(path)) {
            logger.info("转换成功");
            long endTime = System.currentTimeMillis();
            long timeCha = (endTime - beginTime);
            String totalTime = sumTime(timeCha);
            logger.info("转换视频格式共用了:" + totalTime + " ");
            if (isDeleteResult) {
                deleteFile(sourceVideoPath);
            }
            return path;
        } else {
            return "";
        }
    }

    /**
     * 实际转换视频格式的方法
     *
     * @param fileRealName    文件名不带扩展名
     * @param sourceVideoPath 原文件地址
     * @param targetExtension 目标视频扩展名
     * @param isDeleteResult  转换完成后是否删除源文件
     * @return
     */
    private String process(String fileRealName, String sourceVideoPath, String targetExtension, boolean isDeleteResult) {
        int type = checkContentType(sourceVideoPath);
        String path = "";
        if (type == 0) {
            //如果type为0用ffmpeg直接转换
            path = processVideoFormat(sourceVideoPath, fileRealName, targetExtension, isDeleteResult);
        } else if (type == 1) {
            //如果type为1，将其他文件先转换为avi，然后在用ffmpeg转换为指定格式
            String aviFilePath = processAVI(fileRealName, sourceVideoPath);
            if (aviFilePath == null) {
                // avi文件没有得到
                return "";
            } else {
                logger.info("开始转换:");
                path = processVideoFormat(aviFilePath, fileRealName, targetExtension, isDeleteResult);
                if (isDeleteResult) {
                    deleteFile(aviFilePath);
                }
            }
        }
        return path;
    }

    /**
     * 检查文件类型
     *
     * @param sourceVideoPath 原文件地址
     * @return
     */
    private int checkContentType(String sourceVideoPath) {
        String type = sourceVideoPath.substring(sourceVideoPath.lastIndexOf(".") + 1).toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }

    /**
     * 检查文件是否存在
     *
     * @param path 文件地址
     * @return
     */
    private boolean checkfile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
     *
     * @param fileRealName    文件名不带扩展名
     * @param sourceVideoPath 原文件地址
     * @return
     */
    private String processAVI(String fileRealName, String sourceVideoPath) {
        /**
         * mencoder.exe的地址
         */
        String menCoderPath = "D:/FFmpeg/mencoder.exe";
        /**
         * 转码后的存放视频地址  avi格式
         */
        String videoFolder = "D:/PYAS/TMS/upload/video/";

        List<String> commend = new java.util.ArrayList<String>();
        commend.add(menCoderPath);
        commend.add(sourceVideoPath);
        commend.add("-oac");
        commend.add("mp3lame");
        commend.add("-lameopts");
        commend.add("preset=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add(videoFolder + fileRealName + ".avi");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            return videoFolder + fileRealName + ".avi";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换为指定格式
     * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
     *
     * @param oldFilePath     源文件地址
     * @param fileRealName    文件名不带扩展名
     * @param targetExtension 目标格式扩展名 .xxx
     * @return
     */
    private String processVideoFormat(String oldFilePath, String fileRealName, String targetExtension, Boolean isDeleteResult) {
        /**
         * ffmpeg.exe的地址
         */
        String ffmpegPath = "D:/FFmpeg/ffmpeg.exe";
        /**
         * 转码后的存放视频地址 mp4格式
         */
        String targetFolder = "D:/PYAS/TMS/upload/video/";
        if (!checkfile(oldFilePath)) {
            logger.error(oldFilePath + "文件不存在");
            return "";
        }
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegPath);
        commend.add("-i");
        commend.add(oldFilePath);
        commend.add("-vcodec");
        commend.add("mpeg4");
        commend.add("-q");
        commend.add("0");
        commend.add("-y");
        commend.add(targetFolder + fileRealName + targetExtension);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            String videoPath = targetFolder + fileRealName + targetExtension;
            String path = this.processVideoFormatH264(videoPath, ffmpegPath, targetFolder, targetExtension, isDeleteResult);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将mpeg4转为h264编码 为了支持播放器
     *
     * @param path
     * @param ffmpegPath
     * @return
     */
    private String processVideoFormatH264(String path, String ffmpegPath, String targetFolder, String targetExtension, Boolean isDeleteResult) {
        if (!checkfile(path)) {
            logger.error(path + "文件不存在");
            return "";
        }
        String newFilePath = targetFolder + UUID.randomUUID().toString() + targetExtension;
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegPath);
        commend.add("-i");
        commend.add(path);
        commend.add("-vcodec");
        commend.add("h264");
        commend.add("-q");
        commend.add("0");
        commend.add("-y");
        commend.add(newFilePath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            if (isDeleteResult) {
                deleteFile(path);
            }
            return newFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public int doWaitFor(Process p) {
        InputStream in = null;
        InputStream err = null;
        int exitValue = -1;
        try {
            in = p.getInputStream();
            err = p.getErrorStream();
            boolean finished = false;

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        in.read();
                    }
                    while (err.available() > 0) {
                        err.read();
                    }

                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            logger.error("doWaitFor();: unexpected exception - " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                logger.info(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
        return exitValue;
    }

    /**
     * 删除文件方法
     *
     * @param filepath
     */
    public void deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.delete()) {
            logger.info("文件" + filepath + "已删除");
        }
    }

    /**
     * 计算转码时间
     *
     * @param ms
     * @return
     */
    public String sumTime(long ms) {
        int ss = 1000;
        long mi = ss * 60;
        long hh = mi * 60;
        long dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second
                * ss;

        String strDay = day < 10 ? "0" + day + "天" : "" + day + "天";
        String strHour = hour < 10 ? "0" + hour + "小时" : "" + hour + "小时";
        String strMinute = minute < 10 ? "0" + minute + "分" : "" + minute + "分";
        String strSecond = second < 10 ? "0" + second + "秒" : "" + second + "秒";
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
                + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond + "毫秒" : ""
                + strMilliSecond + " 毫秒";
        return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " "
                + strMilliSecond;

    }

    public static void main(String[] args) {
        ConverVideoUtils converVideoUtils = new ConverVideoUtils();
        converVideoUtils.beginConver("D:/PYAS/TMS/upload/video/Imag.MOV");
    }
}
