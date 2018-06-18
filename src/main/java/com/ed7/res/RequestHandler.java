package com.ed7.res;

import com.ed7.data.DataHandler;
import com.ed7.source.SourceHandler;
import com.ed7.user.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class RequestHandler {

    private static final Logger LOGGER = LogManager.getLogger(RequestHandler.class.getName());

    @GET
    @Path("/{userid : \\d+}/numbookings")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo getNumberOfBookings(@PathParam("userid") Integer id) {
        LOGGER.info("Request for NumberOfBookings, id: " + id);
        SourceHandler users = DataHandler.getSource();
        return users.getNumberofBookings(id);
    }

    @GET
    @Path("/{userid : \\d+}/totalvalue")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo getTotalValue(@PathParam("userid") Integer id) {
        LOGGER.info("Request for TotalValue, id: " + id);
        SourceHandler users = DataHandler.getSource();
        return users.getTotalBookingValue(id);
    }

    @GET
    @Path("/{userid : \\d+}/averagelength")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo getAverageLength(@PathParam("userid") Integer id) {
        LOGGER.info("Request for AvgLength, id: " + id);
        SourceHandler users = DataHandler.getSource();
        return users.getAverageLength(id);
    }


}