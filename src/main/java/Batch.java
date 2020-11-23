/*
 * CS112 Programming
 * Year 1, term 3
 *
 * Coursework Project 2019/20
 * by nfb19202 - Calum Doughty
 *
 */

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
 */

public class Batch {

    //variables
    Menu menu = new Menu();
    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat formatterTimestamp = new SimpleDateFormat("ddMMyyyy HHmmss");
    public Date date = new Date();
    Scanner scanner = new Scanner(System.in);
    private char summary;
    private String farmNo;
    private int fruitTypeNo;
    private String fruitTypeName;
    private int fruitWeight;
    private String batchNo;

    private int gradeA;
    private int gradeB;
    private int gradeC;
    private int gradeRejected;


    //getters & setters


    public void setSummary(char summary) {
        this.summary = summary;
    }

    public char getSummary() {
        return summary;
    }

    public void setFarmNo(String farmNo) {
        this.farmNo = farmNo;
    }

    public String getFarmNo() {
        return farmNo;
    }

    public void setFruitTypeNo(int fruitTypeNo) {
        this.fruitTypeNo = fruitTypeNo;
    }

    public int getFruitTypeNo() {
        return fruitTypeNo;
    }

    public void setFruitWeight(int fruitWeight) {
        this.fruitWeight = fruitWeight;
    }

    public int getFruitWeight() {
        return fruitWeight;
    }

    public String getFruitTypeName() {
        return fruitTypeName;
    }

