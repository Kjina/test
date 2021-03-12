package com.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.accounts.AccountService;
import com.test.accounts.AccountVo;

@Controller
public class UserController {

	@Autowired
	private AccountService accountService;
	
    @RequestMapping(value = "/", method = { RequestMethod.GET })
    public ModelAndView home() {
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("index");
    	
    	return mv;
    }
    
    @GetMapping("/login")
    public String loginView() {
        return "user/login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }
    
    @PostMapping("/user/signup")
    public String signup(AccountVo accountVo) {
    	accountService.save(accountVo);
        return "redirect:/login";
    }
 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminView() {
        return "user/admin";
    }
}
