package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Service.UserCollectionItemsService;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class homeController {

    @Autowired
    private UserCollectionService userCollectionService;
    @Autowired
    private UserCollectionItemsService userCollectionItemsService;

    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("allItems", userCollectionItemsService.getAllItems());

        return "home/home_page";
    }




}
