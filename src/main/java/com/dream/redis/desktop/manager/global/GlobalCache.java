package com.dream.redis.desktop.manager.global;

import com.dream.redis.desktop.manager.dto.ServerDTO;
import com.dream.redis.desktop.manager.redis.Server;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dream
 * @version v1.0.0
 * @description 全局缓存
 * @createTime 2020/7/3 9:55 上午
 */
public class GlobalCache {

    /**
     * server map
     */
    private static Map<String, Server> redisServerMap = new HashMap<>();

    /**
     * 服务器列表
     */
    private static List<String> serverList = new ArrayList<>();

    /**
     * 当前 redis 连接
     */
    private static Jedis currentJedis;

    /**
     * 缓存 server
     * @param serverList
     */
    public static void cacheServer(List<Server> serverList) {
        serverList.forEach(server -> cacheServer(server));
    }

    /**
     * 缓存 server
     * @param server
     */
    public static void cacheServer(Server server) {
        ServerDTO serverDTO = server.getServerDTO();
        serverList.add(serverDTO.getName());
        redisServerMap.put(serverDTO.getName(), server);
    }

    /**
     * 缓存当前jedis
     * @param jedis
     */
    public static void cacheCurrentJedis(Jedis jedis) {
        currentJedis = jedis;
    }

    /**
     * 获取缓存的服务器列表
     * @return
     */
    public static List<String> getServerList() {
        return serverList;
    }

    /**
     * 获取缓存当前 redis 连接
     * @return
     */
    public static Jedis getCurrentJedis() {
        return currentJedis;
    }

    /**
     * 获取 jedis
     * @param name
     * @return
     */
    public static Jedis getJedis(String name) {
        Server server = redisServerMap.get(name);
        return server.getJedis();
    }
}
