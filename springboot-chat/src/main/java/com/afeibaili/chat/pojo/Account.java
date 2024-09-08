package com.afeibaili.chat.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName account
 */
@Data
public class Account implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String password;

    private String gender;

    private String email;

    private Long createDate;

    private Long birthDate;

    private Integer level;

    private String cookie;

    private String avatarBase64;
}