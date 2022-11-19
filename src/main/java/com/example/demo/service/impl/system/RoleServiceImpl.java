package com.example.demo.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.system.Role;
import com.example.demo.mapper.system.RoleMapper;
import com.example.demo.service.system.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public String checkCanDelete(Role instan) {

        if (instan.fetchPrimeId().equals("1"))
            return "不能删除超级管理员";

        return null;
    }

    public boolean removeById(Role entity) {
        if (entity.getRoleId().equals("1")) {
            return false;
        }
        return super.removeById(entity);
    }

}
