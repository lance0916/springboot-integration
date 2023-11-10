package com.rabbit.jmh.mapstruct;

/**
 * @author WuQinglong
 * @since 2023/11/7 9:58 AM
 */
public class ItemBean {

    private String name;
    private Integer age;

    public ItemBean() {
    }

    public ItemBean(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
