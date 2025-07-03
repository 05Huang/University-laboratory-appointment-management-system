package com.mafei.laboratory.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wutangsheng
 * @create 2021-03-09 16:08
 * @info
 */
@Controller
@RequestMapping("/process")
public class ProcessController {
    private final String prefix = "process";

    @GetMapping("/borrowLog")
    public String borrowLog() {
        return prefix + "/borrowLog";
    }

    @GetMapping("/returnLog")
    public String returnLog() {
        return prefix + "/returnLog";
    }

    @GetMapping("/checkLaboratory")
    public String checkLaboratory() {
        return prefix + "/checkLaboratory";
    }

    @GetMapping("/checkApp")
    public String checkApparatus() {
        return prefix + "/checkApp";
    }
}
