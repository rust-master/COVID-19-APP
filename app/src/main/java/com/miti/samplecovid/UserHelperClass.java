package com.miti.samplecovid;

public class UserHelperClass {

    String name , email , depName , empID,phoneNo;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String email, String depName, String empID, String phoneNo) {
        this.name = name;
        this.email = email;
        this.depName = depName;
        this.empID = empID;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
