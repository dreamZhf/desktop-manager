package com.dream.redis.desktop.manager.eventHandler;

import com.dream.redis.desktop.manager.global.GlobalCache;
import com.dream.redis.desktop.manager.util.JsonUtil;
import com.dream.redis.desktop.manager.util.RedisUtil;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @author dream
 * @version v1.0.0
 * @description redis 服务树双击事件处理
 * @createTime 2020/7/3 10:28 上午
 */
@Slf4j
public class RedisServerTreeDoubleClickEventHandler implements EventHandler<MouseEvent> {

    /**
     * redis 服务树
     */
    private TreeView redisServerTree;

    /**
     * redis key
     */
    private TextField redisKey;

    /**
     * redis 值
     */
    private TextArea redisValue;

    public RedisServerTreeDoubleClickEventHandler(TreeView redisServerTree,
                                                  TextField redisKey,
                                                  TextArea redisValue) {
        this.redisServerTree = redisServerTree;
        this.redisKey = redisKey;
        this.redisValue = redisValue;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getClickCount() != 2) {
            return;
        }
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            TreeItem<String> item = (TreeItem<String>) redisServerTree.getSelectionModel().getSelectedItem();
            String name = item.getValue();
            List<String> serverList = GlobalCache.getServerList();
            if (serverList.contains(name)) {
                // 服务器
                connectRedis(item, name);
            } else {
                // key值
                this.query(item.getParent(), name);
            }
        }
    }

    /**
     * 连接 redis
     * @param item
     * @param serverName
     */
    private void connectRedis(TreeItem<String> item, String serverName) {
        Jedis jedis = GlobalCache.getJedis(serverName);
        if (jedis == null) {
            log.error("[Method-connectRedis] 连接redis 失败");
            return;
        }
        Set<String> keys = jedis.keys("*");
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        item.setExpanded(true);
        item.getChildren().clear();
        keys.forEach(key -> item.getChildren().add(new TreeItem<>(key)));
    }

    /**
     * 查询redis缓存
     * @param treeItem
     * @param key
     */
    private void query(TreeItem<String> treeItem, String key) {
        Jedis jedis = GlobalCache.getJedis(treeItem.getValue());
        if (jedis == null) {
            log.warn("jedis is null");
            return;
        }
        GlobalCache.cacheCurrentJedis(jedis);
        redisKey.setText(key);
        RedisUtil.queryAndSetRedisValue(key, jedis, redisValue);
    }
}
