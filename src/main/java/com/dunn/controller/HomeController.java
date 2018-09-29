package com.dunn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dunn.controller.path.ViewName;

@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping(value= {"", ViewName.HOME}, method=RequestMethod.GET)
	public String home() {
		return "home";
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
