package com.example.demo.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.system.Setting;
import com.example.demo.mapper.system.SettingMapper;
import com.example.demo.service.system.ISettingService;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting>
        implements ISettingService {
    @Override
    public String checkCanDelete(Setting instan) {

        QueryWrapper wrapper = new QueryWrapper<Setting>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        Setting child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

}
