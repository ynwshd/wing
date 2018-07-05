/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.wing.service.impl;

import com.hd.wing.entity.EUser;
import org.springframework.stereotype.Service;
import com.hd.wing.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hd.dao.IEUserDao;
import com.hd.dao.impl.EUserDaoImpl;

/**
 *
 * @author ynwshd
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

    //@Autowired
    private IEUserDao euserDao=new EUserDaoImpl();

    @Override
    public EUser getUser(String Id) {
        log.debug("Log From UserServiceImpl getUser!");
        String hql = " from eUser t where t.id=:Id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Id", Id);
       EUser user = (EUser) euserDao.getByHql(hql, params);
        // User user=new User("黄达",235);
        return user;
    }
}
