package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ListInventory {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String tableId;
    private String tableType;

}
