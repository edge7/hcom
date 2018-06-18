package com.ed7.res;

import com.ed7.source.SourceHandler;
import com.ed7.data.DataHandler;
import com.ed7.source.SparkHandler;
import com.ed7.user.AverageLengthUser;
import com.ed7.user.NumberOfBookingsUser;
import com.ed7.user.TotalValueUser;
import junit.framework.Assert;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;

public class RequestTest extends JerseyTest {

    @Override
    protected Application configure() {
        SourceHandler source = new SparkHandler("src/test/resources/userTest");
        source.loadIntoMemory();
        DataHandler.setSource(source);
        return new ResourceConfig(RequestHandler.class);
    }

    @Test
    public void averageLengthTest() {
        AverageLengthUser response = target("/users/3/averagelength").request().get(AverageLengthUser.class);
        Assert.assertTrue(response.getAverageLength().equals(8.0));
        Assert.assertTrue(response.getId().equals(3));

        response = target("/users/2/averagelength").request().get(AverageLengthUser.class);
        Assert.assertTrue(response.getAverageLength().equals(9.0));
        Assert.assertTrue(response.getId().equals(2));

        response = target("/users/1/averagelength").request().get(AverageLengthUser.class);
        Assert.assertTrue(response.getAverageLength().equals(0.0));
        Assert.assertTrue(response.getId().equals(1));

    }

    @Test
    public void totalBookingValueTest() {
        TotalValueUser response = target("/users/3/totalvalue").request().get(TotalValueUser.class);
        Assert.assertTrue(response.getTotalValue().equals((long)456));
        Assert.assertTrue(response.getId().equals(3));

        response = target("/users/2/totalvalue").request().get(TotalValueUser.class);
        Assert.assertTrue(response.getTotalValue().equals((long)1538));
        Assert.assertTrue(response.getId().equals(2));

        response = target("/users/1/totalvalue").request().get(TotalValueUser.class);
        Assert.assertTrue(response.getTotalValue().equals((long)0.0));
        Assert.assertTrue(response.getId().equals(1));

    }

    @Test
    public void numberOfBookingTest() {
        NumberOfBookingsUser response = target("/users/3/numbookings").request().get(NumberOfBookingsUser.class);
        Assert.assertTrue(response.getNumberOfBookings().equals(1));
        Assert.assertTrue(response.getId().equals(3));

        response = target("/users/2/numbookings").request().get(NumberOfBookingsUser.class);
        Assert.assertTrue(response.getNumberOfBookings().equals(2));
        Assert.assertTrue(response.getId().equals(2));

        response = target("/users/1/numbookings").request().get(NumberOfBookingsUser.class);
        Assert.assertTrue(response.getNumberOfBookings().equals(0));
        Assert.assertTrue(response.getId().equals(1));

    }

    @Test(expected = javax.ws.rs.NotFoundException.class)
    public void testAverageRegex(){
        Invocation.Builder request = target("/users/2b/averagelength").request();
        request.get(String.class);
    }

    @Test(expected = javax.ws.rs.NotFoundException.class)
    public void testBookingvalueRegex(){
        Invocation.Builder request = target("/users/2b/totalvalue").request();
        request.get(String.class);
    }

    @Test(expected = javax.ws.rs.NotFoundException.class)
    public void testNumberOfBookingRegex(){
        Invocation.Builder request = target("/users/2b/numbookings").request();
        request.get(String.class);
    }
}
