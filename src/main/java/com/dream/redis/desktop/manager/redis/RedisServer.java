package com.dream.redis.desktop.manager.redis;

import com.dream.redis.desktop.manager.dto.ServerDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * @author dream
 * @version v1.0.0
 * @description redis server
 * @createTime 2020/7/3 9:31 上午
 */
@Slf4j
public class RedisServer implements Server {

    /**
     * 服务器dto
     */
    private ServerDTO serverDTO;

    /**
     * jedis 连接
     */
    private Jedis jedis;

    public RedisServer(ServerDTO serverDTO) {
        this.serverDTO = serverDTO;
    }

    /**
     * 连接 redis
     */
    @Override
    public void connect() {
        if (jedis != null) {
            log.error("[Method-connect] redis 已连接");
            return;
        }
        if (serverDTO == null) {
            log.error("[Method-connect] serverDTO is null");
            return;
        }
        if (StringUtils.isEmpty(serverDTO.getName())) {
            log.error("[Method-connect] 服务器名称不能为空");
            return;
        }
        if (StringUtils.isEmpty(serverDTO.getHost())) {
            log.error("[Method-connect] 服务器 host 地址不能为空");
            return;
        }
        if (serverDTO.getPort() <= 0) {
            log.error("[Method-connect] 服务器端口号不正确");
            return;
        }
        jedis = new Jedis(serverDTO.getHost(), serverDTO.getPort());
        if (!StringUtils.isEmpty(serverDTO.getUserName())
                && !StringUtils.isEmpty(serverDTO.getPassword())) {
            jedis.auth(serverDTO.getUserName(), serverDTO.getPassword());
            return;
        }
        jedis.auth(serverDTO.getPassword());
    }

    @Override
    public boolean isConnect() {
        return jedis != null;
    }

    @Override
    public Jedis getJedis() {
        if (jedis != null) {
            return jedis;
        }
        connect();
        return jedis;
    }

    @Override
    public ServerDTO getServerDTO() {
        return serverDTO;
    }
}
