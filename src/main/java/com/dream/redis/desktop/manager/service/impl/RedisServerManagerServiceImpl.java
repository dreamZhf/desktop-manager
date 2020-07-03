package com.dream.redis.desktop.manager.service.impl;

import com.dream.redis.desktop.manager.dto.ServerDTO;
import com.dream.redis.desktop.manager.entity.RedisServerEntity;
import com.dream.redis.desktop.manager.global.GlobalCache;
import com.dream.redis.desktop.manager.mapper.RedisServerManagerMapper;
import com.dream.redis.desktop.manager.redis.RedisServer;
import com.dream.redis.desktop.manager.redis.Server;
import com.dream.redis.desktop.manager.service.RedisServerManagerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dream
 * @version v1.0.0
 * @description redis server 管理服务实现
 * @createTime 2020/7/3 10:21 上午
 */
@Service
public class RedisServerManagerServiceImpl implements RedisServerManagerService {

    /**
     * redis server 管理mapper
     */
    private RedisServerManagerMapper redisServerManagerMapper;

    @Override
    public void loadRedisServerList() {
        List<RedisServerEntity> redisServerEntities = redisServerManagerMapper.findAllRedisServer();
        if (CollectionUtils.isEmpty(redisServerEntities)) {
            return;
        }
        List<Server> servers = new ArrayList<>();
        redisServerEntities.forEach(redisServerEntity -> {
            ServerDTO serverDTO = convertRedisServerEntity(redisServerEntity);
            if (serverDTO == null) {
                return;
            }
            servers.add(new RedisServer(serverDTO));
        });
        GlobalCache.cacheServer(servers);
    }

    @Override
    public void saveRedisServer(ServerDTO serverDTO) {
        RedisServerEntity redisServerEntity = new RedisServerEntity();
        redisServerEntity.setName(serverDTO.getName());
        redisServerEntity.setHost(serverDTO.getHost());
        redisServerEntity.setPort(serverDTO.getPort());
        redisServerEntity.setUsername(serverDTO.getUserName());
        redisServerEntity.setPassword(serverDTO.getPassword());
        redisServerManagerMapper.save(redisServerEntity);
    }

    /**
     * 将 RedisServerEntity 转换为 ServerDTO
     * @param redisServerEntity
     * @return
     */
    private ServerDTO convertRedisServerEntity(RedisServerEntity redisServerEntity) {
        if (redisServerEntity == null) {
            return null;
        }
        ServerDTO serverDTO = new ServerDTO();
        serverDTO.setName(redisServerEntity.getName());
        serverDTO.setHost(redisServerEntity.getHost());
        serverDTO.setPort(redisServerEntity.getPort());
        serverDTO.setUserName(redisServerEntity.getUsername());
        serverDTO.setPassword(redisServerEntity.getPassword());
        return serverDTO;
    }

    @Autowired
    public void setRedisServerManagerMapper(RedisServerManagerMapper redisServerManagerMapper) {
        this.redisServerManagerMapper = redisServerManagerMapper;
    }
}
