package com.example.demo.entity;

import lombok.Data;

@Data
public class Project {
    private Integer project_id;
    private String project_name;
    private Integer province;
    private Integer city;
    private Integer region;
    private String start_time;


    private String complete_time;
    private String username;
    private String nature;
    private String category;
    private String category_detail;

    private String status;
    private String contract_price;
    private String final_time;
    private String estimate_income;
    private String estimate_cost;
    private String tax_way;

}
