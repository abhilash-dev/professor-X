package ai.profX.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	// TODO : To implement respective services for the requests here

	@RequestMapping(method = RequestMethod.POST, value = "/admin")
	public String admin() {
		// display admin page here
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dq")
	public String deleteQuestion() {
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/dq")
	public String answer(@PathVariable("answerId") int answerId) {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/do")
	public String getGuess(@PathVariable("guessId") int guessId) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/data")
	public String postGuess(@PathVariable("guessId") int guessId) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/retrain/{}")
	public String postLearnInfo() {
		return null;
	}
}
