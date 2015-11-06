package com.checkdoc.controller;


import com.checkdoc.domain.Directory;
import com.checkdoc.domain.Role;
import com.checkdoc.domain.User;
import com.checkdoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/dashboard"})
    public String redirectToDashboard(Model model) {
        String hello = "hello";
        model.addAttribute("hello", hello);
        return "dashboard";
    }

    @RequestMapping(value = {"/signup"})
    public String showRegistrationPage(Model model) {
        return "registration";
    }

    @RequestMapping(value = {"/registration"})
    public String registerNewUser(@RequestParam("userName") String username, @RequestParam("email") String email, Model model) {
        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setRole(new Role("testrole", new HashSet<User>()));
        user.setDirectories(new HashSet<Directory>());
        //Don't know why exception was caused
        try {
            userService.add(user);
        } catch (Exception e) {
            return "errorpage";
        }
        return "success";
    }

    @RequestMapping(value = {"/success"})
    public String showSuccessPage(Model model) {
        return "success";
    }

    @RequestMapping(value = {"/errorpage"})
    public String showErrorPage(Model model) {
        return "errorpage";
    }


    //Testing method, where we use DAO to make sure that entities working with DB
    @RequestMapping(value = "/savetestuser")
    public String saveTestUser() {

        User user = new User();
        user.setEmail("testuser@gmail.com");
        user.setUserName("testing");
        user.setRole(new Role("testrole", new HashSet<User>()));
        user.setDirectories(new HashSet<Directory>());

        userService.add(user);

        return "dashboard";
    }
}
