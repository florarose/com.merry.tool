package com.liquibase.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;


/**
 * @author ldt merry
 * @date 2019/10/23
 */
@TableName("users")
@Data
public class User  extends Model<User> {

    private static final long serialVersionUID = 1L;
    @Override
    protected Serializable pkVal() {  return this.id;  }

    private int id;
    private String name;
    private String password;
}
