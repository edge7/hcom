package com.ed7.res;

import com.ed7.source.SourceHandler;
import com.ed7.source.SparkHandler;
import com.ed7.user.AverageLengthUser;
import com.ed7.user.NumberOfBookingsUser;
import com.ed7.user.TotalValueUser;
import com.ed7.user.UserInfo;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class SparkHandlerTest {

    private SourceHandler source;

    @Before
    public void setUp()
    {
        SourceHandler source = new SparkHandler("src/test/resources/userTest");
        source.loadIntoMemory();
        this.source = source;
    }

    @Test
    public void testCount()
    {
        Long numberOfRows = source.getNumberOfRows();
        Assert.assertTrue(numberOfRows.equals((long) 9));
    }

    @Test
    public void testAvgLength(){

        AverageLengthUser averageLength = (AverageLengthUser) source.getAverageLength(10);
        Assert.assertTrue(averageLength.getAverageLength().equals(10.0));

        averageLength = (AverageLengthUser) source.getAverageLength(7);
        Assert.assertTrue(averageLength.getAverageLength().equals(12.0));
    }

    @Test
    public void testNumberOfBookings(){
        NumberOfBookingsUser numberofBookings = (NumberOfBookingsUser) source.getNumberofBookings(10);
        Assert.assertTrue(numberofBookings.getNumberOfBookings().equals(3));

        numberofBookings = (NumberOfBookingsUser) source.getNumberofBookings(3);
        Assert.assertTrue(numberofBookings.getNumberOfBookings().equals(1));
    }

    @Test
    public void testGetTotalValue(){

        TotalValueUser totalBookingValue = (TotalValueUser) source.getTotalBookingValue(10);
        Assert.assertTrue(totalBookingValue.getTotalValue().equals((long)27));

        totalBookingValue = (TotalValueUser) source.getTotalBookingValue(2);
        Assert.assertTrue(totalBookingValue.getTotalValue().equals((long)1538));

    }

}
