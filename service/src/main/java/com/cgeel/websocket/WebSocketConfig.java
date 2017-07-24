package com.cgeel.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by Administrator on 2017-07-20.
 */
@Configuration
@EnableWebSocket//开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new MyWebSocketHander(),"/echo").addInterceptors(new MyHandshakeInterceptor()); //支持websocket 的访问链接
        webSocketHandlerRegistry.addHandler(new MyWebSocketHander(),"/sockjs/echo").addInterceptors(new MyHandshakeInterceptor()).withSockJS(); //不支持websocket的访问链接
    }
}
