package com.liuxinwen.rocket.example;

import com.liuxinwen.rocket.core.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lxw
 * @date 2018/12/1717:57
 */
@Service
public class MessageTaskService implements TaskService<Message> {

    @Autowired
    private MessageService messageService;

    @Override
    public void execTask(Message message) {
        this.messageService.send(message);
    }
}
