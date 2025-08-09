package com.mafei.laboratory.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access")
public class AccessController {

    /**
     * 门禁动画页面
     */
    @GetMapping("/animation")
    public String animation() {
        return "access/animation";
    }
} 