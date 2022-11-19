package com.example.demo.service.impl.dict;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.dict.Dict;
import com.example.demo.mapper.dict.DictMapper;
import com.example.demo.service.dict.IDictService;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Override
    public String checkCanDelete(Dict instan) {

        return null;
    }

}
