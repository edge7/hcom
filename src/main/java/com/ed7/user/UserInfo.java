package com.ed7.user;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/* This is the base class. If new API, which returns new info about users, will be added
   an appropriate class which extends UserInfo should be implemented in order
   to have always a clean and light object to return as JSON
 */
public abstract class UserInfo implements Serializable {

    private Integer id;
    private String currentDate;

    public UserInfo(Integer id) {
        this.id = id;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        this.currentDate = formatter.format(new Date());

    }

    public UserInfo() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
