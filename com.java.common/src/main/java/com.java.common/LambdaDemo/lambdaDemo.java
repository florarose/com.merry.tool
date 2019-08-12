package com.java.common.LambdaDemo;


import lombok.Getter;

/**
 * @author liudongting
 * @date 2019/8/12 10:40
 */

public class lambdaDemo {

    @Getter
   private String firstName;
    @Getter
   private String name;
    @Getter
   private int age;

    public lambdaDemo() {
    }

    public lambdaDemo(String firstName, String name, int age) {
        this.firstName = firstName;
        this.name = name;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
