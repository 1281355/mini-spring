package com.rain.zbs.controllers;

import com.rain.zbs.beans.AutoWired;
import com.rain.zbs.servece.SalaryService;
import com.rain.zbs.web.mvc.Controller;
import com.rain.zbs.web.mvc.RequestMapping;
import com.rain.zbs.web.mvc.RequestParam;

/**
 * @author huangyu
 * @version 1.0
 * @date 2019/9/12 17:13
 */
@Controller
public class SalaryController {
    @AutoWired
    private SalaryService salaryService;

    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience){
        return salaryService.calSalary(Integer.parseInt(experience));
    }
}
