package com.liuxinwen.rocket.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lxw
 * @date 2018/12/1718:07
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Override
    public void send(Message message) {
        log.info("send message:" + message);
    }
}
