package com.dream.redis.desktop.manager.service;

/**
 * @author dream
 * @version v1.0.0
 * @description redis 服务器管理服务
 * @createTime 2020/7/3 9:48 上午
 */
public interface RedisServerManagerService {

    /**
     * 加载 redis 服务器列表
     */
    void loadRedisServerList();
}
