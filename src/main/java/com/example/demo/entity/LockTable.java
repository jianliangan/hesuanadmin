package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class LockTable extends Base {
    @TableId(type = IdType.INPUT)
    private String id;

    private Long budget;
    private Long plan;
    private Long actual;


    @Override
    public Object fetchPrimeId() {
        return id;
    }

    @Override
    public void pushPrimeId(Object value) {
        id = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return null;
    }
}
