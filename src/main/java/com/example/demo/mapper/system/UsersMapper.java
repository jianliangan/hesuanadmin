package com.example.demo.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.system.Users;
import org.springframework.stereotype.Repository;

// public interface UserMapper extends BaseMapper<User>
@Repository
public interface UsersMapper extends BaseMapper<Users> {
}
