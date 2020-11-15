package Application.Configuration;

import Application.Handlers.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketButtonDataHandler(), "/button");
        registry.addHandler(new SocketJoyStickDataHandler(), "/joystick");
        registry.addHandler(new SocketSoundDataHandler(), "/sound");
        registry.addHandler(new SocketRedDataHandler(), "/red");
        registry.addHandler(new SocketGreenDataHandler(), "/green");
        registry.addHandler(new SocketBlueDataHandler(), "/blue");
        registry.addHandler(new SocketAngleDataHandler(), "/angle");
    }
}