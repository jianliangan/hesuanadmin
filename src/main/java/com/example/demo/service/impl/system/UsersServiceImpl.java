package com.example.demo.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.system.Users;
import com.example.demo.mapper.system.UsersMapper;
import com.example.demo.service.system.IUsersService;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Override
    public String checkCanDelete(Users instan) {

        if (instan.fetchPrimeId().equals("1"))
            return "不能删除超级管理员";

        return null;
    }

}
