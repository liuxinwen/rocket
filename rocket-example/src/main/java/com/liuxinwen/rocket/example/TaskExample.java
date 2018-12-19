package com.liuxinwen.rocket.example;

import com.liuxinwen.rocket.core.task.TaskExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxw
 * @date 2018/12/1717:55
 */
@Component
@EnableScheduling
public class TaskExample {
    @Autowired
    private TaskExecutorService taskExecutorService;
    @Autowired
    private MessageTaskService messageTaskService;

    @Scheduled(cron = "0/5 * * * * ?")
    void send() {
        int size = 100000;
        List<Message> messages = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            messages.add(Message.builder().id(i).body("message " + i).build());
        }
        this.taskExecutorService.executeTask("sendMessageTask", messageTaskService, messages);
    }
}
