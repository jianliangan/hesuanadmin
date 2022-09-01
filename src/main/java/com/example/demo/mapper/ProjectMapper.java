package com.example.demo.mapper;

import com.example.demo.entity.Project;
import com.example.demo.vo.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectMapper {

    @Select("select * from project")
    List<Project> findAll();

    @Update("INSERT INTO `hesuan`.`project`(`project_name`, `province`, `city`, `region`, `start_time`," +
            "`complete_time`, `username`, `nature`, `category`, `category_detail`, `status`, `contract_price`," +
            "`final_time`, `estimate_income`, `estimate_cost`, `tax_way`)  VALUES (#{project_name}, " +
            "#{province}, #{city}, #{region}, #{start_time}, #{complete_time}," +
            "#{username}, #{nature}, #{category}, #{category_detail}, #{status}, #{contract_price}," +
            "#{final_time}, #{estimate_income},#{estimate_cost}, #{tax_way});")
    @Transactional
    void save(Project project);

    @Update("UPDATE `hesuan`.`project` SET `project_name` = #{project_name}, `province` = #{province}, `city` = #{city}, "
            +
            "`region` = #{region}, `start_time` = #{start_time}, `complete_time` = #{complete_time}," +
            " `username` = #{username}, `nature` = #{nature}, `category` = #{category}, `category_detail` = #{category_detail},"
            +
            "`status` = #{status}, `contract_price` = #{contract_price}, `final_time` = #{final_time}, " +
            "`estimate_income` = #{estimate_income}, `estimate_cost` = #{estimate_cost}, `tax_way` = #{tax_way}")
    @Transactional
    void updateById(Project project);

    @Delete("delete from project where id = #{id}")
    void deleteById(Long id);

    @Select("select * from project where id = #{id}")
    Project findById(Long id);

    @Select("select * from project limit #{offset},#{pageSize}")
    List<Project> findByPage(Integer offset, Integer pageSize);

    @Select("select count(*) from project")
    Integer countProject();
}
