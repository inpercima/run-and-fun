package net.inpercima.runandfun.web;

import java.io.IOException;
import java.net.MalformedURLException;

import net.inpercima.runandfun.service.RunAndFunService;
import net.inpercima.runandfun.service.RunAndFunServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunAndFunController {
	
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String getUser(@RequestParam(value = "code", required = true) String code) throws MalformedURLException, IOException {
    	RunAndFunService runAndFun = new RunAndFunServiceImpl();
    	return runAndFun.getAccessToken(code, "client_id", "client_secret", "http://localhost:8080/user");
    }
    
}