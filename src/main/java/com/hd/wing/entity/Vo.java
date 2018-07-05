package com.hd.wing.entity;

//用于传递数据
public class Vo implements java.io.Serializable {

    private int intData;
    private String strData;
    private double dbData;
    private boolean blData;

    public int getIntData() {
        return intData;
    }

    public void setIntData(int intData) {
        this.intData = intData;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }

    public double getDbData() {
        return dbData;
    }

    public void setDbData(double dbData) {
        this.dbData = dbData;
    }

    public boolean isBlData() {
        return blData;
    }

    public void setBlData(boolean blData) {
        this.blData = blData;
    }
}
