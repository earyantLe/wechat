package com.earyant.sys.userRole.controller;

import com.earyant.sys.userRole.domain.UserRole;
import com.earyant.sys.userRole.service.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/oper/userRole")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;

    @RequestMapping("/addUserRoleInfo.do")
    @ResponseBody
    public String addUserRoleInfo(UserRole ur, HttpServletRequest request, HttpServletResponse response) {
        String flag = "";
        try {
            UserRole urole = userRoleService.selectByUserId(ur.getUserid());
            if (urole == null) {
                userRoleService.insertSelective(ur);
            } else {
                userRoleService.updateByUserId(ur);
            }

            flag = "true";
        } catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }
}
