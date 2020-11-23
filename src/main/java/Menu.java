/*
 * CS112 Programming
 * Year 1, term 3
 *
 * Coursework Project 2019/20
 * by nfb19202 - Calum Doughty
 *
 */

import java.util.Scanner;

public class Menu {

    public void CLI() {
        Scanner scanner = new Scanner(System.in);
        Batch batch = new Batch();

        System.out.println("");
        System.out.println("Welcome to the Renfrewshire Soft Fruits cooperative");
        //System.out.println("");

        //check if today's grade prices have been submitted
        batch.pricingPrompt();


        System.out.println("");
        System.out.println("Choose an option:");
        System.out.println("1. Create a new batch");
        System.out.println("2. List all batches");
        System.out.println("3. View details of a batch");
        System.out.println("4. Sort/Grade a batch");
        System.out.println("5. Payments");
        System.out.println("6. Transactions Report");
        System.out.println("quit  -This can be typed at any point to leave");
        System.out.println("menu  -This can be typed at any point to come back to menu");
        System.out.println("");

        String input;
        boolean validInput;
        do {
            validInput = false;
            System.out.print(">>> ");

            input = scanner.nextLine();
            System.out.println("");
            //System.out.print("You have selected option: " + input);

            //Call appropriate functionality from input
            switch (input) {
                case "1":
                    validInput = true;
                    System.out.println("Create a new batch");
                    batch.newBatch();
                    break;
                case "2":
                    validInput = true;
                    batch.listAllBatches();
                    break;
                case "3":
                    validInput = true;
                    batch.batchDetails();
                    break;
                case "4":
                    validInput = true;
                    batch.sortBatches();
                    break;
                case "5":
                    validInput = true;
                    batch.pricing();
                    break;
                case "6":
                    validInput = true;
                    batch.enterTransactionDate();
                    break;
                case "menu":
                    validInput = true;
                    CLI();
                    break;
                case "quit":
                    validInput = true;
                    System.out.println("Goodbye");
                    break;
                default:
                    break;
            }
        } while (validInput == false);
    }


    //Press enter button to proceed to next program stage
    public void pressEnterToContinue() {
        System.out.println("** Press ENTER to return to menu **");
        try {
            System.in.read();
            CLI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This is used to allow users to return to menu or quit throughout the program
    public void hotKeys(String hotKey) {
        if (hotKey.equals("quit")) {
            System.exit(0);
        } else if (hotKey.equals("menu")) {
            CLI();
        }
    }
}
