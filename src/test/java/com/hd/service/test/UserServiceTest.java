/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.service.test;

import com.hd.wing.controller.UserController;
import com.hd.wing.model.User;
import com.hd.wing.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author ynwshd
 */
//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
//@ContextConfiguration(locations = "classpath:applicationContext.xml") 
public class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //@Test
    public void testUserService() {
        User user = userService.getUser();
        System.out.println(user.toString());
      
    }
}
