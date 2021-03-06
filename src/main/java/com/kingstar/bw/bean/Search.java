package com.kingstar.bw.bean;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午4:31
 * @Version: 1.0
 */
public class Search {

    private String id ;
    //名称
    private String name ;
    //国家
    private String nation ;
    //性别
    private String gender;
    //地址
    private String addr;
    //生日
    private String birthDay;
    //证件号
    private String number;
    //是否为个人
    private boolean isPer;
    //是否为拼音
    private boolean isPinYin;
    //精确度
    private double  percision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    @Override
    public String toString() {
        return id+name+nation+gender+addr+birthDay+number;
    }

    public boolean isPer() {
        return isPer;
    }

    public void setPer(boolean per) {
        isPer = per;
    }

    public double getPercision() {
        return percision;
    }

    public void setPercision(double percision) {
        this.percision = percision;
    }

    public boolean isPinYin() {
        return isPinYin;
    }

    public void setPinYin(boolean pinYin) {
        isPinYin = pinYin;
    }
}
