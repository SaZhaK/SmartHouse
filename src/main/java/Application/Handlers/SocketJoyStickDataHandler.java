package Application.Handlers;

import Application.USB.USBConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketJoyStickDataHandler extends TextWebSocketHandler {
    List sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        USBConnector connector = new USBConnector();
        if (message.getPayload().equals("start")) {
            connector.startSendingJoyStickData(session);
        } else if (message.getPayload().equals("stop")) {
            connector.stopSendingJoyStickData();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
}