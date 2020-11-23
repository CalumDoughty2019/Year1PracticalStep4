/*
 * CS112 Programming
 * Year 1, term 3
 *
 * Coursework Project 2019/20
 * by nfb19202 - Calum Doughty
 *
 */

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/* USED FOR SIMULATING USER INPUT
https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing
 */

public class BatchMaker {
    Batch batch = new Batch();

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;


    //check we can create a batch successfully
    @Test
    public void createBatch() {
        //Batch batch = new Batch();

        batch.newBatch();

//        String input = "5";
//        batch.enterFarmNo(); = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);

        assertEquals("005", batch.getFarmNo());




//        String testFarmNo = "5";
//        InputStream stdin = System.in;
//        try {
//            batch.enterFarmNo();
//            System.setIn(new ByteArrayInputStream(testFarmNo.getBytes()));
//            Scanner scanner = new Scanner(System.in);
//            System.out.println(scanner.nextLine());
//        } finally {
//            System.setIn(stdin);
//        }
//        assertEquals("005", batch.getFarmNo());

    }
}
