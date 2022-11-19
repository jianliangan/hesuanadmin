package com.example.demo.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.system.Logs;
import com.example.demo.service.system.ILogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LogClear {
    @Value("${mylog.leave}")
    private int leave;
    @Autowired
    private ILogsService logsService;

    //凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void run() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QueryWrapper wrapper = new QueryWrapper();
        List orderColumn = new ArrayList<>();
        orderColumn.add("datetime");
        wrapper.orderBy(true, true, orderColumn);
        wrapper.lt("datetime", sdf.format(System.currentTimeMillis() - leave * 24 * 60 * 60 * 1000));
        wrapper.last("limit 0,200");
        while (true) {
            List<Logs> list = logsService.list(wrapper);
            if (list.size() == 0)
                break;
            List tmptime = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                tmptime.add(list.get(i).getDatetime());
            }
            wrapper.clear();
            wrapper.in("datetime", tmptime.toArray());
            logsService.remove(wrapper);
        }

    }
}
