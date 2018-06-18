package com.ed7.main;

import com.ed7.data.DataHandler;
import com.ed7.source.SourceHandler;
import com.ed7.source.SparkHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        LOGGER.info("App has just started ...");

        LOGGER.info("Init. source handler ");

        String inputSource = System.getProperty("inputSource");

        if(inputSource == null){
            LOGGER.error("inputSource is mandatory.. exiting");
            System.exit(-1);
        }

        /* Initializing Source Handler that in my case is based on Spark,
           different computing engines can be added extending SourceHandler base class
         */
        SourceHandler source = new SparkHandler(inputSource);
        source.loadIntoMemory();

        /* DataHandler is just a class which needs to hold SourceHandler to make easy access
           it from the API request handler code, dependency Injection is also possible
         */

        DataHandler.setSource(source);

        LOGGER.info("Input source init. is done");

        /* Starting server on port 8080 specifying the API path (/rest) and the package
            which handles it (com.ed7.res).
        */
        Server server = new Server(8080);
        ServletContextHandler ctx =
                new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/rest/*");
        serHol.setInitOrder(1);
        serHol.setInitParameter("jersey.config.server.provider.packages",
                "com.ed7.res");

        LOGGER.info("Starting Server");

        try {
            server.start();
            server.join();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            server.destroy();
        }
    }
}