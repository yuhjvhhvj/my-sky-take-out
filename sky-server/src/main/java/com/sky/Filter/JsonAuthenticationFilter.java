package com.sky.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.dto.EmployeeLoginDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JsonAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    // 通过构造函数注入AuthenticationManager
    public JsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("/admin/employee/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod())) {
            // 读取原始请求体（注意：只能读取一次！）
            String requestBody = new String(
                    request.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );
            // 解析JSON
            ObjectMapper objectMapper = new ObjectMapper();
            EmployeeLoginDTO loginDTO = objectMapper.readValue(requestBody, EmployeeLoginDTO.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}