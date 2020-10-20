package Application.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        //USBConnector usbConnector = new USBConnector();

        return "home";
    }

//    @MessageMapping("/ws")
//    @SendTo("/button")
//    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
//        System.out.println("test");
//
//        headerAccessor.getSessionAttributes().put("content", message.getContent());
//        return message;
//    }
}
