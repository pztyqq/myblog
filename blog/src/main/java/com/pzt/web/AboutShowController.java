package com.pzt.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 蒲正庭 on 2021/9/1
 */
@Controller
public class AboutShowController {
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
