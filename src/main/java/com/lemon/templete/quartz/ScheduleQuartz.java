package com.lemon.templete.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 描述：定时器类
 * @author 汤中流
 * @date 2019/07/30
 */
@Component
@Configurable
@EnableScheduling
public class ScheduleQuartz {
    // 日志对象
    private static final Logger logger = LoggerFactory.getLogger(ScheduleQuartz.class);

    // 根据cron表达式规则执行处理（该例为每5秒执行一次）
    @Scheduled(cron = "*/5 * * * * *")
    public void doScheduleTaskByCron() {
        logger.info("定时器每间隔5秒运行一次！！！");
    }
}
