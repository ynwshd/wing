/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.wing.controller;

import com.hd.wing.entity.Result;
import com.hd.wing.entity.EUser;
import com.hd.wing.entity.page.Jpage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hd.wing.service.UserService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author ynwshd
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 显示注册页面
     *
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String showRegView() {
        return "user/reg";
    }

    @RequestMapping(value = "/postreg", method = RequestMethod.POST)
    public ResponseEntity<EUser> postreg(String name, Integer age) {
        EUser u = new EUser(name, age);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    /**
     * 检查用户名是否可用
     *
     * @param name
     * @return 一个result对象
     */
    @RequestMapping(value = "/checkusername")
    public ResponseEntity<Result> CheckUserName(String name) {

        return (name.equals("ynwshd")) ? ResultJson("用户名重复")
                : ResultJson("用户名可用", true);

    }

    @RequestMapping(value = "/regconfirm", method = RequestMethod.POST)
    public String regconfirm(String name, Integer age, Model model) {
        EUser user = new EUser(name, age);
        model.addAttribute(user);
        return "user/regconfirm";
    }

    /**
     *
     * @return
     */
    @RequestMapping("show/")
    public String Show(String Id) {
        EUser user = userService.getUser(Id);
        System.out.println(user.toString());
        return "user/show";
    }

    @RequestMapping("list/")
    public String ShowList() {
        return "user/list";
    }

    /**
     * 测试一个返回一个json 使用sping的注入，从sevice里得到数据
     *
     * @return
     */
    @RequestMapping("getuser/{id}")
    public ResponseEntity getUser(@PathVariable("id") String Id) {
        EUser user = userService.getUser(Id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 返回一个分页，用于jgrid , method = RequestMethod.POST
     *
     * @param findkey
     * @param page
     * @param rows
     * @param sord
     * @return
     */
    @RequestMapping(value = "getpage/", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Jpage<EUser>> GetUserPage(String findkey,
            Integer page,
            //            Boolean _search,
            //            String sidx ,
            //            String nd,
            Integer rows, String sord
    ) {
        List<EUser> list = new ArrayList<>();
        list.add(new EUser("1", "黄达", 35));
        list.add(new EUser("2", "张三", 88));
        Jpage<EUser> jpage = new Jpage<>(1, 10, 100, list);
        return new ResponseEntity<>(jpage, HttpStatus.OK);
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public String doUploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File("c:\\temp\\imooc\\", System.currentTimeMillis() + file.getOriginalFilename()));
        }

        return "success";
    }

    /**
     * 上传文件-多文件
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/doUpload2", method = RequestMethod.POST)
    public String doUploadFile2(MultipartHttpServletRequest multiRequest) throws IOException {

        Iterator<String> filesNames = multiRequest.getFileNames();
        while (filesNames.hasNext()) {
            String fileName = filesNames.next();
            MultipartFile file = multiRequest.getFile(fileName);
            if (!file.isEmpty()) {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File("c:\\temp\\imooc\\", System.currentTimeMillis() + file.getOriginalFilename()));
            }

        }

        return "success";
    }

}
