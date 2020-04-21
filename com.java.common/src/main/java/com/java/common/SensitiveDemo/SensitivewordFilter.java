package com.java.common.SensitiveDemo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 13:56
 */
public class SensitivewordFilter {

    @SuppressWarnings("rawtypes")
    private Map sensitiveWordMap = null;
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则

    /**
     * 构造函数，初始化敏感词库
     */
    public SensitivewordFilter(){
        sensitiveWordMap = new SensitiveWordInit().initKeyWord();
    }

    /**
     * 判断文字是否包含敏感字符

     * @param txt  文字
     * @param matchType  匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public boolean isContaintSensitiveWord(String txt,int matchType){
        boolean flag = false;
        for(int i = 0 ; i < txt.length() ; i++){
            //判断是否包含敏感字符
            int matchFlag = this.CheckSensitiveWord(txt, i, matchType);
            //大于0存在，返回true
            if(matchFlag > 0){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词

     * @param txt 文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public Set<String> getSensitiveWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
            if(length > 0){    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符

     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @version 1.0
     */
    public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串

     * @param replaceChar
     * @param length
     * @return
     * @version 1.0
     */
    private String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>

     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes"})
    public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(SensitivewordFilter.minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }

    public static void main(String[] args) {
        SensitivewordFilter filter = new SensitivewordFilter();
        System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
        String string = "新华社乌鲁木齐9月12日电（记者曹志恒）新疆吐鲁番地区中级人民法院12日对鄯善县“6·26”暴力恐怖案件中艾合买提尼亚孜·斯迪克等4名被告人一审公开开庭审理并当庭宣判，以组织领导恐怖组织罪、故意杀人罪、放火罪数罪并罚，判处艾合买提尼亚孜·斯迪克死刑，剥夺政治权利终身；以参加恐怖组织罪、故意杀人罪、放火罪数罪并罚"
                + "，判处吾拉音·艾力死刑，剥夺政治权利终身；以参加恐怖组织罪、故意杀人罪数罪并罚"
                + "，判处阿不都拉·斯热甫力死刑，剥夺政治权利终身；以参加恐怖组织罪、故意杀人罪、放火罪数罪并罚，判处艾克拉木·吾斯曼有期徒刑25年，剥夺政治权利5年。\r\n" +
                "法庭审理查明，2010年4月以来，艾克拉木·吾斯曼、吾拉音·艾力、阿不都拉·斯热甫力先后与"
                + "艾力·艾合买提尼亚孜等数人在鄯善县鲁克沁镇多次聚集，从事非法宗教活动，宣扬宗教极端思想，收听观看境外恐怖组织煽动实施暴力恐怖活动的音视频，"
                + "传看宣扬宗教极端思想的书籍，接受宗教极端思想并就进行暴力恐怖活动达成共谋，进行暴恐体能训练，恐怖组织逐渐形成个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";
        System.out.println("待检测语句字数：" + string.length());
        long beginTime = System.currentTimeMillis();
        Set<String> set = filter.getSensitiveWord(string, 1);
        long endTime = System.currentTimeMillis();
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
        System.out.println("总共消耗时间为：" + (endTime - beginTime));
    }
}
