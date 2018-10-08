package com.dunn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dunn.controller.path.ViewName;

@Controller
@RequestMapping(ViewName.HOME)
public class HomeController {

	@RequestMapping(value= ViewName.HOME, method=RequestMethod.GET)
	public String home() {
		return "home/home";
	}


	@RequestMapping(value=ViewName.ABOUT, method=RequestMethod.GET)
	public String about(){
		return "home/about";
	}

	@RequestMapping(value=ViewName.CONTACT, method = RequestMethod.GET)
	public String contact(){
		return "home/contact";
	}

	@RequestMapping(value="inputData", method=RequestMethod.POST)
	public ModelAndView testDetails(@RequestParam("name") String name, @RequestParam("age") String age) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("name", name);
		mav.addObject("age", age);
		mav.setViewName("nameAndAge");
		return mav;
	}

}
