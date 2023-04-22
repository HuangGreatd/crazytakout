package com.pidan.service;

import com.pidan.model.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 73782
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2023-04-22 17:11:41
*/
public interface EmployeeService extends IService<Employee> {

    Employee login(Employee employee, HttpServletRequest request);
}
