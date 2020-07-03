package com.dream.redis.desktop.manager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dream
 * @version v1.0.0
 * @description redis server manager
 * @createTime 2020/7/3 4:59 下午
 */
@Getter
@Setter
@ToString
public class RedisServerEntity {

    /**
     * 主键ID
     */
    private Integer id;

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
    private String username;

    /**
     * 密码
     */
    private String password;
}
