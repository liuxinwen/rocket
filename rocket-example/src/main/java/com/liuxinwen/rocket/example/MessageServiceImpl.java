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
        /**
         * 模拟发送消息的业务处理操作
         */
        log.info("send message:" + message);
    }
}
