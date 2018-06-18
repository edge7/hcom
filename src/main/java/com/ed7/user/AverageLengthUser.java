package com.ed7.user;

/*
    Object returned by users/{user_id}/averagelength endpoint
 */
public class AverageLengthUser extends UserInfo {

    private Double averageLength;

    public AverageLengthUser(){ }
    public AverageLengthUser(Integer id) {
        super(id);
    }

    public Double getAverageLength() {
        return averageLength;
    }

    public void setAverageLength(Double averageLength) {
        this.averageLength = averageLength;
    }
}
