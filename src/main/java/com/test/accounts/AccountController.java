package com.test.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@ExceptionHandler
    @RequestMapping(value = "/", method = { RequestMethod.GET })
    public ModelAndView home() {
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("index");
    	
    	return mv;
    }
    
	@ExceptionHandler
    @GetMapping("/login")
    public String loginView() {
        return "user/login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }
    
    @ExceptionHandler
    @PostMapping("/signup")
    public String signup(AccountDto accountVo) {
    	accountService.save(accountVo);
        return "redirect:/login";
    }
}
