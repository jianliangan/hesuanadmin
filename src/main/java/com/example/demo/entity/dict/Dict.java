package com.example.demo.entity.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class Dict extends Base {
    @TableId(type = IdType.INPUT)
    private String dictId;
    @Length(max = 50, message = "名称长度不能超过50")
    private String dictName;
    @Length(max = 50, message = "名称类型长度不能超过50")
    private String typeName;

    private BigDecimal sort;
    private String ownId;
    private String parentId;
    private Integer tag;

    @Override
    public Object fetchPrimeId() {
        return dictId;
    }

    @Override
    public void pushPrimeId(Object value) {
        dictId = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return parentId;
    }
}
