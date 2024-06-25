package com.example.c23team2.Controllers;

import com.example.c23team2.Interfacemethods.LeaveTypeService;
import com.example.c23team2.Interfacemethods.LoginService;
import com.example.c23team2.Interfacemethods.UserService;
import com.example.c23team2.Models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class LoginController {
    @Autowired
    private UserService userInterface;

    @GetMapping(value = {"/", "/login", "/home"})
    public String login(Model model) {
        model.addAttribute("user", new User());

        return "login";
    }

    @RequestMapping(value = "/login")
    public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
                               HttpSession session, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User u = userInterface.authenticate(user.getAccount(), user.getPassword());

        if (u == null) {
            model.addAttribute("loginMessage", "Incorrect username/password");
            return "login";
        } else {
            session.setAttribute("user", u);

            String referer = request.getHeader("Referer");

            if (u.getRole() == 0) {
                return "redirect:/Admin/users";
            } else if (u.getRole() == 1) {
                return "redirect:/staff/leaveApplication/history";
            } else if (u.getRole() == 2) {
                if (referer != null && referer.startsWith("http://localhost:3000/")) {
                    return "redirect:/staff/leaveApplication/history";
                } else {
                    String redirectUrl = "http://localhost:3000?username=" + u.getUsername() + "&id=" + u.getId();
                    return "redirect:" + redirectUrl;
                }
            } else {
                model.addAttribute("loginMessage", "Unauthorized access");
                return "login";
            }
        }

    }

    @GetMapping("/user")
    public User getUserInfo(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
/*
@Controller
public class LoginController {
    @Autowired
    private UserService userInterface;

    @GetMapping(value = {"/", "/login", "/home"})
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "/login";
    }

    @RequestMapping(value = "/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model, HttpServletRequest request) {
        if (validateUser(user.getAccount(), user.getPassword())) {
            User u = userInterface.findByAccount(user.getAccount());
            session.setAttribute("user", u);

            String referer = request.getHeader("Referer");

            if (u.getRole() == 0) {
                return "redirect:/Admin/users";
            } else if (u.getRole() == 1) {
                return "redirect:/staff/leaveApplication/history";
            } else if (u.getRole() == 2) {
                if (referer != null && referer.startsWith("http://localhost:3000/")) {
                    return "redirect:/staff/leaveApplication/history";
                } else {
                    String redirectUrl = "http://localhost:3000?username=" + u.getUsername() + "&id=" + u.getId();
                    return "redirect:" + redirectUrl;
                }
            } else {
                model.addAttribute("loginMessage", "Unauthorized access");
                return "login";
            }
        } else {
            model.addAttribute("loginMessage", "Invalid username or password.");
            return "login";
        }
    }

    private boolean validateUser(String account, String password) {
        User user = userInterface.findByAccount(account);
        return user != null && user.getPassword().equals(password);
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    */