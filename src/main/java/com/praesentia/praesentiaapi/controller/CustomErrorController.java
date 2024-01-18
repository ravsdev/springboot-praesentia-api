package com.praesentia.praesentiaapi.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String error(){
        //TO-DO
        return "Praesentia API: ERROR";
    }
}
