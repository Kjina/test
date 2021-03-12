package com.test.index;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = { RequestMethod.GET })
    public String home() {
    	 return "index";
    }

    @GetMapping("/login")
    public String loginView() {
        return "/user/login";
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/account")
    public String userInfoView() {
        return "user/account";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminView() {
        return "user/admin";
    }

    @GetMapping("/denied")
    public String deniedView() {
        return "user/denied";
    }
}
