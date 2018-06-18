package com.ed7.source;

import com.ed7.user.AverageLengthUser;
import com.ed7.user.NumberOfBookingsUser;
import com.ed7.user.TotalValueUser;
import com.ed7.user.UserInfo;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.apache.spark.sql.functions.col;


public class SparkHandler extends SourceHandler {

    private final SparkSession spark;
    private Dataset<Row> df;

    /* Constructor, main purpose is to create the Spark session,
       being just a demo am using local[*] but real distributed approach
       should be used (i.e.: yarn master )
     */
    public SparkHandler(String input){
        super(input);
        SparkSession spark = SparkSession.builder().master("local[*]")
                .appName("H.com Exercise")
                .getOrCreate();

        spark.sparkContext().setLogLevel("ERROR");
        this.spark = spark;
    }

    /**
      Given an id which identifies a user, returns the number of
      bookings done by that user.
     @param id User id (integer)
     @return NumberOfBookingsUser which holds id and the number of bookings
     */
    @Override
    public UserInfo getNumberofBookings(Integer id) {
        long count = df.filter(col(ID).equalTo(id)).count();
        NumberOfBookingsUser u = new NumberOfBookingsUser(id);
        u.setNumberOfBookings((int) count);
        return u;
    }

    /**
     Given an id which identifies a user, returns the total value, which
     is the aggregate expenditure (USD).
     @param id User id (integer)
     @return TotalValueUser which holds id and the aggregate Expenditure
     */
    @Override
    public UserInfo getTotalBookingValue(Integer id) {
        TotalValueUser totalValueUser = new TotalValueUser(id);

        Row[] collect = (Row[]) df.filter(col(ID).equalTo(id)).groupBy(ID).sum(VALUE).collect();

        // User not found, but as the specification is not clear on this, just return 0
        if( collect.length == 0 ){
            totalValueUser.setTotalValue((long) 0);
        }
        else {

            long value = collect[0].getLong(1);
            totalValueUser.setTotalValue(value);
        }
        return totalValueUser;
    }

    /**
     Given an id which identifies a user, returns the average length of stay (number of nights)
     @param id User id (integer)
     @return AverageLengthUser which holds id and the average length of stay
     */
    @Override
    public UserInfo getAverageLength(Integer id) {
        AverageLengthUser averageLengthUser = new AverageLengthUser(id);
        Row[] collect = (Row[]) df.filter(col(ID).equalTo(id)).groupBy(ID).avg(NON).collect();

        // User not found, but as the specification is not clear on this, just return 0
        if( collect.length == 0){
            averageLengthUser.setAverageLength(0.0);
        }
        else {

            Double avg = collect[0].getDouble(1);
            averageLengthUser.setAverageLength(new BigDecimal(avg.toString()).setScale(2,
                    RoundingMode.HALF_UP).doubleValue());
        }
        return averageLengthUser;
    }

    /**
     @return the number of rows in the dataSet
     */
    @Override
    public long getNumberOfRows() {
        return df.count();
    }

    /**
     Loads the input data into the workers memory, please note
     that Spark implements lazy evaluation, to force cache to warm-up
     an action needs to be called, such as 'count'
     */
    @Override
    public void loadIntoMemory() {
        this.df = spark.read().json(path);
        this.df.cache();
        // We might want to call .count to force cache to warm-up
    }



}
