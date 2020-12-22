package com.codeup.betterreads.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KeysController {

    @Value("${googleBooksAPIKey}")
    private String gbapikey;

    @Value("${nytapikey}")
    private String nytapikey;

    @Value("${filestackAPIKey}")
    private String filestackkey;

    @RequestMapping(path = "/keys.js", produces = "application/javascript")
    @ResponseBody
    public String apikeys(){
        return String.format("const googleBooksAPIKey='%s';%nconst nytAPIKey='%s';%nconst filestackAPIKey='%s';", gbapikey, nytapikey, filestackkey);
    }
}
