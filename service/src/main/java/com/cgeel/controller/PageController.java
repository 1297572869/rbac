package com.cgeel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017-07-19.
 */
@Controller
@RequestMapping(value = "/web")
public class PageController {
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public String p2Page(@PathVariable String page){
        System.out.println("web");
        return page;
    }
}
