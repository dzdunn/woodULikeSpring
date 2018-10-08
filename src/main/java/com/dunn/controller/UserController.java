package com.dunn.controller;

import com.dunn.dao.user.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private IUserDAO userDAO;
}
