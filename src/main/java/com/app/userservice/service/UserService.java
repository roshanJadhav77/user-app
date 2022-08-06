package com.app.userservice.service;

import com.app.userservice.entity.User;
import com.app.userservice.repository.UserRepository;
import com.app.userservice.vo.Department;
import com.app.userservice.vo.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("inside getUserWithDepartment method of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        Department department =
                restTemplate.getForObject("http://localhost:9090/departments/" + user.getDepartmentId(),
                        Department.class);
        log.info("Calling Rest Template");
        vo.setUser(user);
        vo.setDepartment(department);

        return vo;
    }
}