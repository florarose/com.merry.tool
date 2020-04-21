package com.java.common.SensitiveDemo;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.*;
import java.util.*;
/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:26
 */

public class TreeBrother {
    //是否存在违禁词
    private  Boolean isExit;
    //记录树的最大长度
    private  int maxLength;
    //最大匹配位置
    private  int index;
    //树根索引
    private  Map<String, TreeNode> map;
    //记录关键词的位置
    private  List<String> indexList;
    //记录关键词
    private  List<String> valueList;
    //违禁词总数
    private  int wordCount;
    private  int articleLength;

    public static void main(String[] args) throws IOException {
        TreeBrother treeBrother = new TreeBrother();
        //加载树
        Long loadStartTime = new Date().getTime();
        treeBrother.readXlsBuildTree("F:\\words1.xls");
        treeBrother.readXlsBuildTree(1, 1,2,"F:\\words2.xls");
        treeBrother.readXlsBuildTree(1, 1,2,"F:\\words3.xls");
        treeBrother.readFileBuildTree("F:\\lex-main.lex");
        Long loadEndTime = new Date().getTime();
        //读取文章
        String str = treeBrother.readTxt("F:\\hello.txt");
        //查找树
        Long searchStartTime = new Date().getTime();
        treeBrother.findWordLocation(str);
        Long searchEndTime = new Date().getTime();
        //显示树的信息
        treeBrother.showInfo(loadEndTime-loadStartTime,searchEndTime-searchStartTime);
    }

    public TreeBrother(){
        initTreeHelper();
    }

    /**
     * TreeHelper初始化
     */
    public void initTreeHelper(){
        isExit=false;
        maxLength = 0;
        index=0;
        map = new HashMap<String, TreeNode>();
        wordCount=0;
        indexList = new ArrayList<String>();
        valueList = new ArrayList<String>();
        articleLength =0;
    }

    /**
     * 显示树的操作信息
     * @param loadTime      加载时间
     * @param searchTime    查询时间
     */
    public void showInfo(Long loadTime, Long searchTime){
        System.out.println("加载树的时间："+String.valueOf(loadTime)+"(ms)");
        System.out.println("查询树的时间："+String.valueOf(searchTime)+"(ms)");
        System.out.println("树的最大长度为："+maxLength);
        System.out.println("文章字数："+articleLength);
        System.out.println("禁词条数："+wordCount);
        System.out.print("关键词序列：");
        for(String st : indexList){
            System.out.print(st);
        }
        System.out.println();
        System.out.print("关键词队列：");
        for(String st : valueList){
            System.out.print(st);
        }
        System.out.println();
    }

    /**
     * 找出字符串中匹配关键词的位置
     * @param str   待处理字符串
     */
    public  void findWordLocation(String str){
        int first =0;
        String word = "";
        while(true){
            //从源字符串中取出小于或等于该树最大长度个数的字符串
            if(first + maxLength < str.length()){
                word = str.substring(first, first + maxLength);
            }else {
                if(first != str.length()-1) {
                    //直到最后一个退出匹配
                    if (first != str.length() - 2) {
                        word = str.substring(first, str.length());
                    } else {
                        break;
                    }
                }else {
                    break;
                }
            }

            TreeNode node = map.get(word.substring(0, 1));
            isExit = false;         //重置原始状态
            //树存在则查找
            if (null != node) {
                findWord(node, word, 0);
            }
            //index > 0 说明树干存在关键词
            if (0 != index) {
                String out = String.format("(%d,%d)",first,first+index );
                indexList.add(out);
                valueList.add(word.substring(first,first+index+1));
            } else {
                //index = 0 说明树叶存在关键词
                if(isExit) {
                    String out = String.format("(%d,%d)",first,first+index );
                    indexList.add(out);
                    valueList.add(word.substring(first,first+index+1));
                }
            }
            first++;            //字符串起始位置后移
            index =0;           //重置全局变量
            isExit =false;      //重置全局变量
        }
    }

    /**
     * 字符串挂载到树
     * @param word  源字符串
     */
    public  void dealWord(String word){
        String temp = word.substring(0,1);
        TreeNode node = map.get(temp);
        //树不存在则创建树根，再创建树
        if(null == node){
            TreeNode rootNode = new TreeNode();
            rootNode.setNodeName(temp);
            makeTree(rootNode, word, 0);
            map.put(temp, rootNode);
        }else{
            //树存在，则更新树节点
            makeTree(node,word,0);
        }
    }

