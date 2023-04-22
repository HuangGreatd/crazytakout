package com.pidan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pidan.common.Ressult;
import com.pidan.exception.BusinessException;
import com.pidan.model.entity.Employee;
import com.pidan.model.enums.ErrorCode;
import com.pidan.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 黄大头
 * @date 2023年04月22日 17:17
 */
@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 登录功能
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Ressult<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Employee emp = employeeService.login(employee, request);
        return Ressult.success(emp);
    }

    /**
     * 登出功能
     */
    @PostMapping("/logout")
    public Ressult<String> logout(HttpServletRequest request){
      request.getSession().removeAttribute("employee");
      return Ressult.success("退出成功");


    }
}
