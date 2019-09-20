package com.springboot.enmu.config;

/**
 * @author liudongting
 * @date 2019/9/10 12:03
 */
public final class EnumUtil {

    private EnumUtil() { /* no instance */ }

    /**
     * 在类中查找对应的值
     * @param classType 枚举类
     * @param value     value值
     * @param <E>       必须实现 Enumerable 接口
     * @return 枚举值
     */
    public static <E extends Enumerable> E of(Class<E> classType, int value) {
        for (E enumConstant : classType.getEnumConstants()) {
            if (value == enumConstant.getValue()) {
                return enumConstant;
            }
        }
        return null;
    }

    public static  Integer ofInt(Class<Enumerable> classType, String value) {
        for (Enumerable enumConstant : classType.getEnumConstants()) {
            if (value == enumConstant.getKey()) {
                return enumConstant.getValue();
            }
        }
        return null;
    }
}
