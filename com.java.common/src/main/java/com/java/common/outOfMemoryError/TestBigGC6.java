package com.java.common.outOfMemoryError;

/**
 * @author ldt merry
 * @date 2020/4/25
 */
public class TestBigGC6 {


    private static final int _1MB=1024*1024;
    /** *VM参数：-Xms20M-Xmx20M-Xmn10M-XX：+PrintGCDetails-XX：SurvivorRatio=8-XX：-HandlePromotionFailure */

    /**
     * 执行两次；
     * 一次：
     *     以HandlePromotionFailure=false参数来运行的结果：
     *  第二次：
     *     以HandlePromotionFailure=true参数来运行的结果：
     */
    @SuppressWarnings("unused")
    public static void testHandlePromotion(){
        byte[]allocation1, allocation2,allocation3,allocation4,allocation5,allocation6,allocation7 ;
        allocation1=new byte[2*_1MB];
        allocation2=new byte[2*_1MB];
        allocation3=new byte[2*_1MB];
        allocation1=null;
        allocation4=new byte[2*_1MB]; allocation5=new byte[2*_1MB];
        allocation6=new byte[2*_1MB];
        allocation4=null;
        allocation5=null;
        allocation6=null; allocation7=new byte[2*_1MB];
    }
}
