package com.developtech.contactappwithsqlite.bean;

import java.io.Serializable;

public class ContactBean implements Serializable {
    private int contactid;
    private String name, dob, mobile, email, member, image;
    private boolean isBirthdayReminder;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isBirthdayReminder() {
        return isBirthdayReminder;
    }

    public void setBirthdayReminder(boolean birthdayReminder) {
        isBirthdayReminder = birthdayReminder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {

        this.mobile = mobile;
    }

    public int getContactid() {
        return contactid;
    }

    public void setContactid(int contactid) {
        this.contactid = contactid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


