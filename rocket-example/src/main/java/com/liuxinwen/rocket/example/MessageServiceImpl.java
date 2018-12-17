package com.liuxinwen.rocket.example;

import org.springframework.stereotype.Service;

/**
 * @author lxw
 * @date 2018/12/1718:07
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public void send(Message message) {
        System.out.println("send message:" + message);
    }
}
