package com.java.common.SensitiveDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 13:51
 */
public class SensitiveWordInit {


    private String ENCODING = "UTF-8";    //字符编码


    @SuppressWarnings("rawtypes")
    public HashMap sensitiveWordMap;

    public SensitiveWordInit(){
        super();
    }

    /**
     * @author zhangyuntao
     * @date 2018年4月20日 下午2:28:32
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    public Map initKeyWord(){
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = {
     *      isEnd = 0
     *      国 = {<br>
     *           isEnd = 1
     *           人 = {isEnd = 0
     *                民 = {isEnd = 1}
     *                }
     *           男  = {
     *                 isEnd = 0
     *                  人 = {
     *                       isEnd = 1
     *                      }
     *              }
     *           }
     *      }
     *  五 = {
     *      isEnd = 0
     *      星 = {
     *          isEnd = 0
     *          红 = {
     *              isEnd = 0
     *              旗 = {
     *                   isEnd = 1
     *                  }
     *              }
     *          }
     *      }
     * @author zhangyuntao
     * @date 2018年4月20日 下午3:04:20
     * @param keyWordSet  敏感词库
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            //关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                //转换成char型
                char keyChar = key.charAt(i);
                //获取
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值
                if(wordMap != null){
                    nowMap = (Map) wordMap;
                }
                else{
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    public static void main(String[] args) {
        Set set = new HashSet();
        set.add("中国");
        set.add("中国人民");
        set.add("中国人");
        new SensitiveWordInit().addSensitiveWordToHashMap(set);
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     * @author zhangyuntao
     * @date 2018年4月20日 下午2:31:18
     * @return
     * @version 1.0
     * @throws Exception
     */
    @SuppressWarnings("resource")
    private Set<String> readSensitiveWordFile() throws Exception{
        Set<String> set = null;
        //读取文件
        File file=new File("C:\\Users\\merry\\Desktop\\dirty.txt");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
        try {
            //文件流是否存在
            if(file.isFile() && file.exists()){
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                //读取文件，将文件内容放入到set中
                while((txt = bufferedReader.readLine()) != null){
                    set.add(txt);
                }
            }
            else{
                //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        }finally{
            //关闭文件流
            read.close();
        }
        return set;
    }

}
