package com.springboot.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class errorController {
	@RequestMapping("/getUser")
	public String getUser(int i ) {
		int j=0;
		try {
		  j= 1/i;		
		} catch (Exception e) {
			return "系统错误！";
		}
		return "success"+j;	
	}
		
}
