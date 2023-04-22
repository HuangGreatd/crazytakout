package com.pidan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pidan.common.Ressult;
import com.pidan.exception.BusinessException;
import com.pidan.model.entity.Employee;
import com.pidan.model.enums.ErrorCode;
import com.pidan.service.EmployeeService;
import com.pidan.mapper.EmployeeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
* @author 73782
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-04-22 17:11:41
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

    /**
     * 员工登录
     * @param employee
     * @param request
     * @return
     */
    @Override
    public Employee login(Employee employee, HttpServletRequest request) {
        String password = employee.getPassword();
        String username = employee.getUsername();
        if (StringUtils.isBlank(password) || StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "账户或密码为空");
        }
        //1、将页面提交的密码password进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("username", username);
        //2、根据页面提交的用户名username查询数据库
        Employee emp = this.getOne(employeeQueryWrapper);
        //3、如果没有查询到则返回登录失败结果
        if (emp == null) {
          throw new BusinessException(ErrorCode.NOT_DATA);
        }
        //4、密码比对，如果不一致则返回登录失败结果
        String empPassword = emp.getPassword();
        if (!password.equals(empPassword)) {
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //   5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        int status = emp.getStatus();
        if (status == 0) {
            throw new BusinessException(ErrorCode.STATUS_ERROR);
        }
        //   6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return emp;
    }
}




