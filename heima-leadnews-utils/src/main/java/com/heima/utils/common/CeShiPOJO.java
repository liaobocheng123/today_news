package com.heima.utils.common;


/**
 * TODO
 *
 * @author QLP
 * @version 1.0
 * @date 2021/9/23 7:57
 */
public class CeShiPOJO{
    String name;
    String age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CeShiPOJO{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
