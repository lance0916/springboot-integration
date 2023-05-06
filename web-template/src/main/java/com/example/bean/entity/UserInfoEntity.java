package com.example.bean.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author WuQinglong
 * @since 2022/11/29 13:36
 */
@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
