package com.checkdoc.controller;


import com.checkdoc.domain.User;
import com.checkdoc.exception.IncorrectPasswordException;
import com.checkdoc.service.UserService;
import com.checkdoc.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.security.pkcs11.wrapper.Functions;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/index"})
    public String redirectToDashboard(Model model) {
        String hello = "hello";
        model.addAttribute("hello", hello);
        return "index";
    }

    @RequestMapping(value = {"/signup"})
    public String showRegistrationPage(Model model) {
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String registerView() {
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerNewUser(@RequestParam("display_name") String username,
                                  @RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("password_confirmation") String passwordConfirmation,
                                  Model model) {
        try {
            userService.register(username, email, password, passwordConfirmation);
        } catch (IncorrectPasswordException e) {
            return "register";
        }
        return "index";
    }

    @RequestMapping(value = {"/forgetPassword"}, method = RequestMethod.GET)
    public String forgetPassword() {
        return "forgetPassword";
    }

    @RequestMapping(value = {"/forgetPassword"}, method = RequestMethod.POST)
    public String forgetPassword(@RequestParam("email") String email,
                                 Model model) {
        User user = userService.findUserByEmail(email);

        if (user != null) {
            final String username = "hookapplicationmain@gmail.com ";
            final String password = "hookppzpi14";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));
                message.setSubject("Greetings from CheckDoc..");
                message.setText("Here your credits" + "\nEmail :" + user.getEmail() +
                        "\nPassword :" + user.getPassword() +
                        "\nRegards, CheckDoc Admin");
                Transport.send(message);
                System.out.println("Done");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return "messageSend";
        }
        return "forgetPassword";
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model
            model) {
        System.out.println("Logining started");
        User user = userService.login(email, password);
        if (user == null) {
            System.out.println("Not logged");
            return "index";
        }
        ControllerUtil.session().setAttribute("currentUser", user);
        System.out.println("Logged");
        return "profile";
    }
}
