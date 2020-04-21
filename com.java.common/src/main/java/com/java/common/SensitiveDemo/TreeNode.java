package com.java.common.SensitiveDemo;

import java.io.Serializable;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:25
 */
public class TreeNode implements Serializable {

    //是否是关键字结束节点
    private int isLast;
    //节点名
    private String nodeName;
    //父节点
    private TreeNode parentNode;
    //兄弟节点
    private TreeNode brotherNode;
    //子节点
    private TreeNode childNode;

    public int getIsLast() {return isLast;}

    public void setIsLast(int isLast) {this.isLast = isLast;}

    public String getNodeName() {return nodeName;}

    public void setNodeName(String nodeName) {this.nodeName = nodeName;}

    public TreeNode getParentNode() {return parentNode;}

    public void setParentNode(TreeNode parentNode) {this.parentNode = parentNode;}

    public TreeNode getBrotherNode() {return brotherNode;}

    public void setBrotherNode(TreeNode brotherNode) {this.brotherNode = brotherNode;}

    public TreeNode getChildNode() {return childNode;}

    public void setChildNode(TreeNode childNode) {this.childNode = childNode;}
}
