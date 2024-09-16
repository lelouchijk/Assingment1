package com.sell.controller;

import com.sell.model.User;
import com.sell.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/LogIn")
public class LoginController {

    private final LoginService logInSer;

    private final HttpSession session;

    @Autowired
    public LoginController(LoginService logInSer, HttpSession session) {
        this.logInSer = logInSer;
        this.session = session;
    }


    @GetMapping("/index")
    public String showLogInPage(){
        return "logIn";
    }

    @PostMapping("/logIn")
    public String logInProcess(@RequestParam("email")String email,
                               @RequestParam("password")String pw,HttpSession session, Model model){
        User user = logInSer.checkLogIn(email, pw);

        if (user != null && user.getRole() != null) {
            session.setAttribute("loggedUser", user);

            String roleName = user.getRole().getRoleName();

            // Redirect user based on role
            if (roleName.equalsIgnoreCase("Admin")) {
                return "redirect:/adminSystem/index";
            } else if (roleName.equalsIgnoreCase("ShopAdmin")) {
                return "redirect:/shopAdminPage";
            } else if (roleName.equalsIgnoreCase("Delivery")) {
                return "redirect:/deliveryPage";
            } else if (roleName.equalsIgnoreCase("Customer")) {

                return "redirect:/userSystem/index";
            }
        }


        return "redirect:/LogIn/index";

    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user",new User());
        return "registerPage";

    }

    @PostMapping("/registerProcess")
    public String registerUser(@ModelAttribute("user")User user){
//        return (logInSer.saveUser(user))? "main" : "register";
        logInSer.saveUser(user);
        return "redirect:/LogIn/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/LogIn/index";
    }

}
