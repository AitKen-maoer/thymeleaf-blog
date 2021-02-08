package com.vblog.web.admin;

import com.vblog.entity.User;
import com.vblog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping
    public String login(){
        return "/admin/login";
    }

    @PostMapping("/login")
    public String index(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        User user = userService.CheckUser(username, password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "/admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String loginout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
