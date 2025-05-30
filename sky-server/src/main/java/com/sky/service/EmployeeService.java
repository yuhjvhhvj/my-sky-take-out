package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 改变账户状态
     * @param status
     * @param id
     */
    void changeStatus(Integer status, Long id);

    /**
     * ID查询
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 编辑员工
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 根据用户名查询用户
     * @param username
     */
    Employee getByUsername(String username);
}
