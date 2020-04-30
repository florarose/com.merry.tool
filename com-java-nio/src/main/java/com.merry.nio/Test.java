package com.merry.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/28 17:56
 */
public class Test {

    /**
     * @param file
     * @throws IOException
     */
    public void nioRead(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
         //1. 获取通道,从FileInputStream 中获取
        FileChannel fileChannel  = in.getChannel();
        //2. 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 3. 将数据从通道读取/写入 到缓冲区
        fileChannel.read(byteBuffer);
        byte[] b = byteBuffer.array();
    }

    private static void ioRead(String file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] b = new byte[1024];
        in.read(b);
        System.out.println(new String(b));
    }
    /**
     *  io 是直接创建字符数组，然后通过FileInputStream 写入;
     *  Nio  增加缓冲，最后从缓冲区取数组。
     *
     *  在面向流的IO中，将数据直接写入或者将数据读到stream对象中，
     *  在NIO，所有的对象都是用缓冲区处理的，读入写入数据都放到缓冲区中，
     *  任何时候访问NIO中的数据，都是将它放入缓冲区，缓冲区实际上是一个字节数组，但不仅仅是一个数组，缓冲区还提供了对数据的结构化访问，而且还可以跟踪系统的读写进程。
     *
     *  操作缓冲区的方法：
     *      clear(), 重设缓冲区，可以接受读入的数据;
     *      flip() ， 让缓冲区可以将新读入的数据，写入到另一个通道中。
     */


    /**
     *  文件拷贝
     * @param file
     * @throws IOException
     */
    private static void NIOCopy(String file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        FileInputStream in = new FileInputStream(file);
        //获取输入和输出通道
        FileChannel outChannel = out.getChannel();
        FileChannel inChannel = in.getChannel();
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(true){
            //clear 方法重设缓冲区，使它可以接收读入的数据
            buffer.clear();
            //从输入通道将数据读到缓冲区
            int r = inChannel.read(buffer);
            //read 方法，返回读取的字节数，可能为零，如果该通道已经到达流的末尾，则返回-1
            if(r == -1){
                break;
            }
            //flip 方法，让缓冲区可以将新读入的数据，写入到另一个通道中
            buffer.flip();
            //从输出通道中将数据写入缓冲区
            outChannel.write(buffer);
        }
    }
}
