package com.mafei.laboratory.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门禁控制器
 */
@Controller
@RequestMapping("/system/access")
public class SysAccessController {
    
    /**
     * 门禁动画演示页
     */
    @GetMapping("/animation")
    public String animation() {
        return "access/animation";
    }
} 