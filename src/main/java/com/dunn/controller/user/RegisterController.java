package com.dunn.controller.user;

import com.dunn.controller.uipaths.views.ViewName;
import com.dunn.dao.user.UserService;
import com.dunn.model.user.WoodulikeUser;
import com.dunn.validation.registration.IWoodulikeUserRegistrationValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private Validator passwordMatchValidator;
//
//    @InitBinder
//    private void initBinder(WebDataBinder binder) {
//        binder.setValidator(passwordMatchValidator);
//    }

    private static final List<String> COUNTRIES = Arrays.stream(Locale.getISOCountries())
                                                    .map(x -> new Locale("", x)
                                                    .getDisplayCountry()).sorted(Comparator.naturalOrder())
                                                    .collect(Collectors.toList());


    @RequestMapping(value = ViewName.REGISTER, method = RequestMethod.GET)
    public ModelAndView showRegister(@RequestParam(value = "error", required = false) Boolean error){
        ModelAndView mav = new ModelAndView(ViewName.REGISTER);
        mav.addObject("countries", COUNTRIES);


        mav.addObject("woodulikeUser", userService.constructNewWoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.REGISTER_PROCESS, method = RequestMethod.POST)
    public ModelAndView registerProcess(@ModelAttribute("woodulikeUser") @Validated(IWoodulikeUserRegistrationValidationGroup.class) WoodulikeUser woodulikeUser, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        if(bindingResult.hasErrors()){
            mav.addObject("countries", COUNTRIES);
            mav.setViewName(ViewName.REGISTER);
            return mav;
        }
//        woodulikeUser.setUserRoles(Arrays.asList(new UserRole("ROLE_USER")));
//        woodulikeUser.setPassword(passwordEncoder.encode(woodulikeUser.getPassword()));
//        woodulikeUser.setEnabled(true);
//        woodulikeUser.setAccountNonExpired(true);
//        woodulikeUser.setCredentialsNonExpired(true);
//        woodulikeUser.setAccountNonLocked(true);
        boolean isRegistered = userService.registerUser(woodulikeUser);
        if(isRegistered) {
           mav.setViewName("redirect:" + ViewName.LOGIN);
           return mav;
        } else {
           mav.setViewName("redirect:" + ViewName.REGISTER + "?error=true");
           return mav;
        }
    }
}
