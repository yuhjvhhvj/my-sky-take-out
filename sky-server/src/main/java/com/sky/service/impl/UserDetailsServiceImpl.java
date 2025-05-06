package com.sky.service.impl;

import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public UserDetails loadUserByUsername(String username){
        Employee employee = employeeMapper.getByUsername(username);
        log.info("尝试认证用户: {}", username);
        UserDetails userDetails = User.withUsername(username).password(employee.getPassword()).roles("admin").build();
        return userDetails;
    }
}
