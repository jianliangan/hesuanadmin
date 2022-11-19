package com.example.demo.service.impl.dict;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.dict.Materials;
import com.example.demo.mapper.dict.MaterialsMapper;
import com.example.demo.service.dict.IMaterialsService;
import org.springframework.stereotype.Service;

@Service
public class MaterialsServiceImpl extends ServiceImpl<MaterialsMapper, Materials>
        implements IMaterialsService {

}
