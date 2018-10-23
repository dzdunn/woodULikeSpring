package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.user.UserService;
import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.Formatter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final List<String> countries = Arrays.stream(Locale.getISOCountries())
                                                    .map(x -> new Locale("", x)
                                                    .getDisplayCountry()).sorted(Comparator.naturalOrder())
                                                    .collect(Collectors.toList());


    @RequestMapping(value = ViewName.REGISTER, method = RequestMethod.GET)
    public ModelAndView showRegister(@RequestParam(value = "error", required = false) Boolean error){
        ModelAndView mav = new ModelAndView(ViewName.REGISTER);
        mav.addObject("countries", countries);


        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.REGISTER_PROCESS, method = RequestMethod.POST)
    public ModelAndView registerProcess(@ModelAttribute("woodulikeUser") WoodulikeUser woodulikeUser){
        ModelAndView mav = new ModelAndView();
        woodulikeUser.setUserRoles(Arrays.asList(new UserRole("ROLE_USER")));
        woodulikeUser.setPassword(passwordEncoder.encode(woodulikeUser.getPassword()));
        woodulikeUser.setEnabled(true);
        woodulikeUser.setAccountNonExpired(true);
        woodulikeUser.setCredentialsNonExpired(true);
        woodulikeUser.setAccountNonLocked(true);
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
