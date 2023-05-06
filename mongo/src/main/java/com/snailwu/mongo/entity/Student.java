package com.snailwu.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author WuQinglong
 */
@Getter
@Setter
@Document(collection = "student")
public class Student {

    @Id
    private Integer id;
    private String name;
    private Integer age;

}
