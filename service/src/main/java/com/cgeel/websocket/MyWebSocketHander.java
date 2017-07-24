package com.cgeel.websocket;

import com.cgeel.model.Message;
import com.cgeel.model.Result;
import com.cgeel.model.SUser;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.socket.*;

import java.util.ArrayList;
import java.util.List;

public class MyWebSocketHander implements WebSocketHandler {
    private static final Logger logger = Logger.getLogger(MyWebSocketHander.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.debug("链接成功......");
        users.add(webSocketSession);
        String userName = (String) webSocketSession.getAttributes().get("WEBSOCKET_USERNAME");
        if(userName!= null){
            //查询未读消息
            int count = 5;
            webSocketSession.sendMessage(new TextMessage(count + ""));

            Result result = new Result();
            List<SUser> list = new ArrayList<SUser>();

            for(WebSocketSession u : users){
                SUser user = new SUser();
                user.setName((String)u.getAttributes().get("WEBSOCKET_USERNAME"));
                list.add(user);
            }

            result.setCode(1);
            result.setObj(list);

            Gson gson = new Gson();
            String json = gson.toJson(result);
            sendMessageToUsers(new TextMessage(json));
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        Result result = new Result();

        Message msg = new Message();
        msg.setFromName((String)webSocketSession.getAttributes().get("WEBSOCKET_USERNAME"));
        msg.setMessageText((String)webSocketMessage.getPayload());
        msg.setMessageDate(System.currentTimeMillis()/1000);

        result.setCode(2);
        result.setObj(msg);

        Gson gson = new Gson();
        String json = gson.toJson(result);

        sendMessageToUsers(new TextMessage(json));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.debug("链接出错，关闭链接......");
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.debug("链接关闭......" + closeStatus.toString());
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}