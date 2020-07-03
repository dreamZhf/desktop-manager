package com.dream.redis.desktop.manager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dream
 * @version v1.0.0
 * @description 服务器DTO
 * @createTime 2020/7/2 10:40 下午
 */
@Getter
@Setter
@ToString
public class ServerDTO {

    /**
     * 服务器名称
     */
    private String name;

    /**
     * 服务器地址
     */
    private String host;

    /**
     * 服务器端口
     */
    private int port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;
}
