package com.afeibaili.chat.config;

import com.afeibaili.chat.controller.PublicChatMessageWebSocketController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author AfeiB
 * @description WebSocket配置类
 * @createDate 2024/8/23 下午4:12
 */

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
        serverEndpointExporter.setAnnotatedEndpointClasses(PublicChatMessageWebSocketController.class);
        return serverEndpointExporter;
    }
}
