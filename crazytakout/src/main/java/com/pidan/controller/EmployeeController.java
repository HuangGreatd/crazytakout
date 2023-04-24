package com.pidan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pidan.common.Ressult;
import com.pidan.exception.BusinessException;
import com.pidan.model.entity.Employee;
import com.pidan.model.enums.ErrorCode;
import com.pidan.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
     *
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Ressult<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        if (employee == null) {
            return Ressult.error("请求数据为空");
        }
        Employee emp = employeeService.login(employee, request);
        return Ressult.success(emp);
    }

    /**
     * 登出功能
     */
    @PostMapping("/logout")
    public Ressult<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Ressult.success("退出成功");
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @RequestMapping
    public Ressult<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        if (employee == null) {
            return Ressult.error("添加失败");
        }
        //设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long createUserId = (Long) request.getSession().getAttribute("employee");
        //创建人
        employee.setCreateUser(createUserId);
        employee.setUpdateUser(createUserId);

        boolean save = employeeService.save(employee);
        if (!save) {
            return Ressult.error("添加失败");
        }
        log.info("添加员工信息:{}", employee.toString());
        return Ressult.success("添加成功");
    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Ressult<Page> page(int page, int pageSize, String name) {

        Page employeePage = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加一个排序
        employeeQueryWrapper.orderByDesc(Employee::getUpdateTime);
        Page<Employee> page1 = employeeService.page(employeePage, employeeQueryWrapper);
        return Ressult.success(page1);

    }


    /**
     * 根据id修改员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public Ressult<String> update(@RequestBody Employee employee, HttpServletRequest request) {
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long empId = (Long) request.getSession().getAttribute("employee");
        if (empId == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        boolean result = employeeService.getUpdateById(employee, empId);
        return Ressult.success("更新成功");
    }

    @GetMapping("{/id}")
    public Ressult<Employee> updateById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee ==null){
            throw  new BusinessException(ErrorCode.NOT_DATA);
        }
        return Ressult.success(employee);
    }
}
