package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.LockTable;
import com.example.demo.mapper.LockTableMapper;
import com.example.demo.service.ILockTableService;
import org.springframework.stereotype.Service;

@Service
public class LockTableServiceImpl extends ServiceImpl<LockTableMapper, LockTable>
        implements ILockTableService {


}