    /**
     * 寻找子树匹配的关键词的位置
     * @param treeNode  树节点
     * @param word      待处理字符串
     * @param first     字符串的起始位置
     */
    public  void findWord(TreeNode treeNode, String word, int first){
        //取出起始字符串
        String temp = word.substring(first,first+1);
        //是否首字符串跟树根字符串相同
        if(temp.equals(treeNode.getNodeName()) && 0 == first){

            //是否起始字符串在字符的最后位置
            if(first != word.length() - 1) {
                //是否存在子节点
                if (null != treeNode.getChildNode()) {
                    //树干是否是关键词，不是则寻找下一个
                    if(1 != treeNode.getIsLast() ) {
                        first++;
                        findWord(treeNode, word, first);
                    }else {
                        //是树干节点则说明是关键词
                        index = first;
                        isExit = true;
                        return;
                    }
                } else {
                    //是叶节点则说明是关键词
                    index = first;
                    isExit = true;
                    return;
                }
            }
        }
        //是否叶节点
        if(null == treeNode.getChildNode()){
            //源字符串已经扫描完毕
            if(first == word.length()-2){
                //是叶节点则说明是关键词
                index = first;
                isExit=true;
                return;
            }
        } else {
            //遍历子节点
            TreeNode brotherNode = treeNode.getChildNode();
            while(null != brotherNode) {
                //存在路径
                if(temp.equals(brotherNode.getNodeName())) {
                    //是关键词树干节点或叶节点，记录下来
                    if(1 == brotherNode.getIsLast() || null == brotherNode.getChildNode()){
                        index = first;
                    }
                    //源字符串扫描完毕
                    if(first == word.length()-1) {
                        //子节点是树干关键字，记录下来
                        if(1 == brotherNode.getIsLast()) {
                            index = first;
                            isExit = true;
                            return;
                        }else {
                            //子节点是叶节点关键字，记录下来
                            if(null == brotherNode.getChildNode()){
                                index = first;
                                isExit = true;
                                return;
                            }else {
                                return;
                            }
                        }
                    }
                    //扫描下一个
                    first++;
                    findWord(brotherNode, word, first);
                    break;
                }
                brotherNode = brotherNode.getBrotherNode();
            }
        }
    }

    /**
     * 树的遍历
     * @param treeNode  根节点
     */
    public   void showTree(TreeNode treeNode){
        System.out.println(treeNode.getNodeName());
        //遍历兄弟节点
        if(null != treeNode.getBrotherNode()) {
            showTree(treeNode.getBrotherNode());
        }
        //遍历子节点
        if (null != treeNode.getChildNode()) {
            showTree(treeNode.getChildNode());
        }
    }

    /**
     * 创建树
     * @param treeNode  节点
     * @param word      待创建字符串
     * @param first     字符串的起始位置
     */
    public  void makeTree(TreeNode treeNode, String word, int first){
        int flag=0;                 //记录是否已存在兄弟节点，不存在flag=0,则创建兄弟节点
        //word字符扫描完毕就结束
        if(first == word.length()){
            return;
        }
        //取字符
        String temp = word.substring(first,first+1);
        //是否匹配当前节点
        if(temp.equals(treeNode.getNodeName())){
            flag =1;
            //字符游标是否到最后
            if(first != word.length()-1){
                first++;
                //子节点是否为空，空则创建子节点，非空则递归调用下一个节点
                if(null != treeNode.getChildNode()) {
                    makeTree(treeNode.getChildNode(), word, first);
                }else {
                    TreeNode childNode = new TreeNode();
                    childNode.setNodeName(word.substring(first,first+1));
                    childNode.setParentNode(treeNode);
                    treeNode.setChildNode(childNode);
                    makeTree(childNode, word, first);
                }
            }else {
                //关键词匹配最后的节点则标志为1
                if(0 != first){
                    treeNode.setIsLast(1);
                }
            }
        }
        TreeNode brotherNode = treeNode.getBrotherNode();           //获取兄弟节点
        //遍历兄弟节点
        while(null != brotherNode){
            //找到匹配节点
            if(temp.equals(brotherNode.getNodeName())){
                flag =1;
                //是否是最后一个字符
                if(first != word.length()-1){
                    first++;
                    makeTree(brotherNode, word, first);
                    break;
                }else {
                    //扫描到最后一个字符，说明该字符串挂载完毕，标志节点为关键节点
                    if(0 != first){
                        brotherNode.setIsLast(1);
                    }
                }
            }
            brotherNode = brotherNode.getBrotherNode();
        }
        //节点不存在，则创建兄弟节点
        if(0 == flag){
            brotherNode = new TreeNode();
            brotherNode.setNodeName(word.substring(first,first+1));
            brotherNode.setParentNode(treeNode.getParentNode());
            treeNode.setBrotherNode(brotherNode);
            //偏移下一个节点
            if(first != word.length()-1){
                makeTree(brotherNode, word, first);
            }else {
                brotherNode.setIsLast(1);
            }
        }
    }

