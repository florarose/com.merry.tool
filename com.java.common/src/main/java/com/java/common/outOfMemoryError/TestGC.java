package com.java.common.outOfMemoryError;

/**
 * @author ldt merry
 * @date 2020/4/25
 */
public class TestGC {

    private static final int _1MB = 1024*1024;
    /**
     * testALLocation()中，尝试分配3个2MB大小和1个4MB大小的对象， 在运行时通过-Xms20M、-Xmx20M、-Xmn10M这3个参数限制了Java堆大小为20MB，
     * 不可扩 展，其中10MB分配给新生代，剩下的10MB分配给老年代。-XX：SurvivorRatio=8决定了新 生代中Eden区与一个Survivor区的空间比例是8:1，
     * 从输出的结果也可以清晰地看到“eden space 8192K、from space 1024K、to space 1024K”的信息，新生代总可用空间为 9216KB（Eden区+1个Survivor区的总容量）。
     * 执行testAllocation（）中分配allocation4对象的语句时会发生一次Minor GC，这次GC的 结果是新生代6651KB变为148KB，
     * 而总内存占用量则几乎没有减少（因为allocation1、 allocation2、allocation3三个对象都是存活的，虚拟机几乎没有找到可回收的对象）。
     * 这次 GC发生的原因是给allocation4分配内存的时候，发现Eden已经被占用了6MB，剩余空间已不 足以分配allocation4所需的4MB内存，
     * 因此发生Minor GC。GC期间虚拟机又发现已有的3个 2MB大小的对象全部无法放入Survivor空间（Survivor空间只有1MB大小），所以只好通过分 配担保机制提前转移到老年代去。
     *
     * 这次GC结束后，4MB的allocation4对象顺利分配在Eden中，因此程序执行完的结果是 Eden占用4MB（被allocation4占用），Survivor空闲，老年代被占用6MB（被allocation1、
     * allocation2、allocation3占用）。通过GC日志可以证实这一点。
     */
    /**
     *  vm 参数，-verbose : gc-Xms20M-Xmx20M-Xmn10-XX : +PrintGCDetails   -XX: SurvivorRatio=8
     */
    public static void testALLocation(){
        byte [] allocation1, allocation2,allocation3,allocation4;
        allocation1 = new byte[2*_1MB];
        allocation2 = new byte[2*_1MB];
        allocation3 = new byte[2*_1MB];
        allocation4 = new byte[4*_1MB];    // 出现一次Minor GC
    }
}
