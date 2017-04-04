package ai.profX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
public class ViewController {
	
	@RequestMapping("/")
	public String showIndexPage(){
		return "index";
	}
	
	@RequestMapping("/play")
	public String showPlayPage(){
		return "play";
	}
	
	@RequestMapping("/aboutus")
	public String showAboutUsPage(){
		return "aboutus";
	}
	
	@RequestMapping("/admin")
	public String showAdminPage(){
		return "admin";
	}
}