    /**
     * 读取XLS文档（按行读取一行记录）
     * @param myPath  文档的路径
     */
    public  void readXlsBuildTree(String myPath) {
        Workbook readwb = null;
        try {
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            InputStream instream = new FileInputStream(myPath);
            readwb = Workbook.getWorkbook(instream);
            //Sheet的下标是从0开始
            //获取第一张Sheet表
            Sheet readsheet = readwb.getSheet(0);
            //获取Sheet表中所包含的总列数
            int rsColumns = readsheet.getColumns();
            //获取Sheet表中所包含的总行数
            int rsRows = readsheet.getRows();
            //获取指定单元格的对象引用
            String theWord =null;
            for (int i = 0; i < rsRows; i++) {
                for (int j = 0; j < rsColumns; j++) {
                    Cell cell = readsheet.getCell(j, i);
                    theWord =cell.getContents();
                    //如果theWord不为null和空，则加入Bloomfilter中
                    if (theWord != null && !theWord.trim().equals("")) {
                        //记录树的最大长度
                        if(maxLength < theWord.length()){
                            maxLength = theWord.length();
                        }
                        dealWord(theWord);  //挂载节点
                        wordCount++;        //记录禁词条数
                    }
                    //System.out.print(cell.getContents() + " ");
                }
                //System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readwb.close();
        }
    }/**
     * 读取Excel文档（按行读取一行记录）
     * @param myPath   文档的路径
     * @param startRow Excel开始读取的行  默认是最大的长度结束
     * @param startCol Excel开始读取的列
     * @param endCol   Excel结束读取的列
     */
    public  void readXlsBuildTree(int startRow, int startCol,int endCol,String myPath) {
        Workbook readwb = null;
        try {
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            InputStream instream = new FileInputStream(myPath);
            readwb = Workbook.getWorkbook(instream);
            //Sheet的下标是从0开始
            //获取第一张Sheet表
            Sheet readsheet = readwb.getSheet(0);
            //获取Sheet表中所包含的总列数
            int rsColumns = endCol;
            //获取Sheet表中所包含的总行数
            int rsRows = readsheet.getRows();
            //获取指定单元格的对象引用
            String theWord =null;
            for (int i = startRow; i < rsRows; i++) {
                for (int j = startCol; j < rsColumns; j++) {
                    Cell cell = readsheet.getCell(j, i);
                    theWord =cell.getContents();
                    //如果theWord不为null和空，则加入Bloomfilter中
                    if (theWord != null && !theWord.trim().equals("")) {
                        //记录树的最大长度
                        if(maxLength < theWord.length()){
                            maxLength = theWord.length();
                        }
                        dealWord(theWord);  //挂载节点
                        wordCount++;        //记录禁词条数
                    }
                    //System.out.print(cell.getContents() + " ");
                }
                //System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readwb.close();
        }
    }

    /**
     *读取文件创建树（按行读取一行记录）
     * @param path  路径
     */
    public void readFileBuildTree(String path) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream in = new FileInputStream(file);
        if (null == in) { //如果in为null，则返回
            return;
        }
        InputStreamReader reader = null;
        try {
            //创建输入流
            reader = new InputStreamReader(in, "UTF-8");
            BufferedReader buffReader = new BufferedReader(reader, 512);
            String theWord = null;
            do {
                theWord = buffReader.readLine();
                //如果theWord不为null和空，则加入Bloomfilter中
                if (theWord != null && !theWord.trim().equals("")) {
                    //记录树的最大长度
                    if(maxLength < theWord.length()){
                        maxLength = theWord.length();
                    }
                    dealWord(theWord);  //挂载节点
                    wordCount++;        //记录禁词条数
                }
            } while (theWord != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取txt
     * @param path  文件路径
     * @return
     * @throws IOException
     */
    public String readTxt(String path) throws IOException {
        File file = new File(path);
        FileInputStream fin = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader  buffer = new BufferedReader(reader,512);
        String theWord ;
        String str = "肯德基" ;
        do{
            theWord = buffer.readLine();
            str += theWord;
        } while (null != theWord);
        //记录文章长度
        articleLength = str.length();
        return  str;
    }

    public Boolean getIsExit() {return isExit;}

    public void setIsExit(Boolean isExit) {this.isExit = isExit;}

    public int getMaxLength() {return maxLength;}

    public void setMaxLength(int maxLength) {this.maxLength = maxLength;}

    public int getIndex() {return index;}

    public void setIndex(int index) {this.index = index;}

    public Map<String, TreeNode> getMap() {return map;}

    public void setMap(Map<String, TreeNode> map) {this.map = map;}

    public List<String> getIndexList() {return indexList;}

    public void setIndexList(List<String> indexList) {this.indexList = indexList;}

    public int getWordCount() {return wordCount;}

    public void setWordCount(int wordCount) {this.wordCount = wordCount;}

    public List<String> getValueList() {return valueList;}

    public void setValueList(List<String> valueList) {this.valueList = valueList;}
}

