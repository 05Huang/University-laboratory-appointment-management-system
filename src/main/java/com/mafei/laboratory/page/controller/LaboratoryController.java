package com.mafei.laboratory.page.controller;

import com.mafei.laboratory.system.entity.SysDept;
import com.mafei.laboratory.system.service.SysDeptService;
import com.mafei.laboratory.system.service.SysLaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author wutangsheng
 * @create 2021-03-01 20:06
 * @info
 */
@Controller
@RequestMapping("/laboratory")
public class LaboratoryController {
    @Autowired
    private SysDeptService deptService;

    private final String prefix = "laboratory";

    @GetMapping("/info")
    public String info() {
        return prefix + "/info";
    }

    @GetMapping("/import")
    public String importData(Model model) {
        List<SysDept> all = deptService.findAll();
        model.addAttribute("depots", all);
        return prefix + "/import";
    }

    @GetMapping("/log")
    public String log() {
        return prefix + "/log";
    }

}
