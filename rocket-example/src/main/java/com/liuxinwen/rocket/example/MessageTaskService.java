package com.liuxinwen.rocket.example;

import com.liuxinwen.rocket.core.task.TaskService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lxw
 * @date 2018/12/1717:57
 */
@Service
@Slf4j
public class MessageTaskService implements TaskService<Message> {

    @Autowired
    private MessageService messageService;

    @Override
    @SneakyThrows(InterruptedException.class)
    public void execTask(Message message) {
        TimeUnit.MICROSECONDS.sleep(new Random().nextInt(1000));
        this.messageService.send(message);
    }
}
