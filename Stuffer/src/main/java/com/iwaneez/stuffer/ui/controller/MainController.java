package com.iwaneez.stuffer.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private static final String VIEW_LOGIN = "login";
    private static final String VIEW_ADMIN = "admin";
    private static final String VIEW_ERROR_403 = "error/403";
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        log.info("login - Received attempt to login");
        
        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        
        return VIEW_LOGIN;
    }
    
    @RequestMapping(value = "/403")
    public String showError403Page(HttpServletRequest request, Model model) {
        return VIEW_ERROR_403;
    }
    
    @RequestMapping(value = "/admin/**")
    public String showAdminPage() {
        return VIEW_ADMIN;
    }

}
