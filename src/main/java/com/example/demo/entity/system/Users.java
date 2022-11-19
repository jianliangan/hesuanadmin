package com.example.demo.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class Users extends Base {
    @TableId(type = IdType.INPUT)
    private String usersId;
    @Length(max = 50, message = "名称长度不能超过50")
    private String usersName;
    @Length(max = 50, message = "密码长度不能超过50")
    private String passWord;
    @Length(max = 50, message = "手机长度不能超过50")
    private String phone;
    private String roleId;
    private BigDecimal sort;
    private String ownId;
    private String parentId;
    private Integer tag;

    @Override
    public Object fetchPrimeId() {
        return usersId;
    }

    @Override
    public void pushPrimeId(Object value) {
        usersId = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return parentId;
    }
}
