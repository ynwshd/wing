/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.wing.controller;

import com.hd.wing.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ynwshd
 */
@Controller
@RequestMapping("/user")
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public ResponseEntity<Result> ResultJson(Result result) {
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Result> ResultJson(String name) {
        Result result = new Result(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Result> ResultJson(String name, Boolean issuccess) {
        Result result = new Result(name, issuccess);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
