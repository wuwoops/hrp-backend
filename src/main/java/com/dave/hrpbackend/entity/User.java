package com.dave.hrpbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"user\"")
public class User {
    private String id;
    private String userName;
    private String password;
    private String nickName;
    private Integer age;
    private String email;
    private String job;
    private String authorities;

}