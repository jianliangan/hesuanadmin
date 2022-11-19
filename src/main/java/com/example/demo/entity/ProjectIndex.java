package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class ProjectIndex extends Base {
    @TableId(type = IdType.AUTO)
    private Integer indexId;

    private String projectId;
    @Length(max = 50, message = "名称长度不能超过50")
    private String nodeName;
    @Length(max = 50, message = "备注长度不能超过200")
    private String comment;
    private BigDecimal sort;
    private Integer parentId;

    @Override
    public Object fetchPrimeId() {
        return indexId;
    }

    @Override
    public void pushPrimeId(Object value) {
    }

    @Override
    public Object fetchParentId() {
        return parentId;
    }
}
