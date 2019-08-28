package com.java.common.MapDemo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liudongting
 * @date 2019/8/15 11:56
 */
public class demo_01 {

   public static void main(String[] args) {

      Map<String,Object> map = new HashMap<String,Object>();
      // java8之前。从map中根据key获取value操作可能会有下面的操作
      Object key = map.get("key");
      if (key == null) {
         key = new Object();
         map.put("key", key);
      }

       // java8之后。上面的操作可以简化为一行，若key对应的value为空，会将第二个参数的返回值存入并返回
//      Object key2 = map.computeIfAbsent("key", k -> new Object());

   }
}
