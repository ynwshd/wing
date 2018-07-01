/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.wing.model;

/**
 *
 * @author ynwshd
 */
public class Result {

      public Result( String appmsg) {
        this.appcode = -1;
        this.appmsg = appmsg;
        this.issuccess = false;
    }
      
    public Result( String appmsg, Boolean issuccess) {
        this.appcode = appcode;
        this.appmsg = appmsg;
        this.issuccess = issuccess;
    }

    public Integer getAppcode() {
        return appcode;
    }

    public void setAppcode(Integer appcode) {
        this.appcode = appcode;
    }

    public String getAppmsg() {
        return appmsg;
    }

    public void setAppmsg(String appmsg) {
        this.appmsg = appmsg;
    }

    public Boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(Boolean issuccess) {
        this.issuccess = issuccess;
    }

    private Integer appcode;
    private String appmsg;
    private Boolean issuccess;
}
