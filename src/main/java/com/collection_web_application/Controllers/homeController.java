package com.collection_web_application.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    //Unauthenticated users page
    @GetMapping("/")
    public String home(){

        return "home/home_page";
    }




}
