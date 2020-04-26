package com.java.common.outOfMemoryError;

/**
 * @author ldt merry
 * @date 2020/4/25
 */
public class TestBigGc5 {


    private static final int _1MB=1024*1024;
    /***VM参数：-verbose：gc-Xms20M-Xmx20M-Xmn10M-XX：+PrintGCDetails-XX：SurvivorRatio=8-XX：MaxTenuringThreshold=15
     *  *-XX：+PrintTenuringDistribution
     */
    /**
     *以-XX：MaxTenuringThreshold=1和-XX：MaxTenuringThreshold=15两 种设置来执行代码清单3-7中的testTenuringThreshold（）方法，
     * 此方法中的allocation1对象需 要256KB内存，Survivor空间可以容纳。当MaxTenuringThreshold=1时，allocation1对象在第二 次GC发生时进入老年代，
     * 新生代已使用的内存GC后非常干净地变成0KB。而 MaxTenuringThreshold=15时，
     * 第二次GC发生后，allocation1对象则还留在新生代Survivor空 间，这时新生代仍然有404KB被占用。
     *
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold(){
        byte[]allocation1,allocation2,allocation3,allocation4;
        //什么时候进入老年代取决于XX：MaxTenuringThreshold设置
        allocation1=new byte[_1MB/4];
        //allocation1+allocation2大于survivo空间一半
        allocation2=new byte[_1MB/4];
        allocation3=new byte[4*_1MB];
        allocation4=new byte[4*_1MB];
        allocation4=null;
        allocation4=new byte[4*_1MB];
    }
}
