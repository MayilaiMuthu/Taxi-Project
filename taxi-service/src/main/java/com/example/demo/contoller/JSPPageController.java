package com.example.demo.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@author MayilaiMuthu
 *@since 01-08-2013
 */

@Controller
@RequestMapping("/page")
public class JSPPageController {
	
	@GetMapping("/welcome-page")
	public String getWelcomePage() {
		return "welcome";
	}

}
