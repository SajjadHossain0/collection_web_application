package com.collection_web_application.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class userController {
    //Authenticated users page
    @GetMapping("")
    public String user(){

        return "user/user_page";
    }
}