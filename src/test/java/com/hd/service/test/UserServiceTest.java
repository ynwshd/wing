/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.service.test;

import com.hd.wing.controller.UserController;
import com.hd.wing.entity.EUser;
import com.hd.wing.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

   // @Test
    public void testUserService() {
        EUser user = userService.getUser("1");
        System.out.println(user.toString());
      
    }
}
