package com.example.demo.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.entity.Base;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@TableName(autoResultMap = true)
@Data
public class Role extends Base {
    @TableId(type = IdType.INPUT)
    private String roleId;
    @Length(max = 50, message = "角色名称长度不能超过50")
    private String roleName;
    @Length(max = 500, message = "备注长度不能超过500")
    private String comment;
    private String author;

    private BigDecimal sort;
    private String ownId;
    private String parentId;
    private Integer tag;

    @Override
    public Object fetchPrimeId() {
        return roleId;
    }

    @Override
    public void pushPrimeId(Object value) {
        roleId = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return parentId;
    }
}
