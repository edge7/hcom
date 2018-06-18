package com.ed7.source;

import com.ed7.user.UserInfo;

/*
   Base class for any source data engine.
   At the moment just Spark implementation is availabl.
   Any implementation should at least offer the following methods:

    - GetNumberOfBookings
    - getTotalBookingValue
    - getAverageLength
 */
public abstract class SourceHandler {

    protected final String path;

    protected String ID = "userId";
    protected String H_ID = "hotelId";
    protected String NON = "numberOfNights";
    protected String VALUE = "totalPriceInUSD";

    public SourceHandler(String input){
        this.path = input;
    }
    public abstract UserInfo getNumberofBookings(Integer id);
    public abstract UserInfo getTotalBookingValue(Integer id);
    public abstract UserInfo getAverageLength(Integer id);
    public abstract void loadIntoMemory();
    public abstract long getNumberOfRows();
}
