package com.example.demo.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public abstract class BaseOther extends Base {
    @Length(max = 50, message = "名称长度不能超过50")
    private String name;

    public abstract String getOwnId();

    public abstract void setOwnId(String v);


    public abstract String getParentId();

    public abstract void setParentId(String v);

    public abstract Integer getTag();

    public abstract void setTag(Integer v);

    public abstract BigDecimal getSort();

    public abstract void setSort(BigDecimal v);


}

