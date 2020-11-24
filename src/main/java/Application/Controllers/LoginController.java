package Application.Controllers;

import Application.Entities.User;
import Application.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("user") User user) {
        User userFromDb = (User) userService.loadUserByUsername(user.getUsername());

        if (userFromDb != null && user.getPassword().equals(userFromDb.getPassword())) {
            return "//main-page";
        } else {
            return "/login";
        }
    }
}
