package com.java.common.outOfMemoryError;

/**
 * @author ldt merry
 * @date 2020/4/25
 */
public class TestBigGC {

    private static final int _1MB = 1024*1024;
    /**
     * testPretenureSizeThreshold（）方法后，我们看到Eden空间几乎没有 被使用，而老年代的10MB空间被使用了40%，
     * 也就是4MB的allocation对象直接就分配在老 年代中，这是因为PretenureSizeThreshold被设置为3MB（就是3145728，
     * 这个参数不能像-Xmx 之类的参数一样直接写3MB），因此超过3MB的对象都会直接在老年代进行分配。
     * 注意 PretenureSizeThreshold参数只对Serial和ParNew两款收集器有效，Parallel Scavenge收集器不 认识这个参数，Parallel Scavenge收集器一般并不需要设置。
     * 如果遇到必须使用此参数的场 合，可以考虑ParNew加CMS的收集器组合。
     */
    /**
     *  *VM参数：-verbose：gc-Xms20M-Xmx20M-Xmn10M-XX：+PrintGCDetails-XX：SurvivorRatio=8
     *  *-XX：PretenureSizeThreshold=3145728
     */
    public static void testALLocation(){
        byte [] allocation1, allocation2,allocation3,allocation4;
        allocation1 = new byte[2*_1MB];
        allocation2 = new byte[2*_1MB];
        allocation3 = new byte[2*_1MB];
        allocation4 = new byte[4*_1MB];    // 出现一次Minor GC
    }

    public static void main(String[] args) {
        testALLocation();
    }
}
