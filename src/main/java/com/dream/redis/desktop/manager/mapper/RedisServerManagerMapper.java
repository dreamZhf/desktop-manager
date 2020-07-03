package com.dream.redis.desktop.manager.mapper;

import com.dream.redis.desktop.manager.entity.RedisServerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 赵海帆
 * @version v1.0.0
 * @description redis server manager mapper
 * @createTime 2020/7/3 5:06 下午
 */
@Mapper
public interface RedisServerManagerMapper {

    /**
     * 保存 redis server
     * @param redisServerEntity
     * @return
     */
    int save(RedisServerEntity redisServerEntity);

    /**
     * 查询所有的redis server
     * @return
     */
    List<RedisServerEntity> findAllRedisServer();
}
