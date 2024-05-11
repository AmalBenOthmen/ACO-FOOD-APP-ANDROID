package com.example.acofoodapp.ChefFoodPanel;

import com.google.firebase.database.PropertyName;

public class Chef {

    @PropertyName("Confirm Password")
    private String ConfirmPassword;

    @PropertyName("EmailId")
    private String Emailid;

    @PropertyName("First Name")
    private String Fname;

    @PropertyName("Last Name")
    private String Lname;

    @PropertyName("Mobile Num")
    private String Mobile;

    @PropertyName("Password")
    private String Password;

    public Chef(String confirmPassword, String emailid, String fname, String lname, String mobile, String password) {
        this.ConfirmPassword = confirmPassword;
        this.Emailid = emailid;
        this.Fname = fname;
        this.Lname = lname;
        this.Mobile = mobile;
        this.Password = password;
    }

    public Chef() {
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        this.Emailid = emailid;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        this.Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        this.Lname = lname;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        this.Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
