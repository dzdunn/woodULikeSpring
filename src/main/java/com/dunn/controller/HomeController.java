package com.dunn.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dunn.controller.path.ViewName;

@Controller
@RequestMapping
public class HomeController {

	@RequestMapping(value={ViewName.HOMEPAGE, ViewName.HOME, "/"}, method=RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView(ViewName.HOMEPAGE);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails){
			mav.addObject("username", ((UserDetails)principal).getUsername());
		}
		return mav;
	}


	@RequestMapping(value=ViewName.ABOUT, method=RequestMethod.GET)
	public String about(){
		return ViewName.ABOUT;
	}

	@RequestMapping(value=ViewName.CONTACT, method = RequestMethod.GET)
	public String contact(){
		return ViewName.CONTACT;
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
