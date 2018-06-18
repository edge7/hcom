# Smurf WebService API

Smurf WebService API is a simple prototype which shows how to offer REST API through Jersey and Jetty.

It is designed with scalability in mind, in fact, it uses Apache Spark as backend engine.
It can be run on AWS on the same machine as the Spark Driver (EMR) for high performance, it 
caches the source data in memory for quick data retrieval.
Other engines can be easily added extending the SourceHandler class with little or no modification in the 
application.


## How to build it

Smurf WS Api is built using [Apache Maven](http://maven.apache.org/).
To build it run:

    mvn clean package
    
## How to run it
Run it as a normal Java JAR passing inputSource as JVM parameter, inputSource should be the path
where the file is located, i.e.:
```console
foo@bar:~$ java -DinputSource=/home/edge7/Downloads/user_features.txt -jar /home/edge7/Desktop/projects/exerciseHotels.com/target/exercise-0.1.jar
```
Please note that everything has been tested with the file hosted in the localfile system, but
Spark supports different filesystem (S3, HDFS) without particular problems.

## Quick test
```console
foo@bar:~$ curl http://127.0.0.1:8080/rest/users/7/averagelength
           {"id":7,"currentDate":"2018-06-18T10:54:03.019+0100","averageLength":5.44}
```
```console
foo@bar:~$ curl http://127.0.0.1:8080/rest/users/7/totalvalue
           {"id":7,"currentDate":"2018-06-18T10:56:32.042+0100","totalValue":6953}
```
```console
foo@bar:~$ curl http://127.0.0.1:8080/rest/users/7/numbookings
            {"id":7,"currentDate":"2018-06-18T10:58:34.785+0100","numberOfBookings":16}
```

## Assumptions
Given the sample data given to me I have done the following assumptions:
- userId is an integer such as 123. 123b is not a valid user id
- if a given userId is not listed in the source file, 0 is returned as numberOfBookings/totalValue/averageLength.
 (and not 404 or similar HTTP responses). This makes sense as a user can subscribe to H.com without making any purchase.
 