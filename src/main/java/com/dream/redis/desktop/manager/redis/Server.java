package com.dream.redis.desktop.manager.redis;

import com.dream.redis.desktop.manager.dto.ServerDTO;
import redis.clients.jedis.Jedis;

/**
 * @author dream
 * @version v1.0.0
 * @description 服务器接口类
 * @createTime 2020/7/3 9:43 上午
 */
public interface Server {

    /**
     * 连接
     */
    void connect();

    /**
     * 判断是否已连接
     * @return
     */
    boolean isConnect();

    /**
     * 获取 jedis
     * @return
     */
    Jedis getJedis();

    /**
     * 获取 serverDTO
     * @return
     */
    ServerDTO getServerDTO();
}
