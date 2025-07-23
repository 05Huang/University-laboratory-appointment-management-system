package com.mafei.laboratory.page.controller;

import com.mafei.laboratory.commons.enums.TypeEnum;
import com.mafei.laboratory.page.vo.TypeVo;
import com.mafei.laboratory.system.entity.SysDept;
import com.mafei.laboratory.system.service.SysDeptService;
import com.mafei.laboratory.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wutangsheng
 * @create 2021-03-09 15:39
 * @info
 */
@Controller
@RequestMapping("/apparatus")
public class ApparatusController {
    private final String prefix = "apparatus";
    @Autowired
    private SysUserService userService;

    @Autowired
    private SysDeptService deptService;

    @GetMapping("/info")
    public String info(Model model) {
        List<TypeVo> list = new ArrayList<>(11);
        for (TypeEnum type : TypeEnum.values()) {
            list.add(new TypeVo(type.getType(), type.getValue()));
        }
        List<SysDept> all = deptService.findAll();
        model.addAttribute("depots", all);
        model.addAttribute("types", list);
        return prefix + "/info";
    }


    @GetMapping("/position")
    public String position() {
        return prefix + "/position";
    }

    @GetMapping("/repair")
    public String repair(Model model) {
        return prefix + "/repair";
    }

    @GetMapping("/use")
    public String use(Model model) {
        return prefix + "/use";
    }

    @GetMapping("/import")
    public String importRepair(Model model) {
        return prefix + "/import";
    }
}
