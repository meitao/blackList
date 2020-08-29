package com.kingstar.bw.bean;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午4:31
 * @Version: 1.0
 */
public class Search {

    private String id ;

    private String name ;

    private String nation ;

    private String gender;

    private String addr;

    private String birthDay;

    private String number;

    private boolean isPer;


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
}
