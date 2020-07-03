package com.dream.redis.desktop.manager.util;

import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author dream
 * @version v1.0.0
 * @description
 * @createTime 2020/7/3 11:03 上午
 */
@Slf4j
public class RedisUtil {

    private RedisUtil() {}


    /**
     * 查询缓存并设置到 redis value中
     * @param key
     * @param jedis
     * @param redisValue
     * @return
     */
    public static void queryAndSetRedisValue(String key, Jedis jedis,
                                             TextArea redisValue) {
        log.info("[Method-queryAndSetRedisValue] 查询缓存, key: {}", key);
        if (jedis == null) {
            log.warn("[Method-queryAndSetRedisValue] jedis is null");
            return;
        }
        String value = jedis.get(key);
        log.info("[Method-query] 查询成功, key: {}, value: {}", key, value);
        redisValue.setText(JsonUtil.prettyJson(value));
    }
}