    public void setFruitTypeName(String fruitTypeName) {
        this.fruitTypeName = fruitTypeName;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public int getGradeA() {
        return gradeA;
    }

    public void setGradeA(int gradeA) {
        this.gradeA = gradeA;
    }

    public int getGradeB() {
        return gradeB;
    }

    public void setGradeB(int gradeB) {
        this.gradeB = gradeB;
    }

    public int getGradeC() {
        return gradeC;
    }

    public void setGradeC(int gradeC) {
        this.gradeC = gradeC;
    }

    public int getGradeRejected() {
        return gradeRejected;
    }

    public void setGradeRejected(int gradeRejected) {
        this.gradeRejected = gradeRejected;
    }

    //constructor
    public Batch() {
        summary = 'A';
        formatter.format(date);
        farmNo = "999";
        fruitTypeNo = 1;
        fruitTypeName = "STRAWBERRIES";
        fruitWeight = 99;
        batchNo = formatter.format(date) + "-ST-999";
    }


    //add new batch
    public void newBatch() {
        do {
            //reset in case changed by other functionality
            summary = 'A';

            //sets Date property
            System.out.println("Date: " + formatter.format(date)); // get date

            //input for farm number
            enterFarmNo();

            //input for fruit type
            enterFruitType();

            //get desired weight of fruit
            enterFruitWeight();


            //Here, we check if order is correct
            System.out.println("");
            System.out.println("");
            System.out.println("This batch contains " + getFruitWeight() + "KG of " + getFruitTypeName() +
                    " from farm No. " + getFarmNo() + " received on " + formatter.format(date) + ".");
            //
            do {
                System.out.println("Is this correct y/n? ");
                setSummary(scanner.next().charAt(0));
                if (getSummary() != 'y' && getSummary() != 'n') {
                    System.out.println("!! Please answer with y or n !!");
                    System.out.println("");
                }
            } while (getSummary() != 'y' && getSummary() != 'n');

            switch (getSummary()) {
                case 'y':
                    setBatchNo(formatter.format(date) + "-" + getFruitTypeName().substring(0, 2) + "-" + getFarmNo());
                    break;
                case 'n':
                    System.out.println("");
                    System.out.println("");
                    break;
            }

        }
        while (getSummary() == 'n');

        //once batch is confirmed then store in JSON file
        JSONdoc.create(getBatchNo(), getFarmNo(), formatter.format(date), getFruitTypeName(), getFruitWeight());


        //user can print their order
        char print;
        do {
            System.out.println("");
            System.out.println("Print batch details y/n? ");
            print = scanner.next().charAt(0);
            if (print != 'y' && print != 'n') {
                System.out.println("!! Please answer with y or n !!");
                System.out.println("");
            }
        } while (print != 'y' && print != 'n');
        switch (print) {
            case 'y':
                System.out.println("");
                display();
                menu.pressEnterToContinue();
                //menu.CLI();
                break;
            case 'n':
                menu.pressEnterToContinue();
                //menu.CLI();
                break;
        }
    }


    //display batch details
    public void display() {
        System.out.println("Batch No: " + getBatchNo());
        System.out.println("Received Date: " + formatter.format(date));
        System.out.println("Fruit Type: " + getFruitTypeName());
        System.out.println("Batch Weight: " + getFruitWeight() + "KG");
    }

    //display specific batch details
    public void details() {
        JSONdoc.readSpecific(getBatchNo());
    }


    //used for entering batchNo (includes validation)
    public String enterBatchNo() {
        String input;
        boolean checked;
        do {
            checked = false;
            System.out.println("Enter batch number: > ");
            input = scanner.nextLine();
            checked = JSONdoc.checker(input);

            menu.hotKeys(input);

            if (checked == true) {
                Logs logs = new Logs();

                setBatchNo(input);
                System.out.println("");
                logs.logRetreival(input);
                System.out.println("");
                details();
                System.out.println("");
                return input;
            } else {
                System.out.println("Batch Number does not have a match!!");
            }
        } while (checked == false);
        return null;
    }


    //used for entering farmNo (includes validation)
    public void enterFarmNo() {
        int temp = 0;
        do {
            System.out.println("Enter a Farm number: (001 - 999) > ");
            boolean validInput = false;
            while (!validInput) {
                try {
                    temp = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    if (scanner.hasNext("quit")) {
                        menu.hotKeys("quit");
                    } else if (scanner.hasNext("menu")) {
                        menu.hotKeys("menu");
                        break;
                    }
                    System.out.println("Please enter an integer!");
                    scanner.next();
                }
            }

            if (temp < 1 || temp > 999) {
                System.out.println("!!" + temp + " is out of bounds for the Farm number !!");
                System.out.println("");
            }
        } while (temp < 1 || temp > 999);
        //format farmNo to specification layout
        setFarmNo(String.format("%03d", temp));
    }

    //used for entering fruitType (includes validation)
    public void enterFruitType() {
        do {
            System.out.println("Select a fruit type (1. Strawberries,2. Raspberries,3. Blackberries,4. Gooseberries)? > ");
            boolean validInput = false;
            while (!validInput) {
                try {
                    setFruitTypeNo(scanner.nextInt());
                    validInput = true;
                } catch (InputMismatchException e) {
                    if (scanner.hasNext("quit")) {
                        System.exit(0);
                    } else if (scanner.hasNext("menu")) {
                        menu.CLI();
                        break;
                    }
                    System.out.println("Please enter an integer!");
                    scanner.next();
                }
            }

            if (getFruitTypeNo() < 1 || getFruitTypeNo() > 4) {
                System.out.println("!! " + getFruitTypeNo() + " is not a valid option !!");
                System.out.println("");
            }
        } while (getFruitTypeNo() < 1 || getFruitTypeNo() > 4);

        //get fruit name from fruit type
        switch (getFruitTypeNo()) {
            case 1:
                setFruitTypeName("STRAWBERRIES");
                break;
            case 2:
                setFruitTypeName("RASPBERRIES");
                break;
            case 3:
                setFruitTypeName("BLACKBERRIES");
                break;
            case 4:
                setFruitTypeName("GOOSEBERRIES");
                break;
        }
    }

    //used for entering fruitWeight (includes validation)
    public void enterFruitWeight() {
        do {
            System.out.println("Enter batch weight in KG’s (N.B. Max weight per batch is 100Kg) > ");
            boolean validInput = false;
            while (!validInput) {
                try {
                    setFruitWeight(scanner.nextInt());
                    validInput = true;
                } catch (InputMismatchException e) {
                    if (scanner.hasNext("quit")) {
                        menu.hotKeys("quit");
                    } else if (scanner.hasNext("menu")) {
                        menu.hotKeys("menu");
                        break;
                    }
                    System.out.println("Please enter an integer!");
                    scanner.next();
                }
            }


            if (getFruitWeight() < 1 || getFruitWeight() > 100) {
                System.out.println("!! " + getFruitWeight() + " exceeds the min/max order threshold !!");
                System.out.println("");
            }
        } while (getFruitWeight() < 1 || getFruitWeight() > 100);
    }


    //list all batches (includes validation)
    public void listAllBatches() {
        JSONdoc.readAll();
        System.out.println("End of List");
        System.out.println("");
        menu.pressEnterToContinue();
        //menu.CLI();
    }

    //list all batches (includes validation)
    public void sortBatches() {
        //reset incase changed by other functionality
        summary = 'A';
        String input = enterBatchNo();


        int percent = 100;
        do {
            do {
                percent = 100;
//                System.out.println("Enter percentage of GRADE A fruit: ");
                int tempA = 0;
                boolean validInputA = false;
                while (!validInputA) {
                    try {
                        System.out.println("Enter percentage of GRADE A fruit: ");
                        tempA = scanner.nextInt();
                        if (tempA < 0 || tempA > 100) {
                            System.out.println("!! " + tempA + " exceeds the min/max order threshold !!");
                            System.out.println("");
                            validInputA = false;
                        } else {
                            validInputA = true;
                            percent = percent - tempA;
                            setGradeA(tempA);
                        }
//                        percent = percent - tempA;
//                        setGradeA(tempA);
//                        validInputA = true;
                    } catch (InputMismatchException e) {
                        if (scanner.hasNext("quit")) {
                            menu.hotKeys("quit");
                        } else if (scanner.hasNext("menu")) {
                            menu.hotKeys("menu");
                            break;
                        }
                        System.out.println("Please enter an integer!");
                        scanner.next();
                    }
                }


                int tempB = 0;
                boolean validInputB = false;
                while (!validInputB) {
                    try {
                        System.out.println("Enter percentage of GRADE B fruit: ");
                        System.out.println(percent + "% left");
                        tempB = scanner.nextInt();
                        if (tempB < 0 || tempB > 100) {
                            System.out.println("!! " + tempB + " exceeds the min/max order threshold !!");
                            System.out.println("");
                            validInputB = false;
                        } else {
                            validInputB = true;
                            percent = percent - tempB;
                            setGradeB(tempB);
                        }

                    } catch (InputMismatchException e) {
                        if (scanner.hasNext("quit")) {
                            menu.hotKeys("quit");
                        } else if (scanner.hasNext("menu")) {
                            menu.hotKeys("menu");
                            break;
                        }
                        System.out.println("Please enter an integer!");
                        scanner.next();
                    }
                }


                int tempC = 0;
                boolean validInputC = false;
                while (!validInputC) {
                    try {
                        System.out.println("Enter percentage of GRADE C fruit: ");
                        System.out.println(percent + "% left");
                        tempC = scanner.nextInt();
                        if (tempC < 0 || tempC > 100) {
                            System.out.println("!! " + tempC + " exceeds the min/max order threshold !!");
                            System.out.println("");
                            validInputC = false;
                        } else {
                            validInputC = true;
                            percent = percent - tempC;
                            setGradeC(tempC);
                        }

                    } catch (InputMismatchException e) {
                        if (scanner.hasNext("quit")) {
                            menu.hotKeys("quit");
                        } else if (scanner.hasNext("menu")) {
                            menu.hotKeys("menu");
                            break;
                        }
                        System.out.println("Please enter an integer!");
                        scanner.next();
                    }
                }


                int tempR = 0;
                boolean validInputR = false;
                while (!validInputR) {
                    try {
                        System.out.println("Enter percentage of REJECTED fruit: ");
                        System.out.println(percent + "% left");
                        tempR = scanner.nextInt();
                        if (tempR < 0 || tempR > 100) {
                            System.out.println("!! " + tempR + " exceeds the min/max order threshold !!");
                            System.out.println("");
                            validInputR = false;
                        } else {
                            validInputR = true;
                            percent = percent - tempR;
                            setGradeRejected(tempR);
                        }

                    } catch (InputMismatchException e) {
                        if (scanner.hasNext("quit")) {
                            menu.hotKeys("quit");
                        } else if (scanner.hasNext("menu")) {
                            menu.hotKeys("menu");
                            break;
                        }
                        System.out.println("Please enter an integer!");
                        scanner.next();
                    }
                }

                if (percent != 0) {
                    System.out.println("Percent calculation is INCORRECT: percent left is " + percent);
                    System.out.println("");
                }
            } while (percent != 0);


            //Check user is satisfied with inputs
            do {
                System.out.println("Is this correct y/n? ");
                setSummary(scanner.next().charAt(0));
                if (getSummary() != 'y' && getSummary() != 'n') {
                    System.out.println("!! Please answer with y or n !!");
                    System.out.println("");
                }
            } while (getSummary() != 'y' && getSummary() != 'n');

            switch (getSummary()) {
                case 'y':
                    JSONdoc.addGrade(input, getGradeA(), getGradeB(), getGradeC(), getGradeRejected());

                    //small interface glitch required program to be paused to remedy issue
                    try {
                        Thread.sleep(1000);                 //1000 milliseconds is one second.
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("");
                    menu.pressEnterToContinue();
                    //menu.CLI();
                    break;
                case 'n':
                    System.out.println("");
                    System.out.println("");
                    break;
            }
        } while (getSummary() == 'n');
    }


    //Get batch details (grades, prices per KG, etc.) (includes validation)
    public void batchDetails() {
        String input = enterBatchNo();

        JSONdoc.readGrades(input);
        menu.pressEnterToContinue();
        //menu.CLI();
    }


    public void pricingPrompt() {
        //String checkDate = formatter.format(date);
        boolean dateCheck = JSONdoc.pricingPrompt(formatter.format(date));

        if (dateCheck) {
            //return;
        } else {
            System.out.println("*No fruit has been priced today*");
            pricing();
        }
    }

    //Add pricing
    public void pricing() {
        do {
            System.out.println("Choose a fruit to price:");
            System.out.println("1. STRAWBERRIES");
            System.out.println("2. RASPBERRIES");
            System.out.println("3. BLACKBERRIES");
            System.out.println("4. GOOSEBERRIES");
            System.out.println(">");
            boolean validInput = false;
            while (!validInput) {
                try {
                    setFruitTypeNo(scanner.nextInt());
                    validInput = true;
                } catch (InputMismatchException e) {
                    if (scanner.hasNext("quit")) {
                        System.exit(0);
                    } else if (scanner.hasNext("menu")) {
                        menu.CLI();
                        break;
                    }
                    System.out.println("Please enter an integer!");
                    scanner.next();
                }
            }

            if (getFruitTypeNo() < 1 || getFruitTypeNo() > 4) {
                System.out.println("!! " + getFruitTypeNo() + " is not a valid option !!");
                System.out.println("");
            }
        } while (getFruitTypeNo() < 1 || getFruitTypeNo() > 4);

        //get fruit name from fruit type
        switch (getFruitTypeNo()) {
            case 1:
                setFruitTypeName("STRAWBERRIES");
                setPricing(getFruitTypeName());
                break;
            case 2:
                setFruitTypeName("RASPBERRIES");
                setPricing(getFruitTypeName());
                break;
            case 3:
                setFruitTypeName("BLACKBERRIES");
                setPricing(getFruitTypeName());
                break;
            case 4:
                setFruitTypeName("GOOSEBERRIES");
                setPricing(getFruitTypeName());
                break;
        }

        //menu.pressEnterToContinue();
        //menu.CLI();
    }

    //set pricing (was a repetitive method)
    public void setPricing(String theFruitType) {
        //reset in case changed by other functionality
        summary = 'A';

        //for formatting/padding the input to 2 decimal places
        DecimalFormat df = new DecimalFormat("#0.00");

        do {
            System.out.println("Payments to farmers            System date: " + formatter.format(date)); // print header
            System.out.println("Enter prices below (£ per KG) for " + getFruitTypeName() + ":");

            //get Grade A
            double tempA = 0;
            do {
                System.out.println("Grade A:");
                boolean validInput = false;
                while (!validInput) {
                    try {
                        tempA = scanner.nextDouble();
                        validInput = true;
                    } catch (InputMismatchException e) {
                        if (scanner.hasNext("quit")) {
                            menu.hotKeys("quit");
                        } else if (scanner.hasNext("menu")) {
                            menu.hotKeys("menu");
                            break;
                        }
                        System.out.println("Please enter an integer!");
                        scanner.next();
                    }
                }

                if (tempA < 0 || tempA > 999) {
                    System.out.println("!!" + tempA + " is out of bounds for the pricing !!");
                    System.out.println("");
                }
            } while (tempA < 0 || tempA > 999);
            //format grade to specification layout
            tempA = (double) Math.round(tempA * 100) / 100; //round double up/down
            String formattedA = df.format(tempA); //format


            //get Grade B
            double tempB = 0;
            do {
                System.out.println("Grade B:");
                boolean validInput = false;
                while (!validInput) {
                    try {
                        tempB = scanner.nextDouble();
                        validInput = true;
                    } catch (InputMismatchException e) {
                        if (scanner.hasNext("quit")) {
                            menu.hotKeys("quit");
                        } else if (scanner.hasNext("menu")) {
                            menu.hotKeys("menu");
                            break;
                        }
                        System.out.println("Please enter an integer!");
                        scanner.next();
                    }
                }

                if (tempB < 0 || tempB > 999) {
                    System.out.println("!!" + tempB + " is out of bounds for the pricing !!");
                    System.out.println("");
                }
            } while (tempB < 0 || tempB > 999);
            //format grade to specification layout
            tempB = (double) Math.round(tempB * 100) / 100; //round double up/down
            String formattedB = df.format(tempB); //format


            //get Grade C
            double tempC = 0;
            do {
                System.out.println("Grade C:");
                boolean validInput = false;
                while (!validInput) {
                    try {
                        tempC = scanner.nextDouble();
                        validInput = true;
                    } catch (InputMismatchException e) {
                        if (scanner.hasNext("quit")) {
                            menu.hotKeys("quit");
                        } else if (scanner.hasNext("menu")) {
                            menu.hotKeys("menu");
                            break;
                        }
                        System.out.println("Please enter an integer!");
                        scanner.next();
                    }
                }

                if (tempC < 0 || tempC > 999) {
                    System.out.println("!!" + tempC + " is out of bounds for the pricing !!");
                    System.out.println("");
                }
            } while (tempC < 0 || tempC > 999);
            //format grade to specification layout
            tempC = (double) Math.round(tempC * 100) / 100; //round double up/down
            String formattedC = df.format(tempC); //format


            //Check user is satisfied with inputs
            do {
                System.out.println("");
                System.out.println(getFruitTypeName());
                System.out.println("Grade A = £" + formattedA + " (per KG)");
                System.out.println("Grade B = £" + formattedB + " (per KG)");
                System.out.println("Grade C = £" + formattedC + " (per KG)");
                System.out.println("Is this correct y/n? ");
                setSummary(scanner.next().charAt(0));
                if (getSummary() != 'y' && getSummary() != 'n') {
                    System.out.println("!! Please answer with y or n !!");
                    System.out.println("");
                }
            } while (getSummary() != 'y' && getSummary() != 'n');

            switch (getSummary()) {
                case 'y':
                    JSONdoc.JSONpricing(formatter.format(date), theFruitType, formattedA, formattedB, formattedC);
                    //small interface glitch required program to be paused to remedy issue
                    try {
                        Thread.sleep(1000);                 //1000 milliseconds is one second.
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    //user can be directed back to pricing page
                    char cont;
                    do {
                        System.out.println("");
                        System.out.println("Would you like to continue pricing? y/n");
                        cont = scanner.next().charAt(0);
                        if (cont != 'y' && cont != 'n') {
                            System.out.println("!! Please answer with y or n !!");
                            System.out.println("");
                        }
                    } while (cont != 'y' && cont != 'n');
                    switch (cont) {
                        case 'y':
                            System.out.println("");
                            pricing();
                            break;
                        case 'n':
                            menu.pressEnterToContinue();
                            break;
                    }
                    break;
                case 'n':
                    System.out.println("");
                    System.out.println("");
                    break;
            }
        } while (getSummary() == 'n');
    }


    //used for entering transaction date (includes validation)
    public void enterTransactionDate() {
        String input;
        int dateCounter;
        boolean checked = true;
        do {
            checked = true;
            System.out.println("Enter transaction date: > ");
            input = scanner.nextLine();
            dateCounter = JSONdoc.transactionChecker(input);

            menu.hotKeys(input);

            if (dateCounter > 0) {
                Logs logs = new Logs();
                System.out.println("");
                logs.logRetreival(input);
                System.out.println("");
                System.out.println(dateCounter +" TRANSACTION/S FOR THIS DATE");
                System.out.println("Daily totals:");
                System.out.println("Batch#           Fruit       Farm  Weight  Date    Value");
                JSONdoc.transactionList(input);
                break;
            } else {
                System.out.println(dateCounter + " TRANSACTION/S FOR THIS DATE");
                System.out.println("");
                checked = false;
            }
        } while (checked == false);
        System.out.println("");
        menu.pressEnterToContinue();
    }

}

