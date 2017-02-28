package ai.profX.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	//TODO : To implement respective services for the requests here
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String showIndex(){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/begin")
	public String begin(){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/answer/{answerId}")
	public String answer(@PathVariable("answerId") int answerId){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/guess/{guessId}")
	public String getGuess(@PathVariable("guessId") int guessId){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/guess/{guessId}")
	public String postGuess(@PathVariable("guessId") int guessId){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/learn")
	public String getLearnInfo(){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/learn")
	public String postLearnInfo(){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/restart")
	public String restart(){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public String admin(){
		return null;
	}

}
