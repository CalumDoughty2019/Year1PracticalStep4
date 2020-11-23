/*
 * CS112 Programming
 * Year 1, term 3
 *
 * Coursework Project 2019/20
 * by nfb19202 - Calum Doughty
 *
 */
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
// FOR UNDERSTANDING AND IMPLEMENTING LOGS
https://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger
 */

public class Logs {
    private final Logger logger = Logger.getLogger(Logs.class.getName());
    private FileHandler fh = null;

    //just to make our log file nicer :)
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy_HHmmss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

    //Logs for tracking code is operating correctly
    public Logs() {

        try {
            fh = new FileHandler("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/logFiles/logFile"
                    + dateFormat.format(date) + ".log", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    public void doLogging() {
        logger.info("info msg");
        logger.severe("error message");
        logger.fine("fine message"); //won't show because to high level of logging
    }

    //Log if JSON is created successfully
    public void logJSON(JSONObject json){
        logger.info( "[" + timeFormat.format(date) +"] JSON file successfully created: " + json +"\r\n");
        //small interface glitch required program to be paused to remedy issue
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Log if JSON is retrieved correctly
    public void logRetreival(String json){
        logger.info("[" + timeFormat.format(date) +"] JSON file successfully retrieved: " + json +"\r\n");
        //small interface glitch required program to be paused to remedy issue
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Log if JSON file is appended properly
    public void logAppend(JSONObject json){
        logger.info("[" + timeFormat.format(date) +"] JSON file successfully appended: " + json +"\r\n");
        //small interface glitch required program to be paused to remedy issue
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
