/*
 * CS112 Programming
 * Year 1, term 3
 *
 * Coursework Project 2019/20
 * by nfb19202 - Calum Doughty
 *
 */

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/* USED FOR SIMULATING USER INPUT
https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing
https://stackoverflow.com/questions/56918911/how-to-pass-command-line-arguments-to-junit-test
https://stackoverflow.com/questions/30455423/junit-testing-simulating-user-input
 */

public class BatchTest {
    Batch batch = new Batch();

    public interface userInputString {

        String nextLine();

    }
    public interface userInputNumber {

        int nextInt();

    }
    private userInputString keyboardStubString = new userInputString() {

        private String bet = "100";

        @Override
        public String nextLine() {
            System.out.println(bet);
            return bet;
        }
    };
    private userInputNumber keyboardStubNumber = new userInputNumber() {

        private int bet = 5;

        @Override
        public int nextInt() {
            System.out.println(bet);
            return bet;
        }
    };

    //check the farmNo is formatted properly
    @Test
    public void farmNoFormatted() {
        //batch.enterFarmNo(5);
        assertEquals("005", batch.getFarmNo());
    }

    //check the batchNo is created properly from the fields
    @Test
    public void batchNoFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

        //need to use calendar to set specific date
        Calendar myCalendar = new GregorianCalendar(2020, Calendar.DECEMBER, 25);
        Date date = myCalendar.getTime();

        batch.setFruitTypeName("STRAWBERRIES");
        batch.setFarmNo("999");
        batch.setBatchNo(formatter.format(date) + "-" + batch.getFruitTypeName().substring(0, 2) + "-" + batch.getFarmNo());
        assertEquals("25122020-ST-999", batch.getBatchNo());
    }


    //check the batchNo is created properly from the fields
    @Test
    public void batchDetails() {
        //Batch batch = new Batch();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

        //need to use calendar to set specific date
        Calendar myCalendar = new GregorianCalendar(2020, Calendar.DECEMBER, 25);
        Date date = myCalendar.getTime();

        batch.setFruitTypeName("TESTBERRIES");
        batch.setFarmNo("999");
        batch.setBatchNo(formatter.format(date) + "-" + batch.getFruitTypeName().substring(0, 2) + "-" + batch.getFarmNo());
        assertEquals("25122020-TE-999", batch.getBatchNo());
    }







    //check the grading works on batches
    @Test
    public void gradeBatch() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        Date date = new Date();

        //Use constructor to test other functionality
        Batch obj = new Batch();
        assertEquals(formatter.format(date)+"-ST-999", obj.getBatchNo());

        JSONdoc.addGrade(batch.getBatchNo(), 23, 24, 17, 36);
        //assertEquals()
    }










//    @Test
//    public void addBatchToJSON() {
//        // add a JSON file
//        //JSONdoc.create("test", "999", "17052020", "STRAWBERRIES", 25);
//
//    }


    // USE BELOW AS EXAMPLE
//    @Test
//    public void kidsAccTest() {
//        // Create a Childrens' Account with Account Number 1,
//        // opening balance 50 and a bonus of 3
//        KidsAcc acc1 = new KidsAcc(1, 50, 3);
//        acc1.deposit(10);
//        // try and withdraw 64 pounds - should not allow
//        assertFalse(acc1.withdraw(64));
//        acc1.setBonus(4);
//        acc1.deposit(20);
//        // 3rd arg is the delta/difference allowed between the 1st and 2nd args - use when real nos
//        assertEquals(87, acc1.getBalance(), 0.0);
//        assertTrue(acc1.withdraw(17));
//        assertEquals(70, acc1.getBalance(), 0.0);
//    }

}
