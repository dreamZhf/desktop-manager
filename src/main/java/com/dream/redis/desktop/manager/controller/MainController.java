package com.dream.redis.desktop.manager.controller;

import com.dream.redis.desktop.manager.dto.ServerDTO;
import com.dream.redis.desktop.manager.eventHandler.RedisServerTreeDoubleClickEventHandler;
import com.dream.redis.desktop.manager.global.GlobalCache;
import com.dream.redis.desktop.manager.redis.RedisServer;
import com.dream.redis.desktop.manager.redis.Server;
import com.dream.redis.desktop.manager.service.RedisServerManagerService;
import com.dream.redis.desktop.manager.util.RedisUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.*;

/**
 * @author dream
 * @version v1.0.0
 * @description 主界面controller
 * @createTime 2020/7/1 11:22 下午
 */
@Slf4j
@FXMLController
public class MainController implements Initializable {

    /**
     * redis key Text
     */
    @FXML
    private TextField redisKey;

    /**
     * redis value TextArea
     */
    @FXML
    private TextArea redisValue;

    /**
     * redis server 树
     */
    @FXML
    private TreeView redisServerTree;

    /**
     * redis 服务器管理 server
     */
    private RedisServerManagerService redisServerManagerService;

    /**
     * 根结点
     */
    private TreeItem<String> dummyRoot = new TreeItem<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //1. 初始化 redis 服务器树根结点
        initRedisServerTree();

        //2. 初始化 redis 服务器列表
        initRedisServerList();

        //3. 初始化redis服务器管理视图
        initRedisServerViewList();

        //4. 初始化redis 服务树的双击事件
        initRedisServerTreeDoubleClickEvent();
    }

    /**
     * 初始化 redis 服务器树
     */
    public void initRedisServerTree() {
        redisServerTree.setRoot(dummyRoot);
        redisServerTree.setShowRoot(false);
    }

    /**
     * 初始化 redis 的服务器列表
     */
    public void initRedisServerList() {
        redisServerManagerService.loadRedisServerList();
        return;
    }

    /**
     * 初始化redis服务器管理视图
     */
    public void initRedisServerViewList() {
        List<TreeItem<String>> serverItems = new ArrayList<>();
        GlobalCache.getServerList().forEach(server -> serverItems.add(new TreeItem<>(server)));
        dummyRoot.getChildren().addAll(serverItems);
    }

    /**
     * 初始化redis 服务树的双击事件
     */
    public void initRedisServerTreeDoubleClickEvent() {
        RedisServerTreeDoubleClickEventHandler handler =
                new RedisServerTreeDoubleClickEventHandler(redisServerTree, redisKey, redisValue);
        redisServerTree.setOnMouseClicked(handler);
    }

    /**
     * 查询事件
     * @param event
     */
    @FXML
    public void query(ActionEvent event) {
        String key = redisKey.getText();
        RedisUtil.queryAndSetRedisValue(key, GlobalCache.getCurrentJedis(), redisValue);
    }

    @FXML
    public void showNewServerWindows(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("新建服务器");
        stage.setWidth(200);
        stage.setHeight(300);

        GridPane gridPane = new GridPane();
        Label nameLabel = new Label();
        nameLabel.setText("名称: ");
        gridPane.add(nameLabel, 0, 0);
        TextField nameField = new TextField();
        gridPane.add(nameField, 1, 0);

        Label hostLabel = new Label();
        hostLabel.setText("地址: ");
        gridPane.add(hostLabel, 0, 1);
        TextField hostField = new TextField();
        gridPane.add(hostField, 1, 1);

        Label portLabel = new Label();
        portLabel.setText("端口: ");
        gridPane.add(portLabel, 0, 2);
        TextField portField = new TextField();
        gridPane.add(portField, 1, 2);

        Label pwdLabel = new Label();
        pwdLabel.setText("密码: ");
        gridPane.add(pwdLabel, 0, 3);
        TextField pwdField = new TextField();
        gridPane.add(pwdField, 1, 3);

        Button button = new Button();
        button.setText("保存");
        gridPane.add(button, 0, 4);
        button.setOnAction(e -> {
            ServerDTO serverDTO = new ServerDTO();
            serverDTO.setHost(hostField.getText());
            serverDTO.setName(nameField.getText());
            serverDTO.setPort(Integer.parseInt(portField.getText()));
            serverDTO.setPassword(pwdField.getText());
            addServer(serverDTO);
            stage.close();
        });

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * 添加 server
     * @param serverDTO
     */
    private void addServer(ServerDTO serverDTO) {
        dummyRoot.getChildren().add(new TreeItem<>(serverDTO.getName()));
        Server server = new RedisServer(serverDTO);
        GlobalCache.cacheServer(server);
        redisServerManagerService.saveRedisServer(serverDTO);
    }

    @Autowired
    public void setRedisServerManagerService(RedisServerManagerService redisServerManagerService) {
        this.redisServerManagerService = redisServerManagerService;
    }
}
