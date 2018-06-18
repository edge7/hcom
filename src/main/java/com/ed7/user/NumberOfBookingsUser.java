package com.ed7.user;

/*
    Object returned by users/{user_id}/numbookings endpoint
 */
public class NumberOfBookingsUser extends UserInfo {

    private Integer numberOfBookings;

    public NumberOfBookingsUser(){}

    public NumberOfBookingsUser(int id){
        super(id);
    }
    public Integer getNumberOfBookings() {
        return numberOfBookings;
    }

    public void setNumberOfBookings(Integer numberOfBookings) {
        this.numberOfBookings = numberOfBookings;
    }

}
