package com.dream.redis.desktop.manager;

import com.dream.redis.desktop.manager.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DesktopManagerApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(DesktopManagerApplication.class, MainView.class, args);
    }

    @Override
    public void beforeInitialView(Stage stage, ConfigurableApplicationContext ctx) {
        stage.setTitle("redis桌面管理器");
    }
}
