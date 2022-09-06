package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Construction;

import com.example.demo.mapper.ConstructionMapper;

import com.example.demo.service.IConstructionService;

import org.springframework.stereotype.Service;

@Service
public class ConstructionServiceImpl extends ServiceImpl<ConstructionMapper, Construction> implements IConstructionService {

}
