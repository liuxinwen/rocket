package com.liuxinwen.rocket.example;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lxw
 * @date 2018/12/1717:57
 */
@Getter
@Setter
@Builder
@ToString
public class Message {
    private Integer id;
    private String body;
}
