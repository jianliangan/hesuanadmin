package com.example.demo.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.system.Logs;
import com.example.demo.mapper.system.LogsMapper;
import com.example.demo.service.system.ILogsService;
import org.springframework.stereotype.Service;

@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {
    

}
