/*
 * CS112 Programming
 * Year 1, term 3
 *
 * Coursework Project 2019/20
 * by nfb19202 - Calum Doughty
 *
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.text.DecimalFormat;

/*
// FOR UNDERSTANDING AND IMPLEMENTING JSON
https://stackabuse.com/reading-and-writing-json-in-java/
https://www.w3schools.com/js/js_json_parse.asp
https://howtodoinjava.com/library/json-simple-read-write-json-examples/
https://crunchify.com/how-to-read-json-object-from-file-in-java/
https://www.tutorialspoint.com/how-to-write-create-a-json-file-using-java
https://stackoverflow.com/questions/28228212/how-to-make-json-object-of-json-objects
 */

public class JSONdoc {

    //used to format pricing throughout program
    //DecimalFormat df = new DecimalFormat("0.00");

    //create a new batch
    public static void create(String batchNo, String farmNo, String date, String fruitType, int fruitWeight) {
        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("Batch Number", batchNo);
        jsonObject.put("Farm Number", farmNo);
        jsonObject.put("Received Date", date);
        jsonObject.put("Fruit Type", fruitType);
        jsonObject.put("Fruit Weight(KG)", fruitWeight);
        try {
            FileWriter file = new FileWriter("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + batchNo + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
        //System.out.println("JSON file created: "+jsonObject);
        Logs logs = new Logs();
        logs.logJSON(jsonObject);

    }


    //add on the sort/grade
    public static void addGrade(String batchNoInput, int gradeA, int gradeB, int gradeC, int gradeRejected) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + batchNoInput + ".json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject batchObject = (JSONObject) obj;

            //Get batch number
            //String batchNo = (String) batchObject.get("Batch Number");
            batchObject.put("Grade A", gradeA);
            batchObject.put("Grade B", gradeB);
            batchObject.put("Grade C", gradeC);
            batchObject.put("Grade Rej", gradeRejected);
            try {
                FileWriter file = new FileWriter("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + batchNoInput + ".json");
                file.write(batchObject.toJSONString());
                file.close();
            } catch (IOException e) {
                //
                e.printStackTrace();
            }
            //System.out.println("JSON file created: "+jsonObject);
            Logs logs = new Logs();
            logs.logJSON(batchObject);

            //Add appropriate daily pricing to JSON file so we have accurate PAY of batch
            allocateGradePricing(batchNoInput, gradeA, gradeB, gradeC, gradeRejected);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //Retrieve all batches
    public static void readAll() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        /* Function to get File Name */
        File folder = new File("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles");
        File[] listOfFiles = folder.listFiles();

        System.out.println("Batch#           Fruit       Farm  Weight  Date    Value");

        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();

            try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + fileName)) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);

                JSONObject batchObject = (JSONObject) obj;

                //Get batch number
                String batchNo = (String) batchObject.get("Batch Number");
                //Get farm number
                String farmNo = (String) batchObject.get("Farm Number");
                //Get date which order was received
                String date = (String) batchObject.get("Received Date");
                //Get batch fruit type
                String fruitType = (String) batchObject.get("Fruit Type");
                //Get batch fruit weight
                long fruitWeight = (long) batchObject.get("Fruit Weight(KG)");

                try{
                    //get total price
                    double value = (double) batchObject.get("Total Price (£)");
                    System.out.println(batchNo + "  " + fruitType + "  " + farmNo + "  " + fruitWeight + "KG  " + date + "  £" + value);
                }
                catch(NullPointerException e){
                    //if item has not been graded then Explain "Not Graded" rather than throw exception
                    System.out.println(batchNo + "  " + fruitType + "  " + farmNo + "  " + fruitWeight + "KG  " + date + "  Not Graded");
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    //Retrieve a specific batch
    public static void readSpecific(String theBatchNo) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        System.out.println("Batch#           Fruit       Farm  Weight  Date    Value");

            try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + theBatchNo + ".json")) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);

                JSONObject batchObject = (JSONObject) obj;

                //Get batch number
                String batchNo = (String) batchObject.get("Batch Number");
                //Get farm number
                String farmNo = (String) batchObject.get("Farm Number");
                //Get date which order was received
                String date = (String) batchObject.get("Received Date");
                //Get batch fruit type
                String fruitType = (String) batchObject.get("Fruit Type");
                //Get batch fruit weight
                long fruitWeight = (long) batchObject.get("Fruit Weight(KG)");
                //get total price
//                double value = (double) batchObject.get("Total Price (£)");
//                System.out.println(batchNo + "  " + fruitType + "  " + farmNo + "  " + fruitWeight + "KG  " + date + "  £" + value);

                try{
                    //get total price
                    double value = (double) batchObject.get("Total Price (£)");
                    System.out.println(batchNo + "  " + fruitType + "  " + farmNo + "  " + fruitWeight + "KG  " + date + "  £" + value);
                }
                catch(NullPointerException e){
                    //if item has not been graded then Explain "Not Graded" rather than throw exception
                    System.out.println(batchNo + "  " + fruitType + "  " + farmNo + "  " + fruitWeight + "KG  " + date + "  Not Graded");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

    }


    //Check batch# exists
    public static boolean checker(String batchNoToCheck) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        /* Function to get File Name */
        File folder = new File("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles");
        File[] listOfFiles = folder.listFiles();


        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();

            try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + fileName)) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);

                JSONObject batchObject = (JSONObject) obj;

                //Get batch number
                String batchNo = (String) batchObject.get("Batch Number");

                if(batchNoToCheck.equals(batchNo)){
                    return true;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static void readGrades(String theBatchNo){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();


        try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + theBatchNo + ".json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject batchObject = (JSONObject) obj;

            //Get batch number
            String batchNo = (String) batchObject.get("Batch Number");
            //Get batch fruit weight
            long fruitWeight = (long) batchObject.get("Fruit Weight(KG)");
            //Get batch fruit type
            String fruitType = (String) batchObject.get("Fruit Type");
            //Get gradeA
            long gradeA = (long) batchObject.get("Grade A");
            //Get gradeB
            long gradeB = (long) batchObject.get("Grade B");
            //Get gradeC
            long gradeC = (long) batchObject.get("Grade C");
            //Get gradeR
            long gradeRejected = (long) batchObject.get("Grade Rej");

            double gradeWeight;
            DecimalFormat df = new DecimalFormat("0.00");
            String roundedWeight;
            String roundedWeightGradePrice;

            //Get specific batch details for Grade A produce
            gradeWeight = (double)fruitWeight / 100 * (double)gradeA;
            roundedWeightGradePrice = (String) batchObject.get("Grade A Price");
            roundedWeight = df.format(gradeWeight);
            System.out.println("Grade A " + gradeA + "% = " + roundedWeight + "KG = £" + roundedWeightGradePrice);

            //Get specific batch details for Grade B produce
            gradeWeight = (double)fruitWeight / 100 * (double)gradeB;
            roundedWeightGradePrice = (String) batchObject.get("Grade B Price");
            roundedWeight = df.format(gradeWeight);
            System.out.println("Grade B " + gradeB + "% = " + roundedWeight + "KG = £" + roundedWeightGradePrice);

            //Get specific batch details for Grade C produce
            gradeWeight = (double)fruitWeight / 100 * (double)gradeC;
            roundedWeightGradePrice = (String) batchObject.get("Grade C Price");
            roundedWeight = df.format(gradeWeight);
            System.out.println("Grade C " + gradeC + "% = " + roundedWeight + "KG = £" + roundedWeightGradePrice);

            //Get specific batch details for Rejected Grade produce (no pricing included)
            gradeWeight = (double)fruitWeight / 100 * (double)gradeRejected;
            roundedWeight = df.format(gradeWeight);
            System.out.println("Grade Rejected " + gradeRejected + "% = " + roundedWeight + "KG");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            //if there are no grades done yet then display this
            System.out.println("NO GRADES TO SHOW");
            System.out.println("");
        }
    }


    //Check if current date exists in payment file (if not then prompt user to input new prices)
    public static boolean pricingPrompt(String currentDate) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();


        try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/payments/Pricing.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject batchObject = (JSONObject) obj;

            //Get stored date
            String storedDate = (String) batchObject.get("Date");

            //Check the dates match
            if(currentDate.equals(storedDate)){
                return true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    //add the pricing to JSON Pricing.JSON file
    public static void JSONpricing(String currentDate, String theFruitType, String a, String b, String c) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/payments/Pricing.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject batchObject = (JSONObject) obj;


            //JSONObject batchObject = (JSONObject) obj;
            batchObject.put("Date", currentDate);

            switch(theFruitType){
                case "STRAWBERRIES":
                    JSONObject st = new JSONObject();
                    st.put("Grade A", a);
                    st.put("Grade B", b);
                    st.put("Grade C", c);
                    batchObject.put("STRAWBERRIES", st);
                    break;
                case "RASPBERRIES":
                    JSONObject ra = new JSONObject();
                    ra.put("Grade A", a);
                    ra.put("Grade B", b);
                    ra.put("Grade C", c);
                    batchObject.put("RASPBERRIES", ra);
                    break;
                case "BLACKBERRIES":
                    JSONObject bl = new JSONObject();
                    bl.put("Grade A", a);
                    bl.put("Grade B", b);
                    bl.put("Grade C", c);
                    batchObject.put("BLACKBERRIES", bl);
                    break;
                case "GOOSEBERRIES":
                    JSONObject go = new JSONObject();
                    go.put("Grade A", a);
                    go.put("Grade B", b);
                    go.put("Grade C", c);
                    batchObject.put("GOOSEBERRIES", go);
                    break;
            }

            try {
                FileWriter file = new FileWriter("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/payments/Pricing.json");
                file.write(batchObject.toJSONString());
                file.close();
            } catch (IOException e) {
                //
                e.printStackTrace();
            }
            Logs logs = new Logs();
            logs.logAppend(batchObject);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //get the grade pricing from Pricing.JSON file to calculate farmers weight & grade price for individual fruit
    public static double calculateWeightGradePrice(double gradeWeight, char grade, String fruitType){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();


        try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/payments/Pricing.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject batchObject = (JSONObject) obj;

            JSONObject objectType = (JSONObject) batchObject.get(fruitType);
            String gradePrice;


            switch(grade){
                case 'A':
                    gradePrice = (String) objectType.get("Grade A");
                    double priceA = Double.valueOf(gradePrice);
                    //double priceA = (double) objectType.get("Grade A");
                    //double calculation = gradeWeight * priceA;
                    return gradeWeight * priceA;
                case 'B':
                    gradePrice = (String) objectType.get("Grade B");
                    double priceB = Double.valueOf(gradePrice);
                    return gradeWeight * priceB;
                case 'C':
                    gradePrice = (String) objectType.get("Grade C");
                    double priceC = Double.valueOf(gradePrice);
                    return gradeWeight * priceC;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //set the pricing for specific grades for a specific batch
    public static void allocateGradePricing(String batchNoInput, int gradeA, int gradeB, int gradeC, int gradeRejected){

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();


        try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + batchNoInput + ".json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject batchObject = (JSONObject) obj;

            //Get batch fruit weight
            long fruitWeight = (long) batchObject.get("Fruit Weight(KG)");
            //Get batch fruit type
            String fruitType = (String) batchObject.get("Fruit Type");

            double gradeWeight;
            double weightGradePrice;
            DecimalFormat df = new DecimalFormat("0.00");
            //String roundedWeight;
            String roundedWeightGradePrice;
            double total = 0;

            //Calculate all factors that contribute to total price paid for weight of fruit for this batch
            gradeWeight = (double)fruitWeight / 100 * (double)gradeA;
            weightGradePrice = calculateWeightGradePrice(gradeWeight, 'A', fruitType);
            roundedWeightGradePrice = df.format(weightGradePrice);
            String gradeAWeightPrice = roundedWeightGradePrice;
            total = total + Double.valueOf(roundedWeightGradePrice);

            //Calculate all factors that contribute to total price paid for weight of fruit for this batch
            gradeWeight = (double)fruitWeight / 100 * (double)gradeB;
            weightGradePrice = calculateWeightGradePrice(gradeWeight, 'B', fruitType);
            roundedWeightGradePrice = df.format(weightGradePrice);
            String gradeBWeightPrice = roundedWeightGradePrice;
            total = total + Double.valueOf(roundedWeightGradePrice);

            //Calculate all factors that contribute to total price paid for weight of fruit for this batch
            gradeWeight = (double)fruitWeight / 100 * (double)gradeC;
            weightGradePrice = calculateWeightGradePrice(gradeWeight, 'C', fruitType);
            roundedWeightGradePrice = df.format(weightGradePrice);
            String gradeCWeightPrice = roundedWeightGradePrice;
            total = total + Double.valueOf(roundedWeightGradePrice);


            //Price per weight of grade must be stored as prices are subject to change.
            batchObject.put("Grade A Price", gradeAWeightPrice);
            batchObject.put("Grade B Price", gradeBWeightPrice);
            batchObject.put("Grade C Price", gradeCWeightPrice);
            batchObject.put("Total Price (£)", total);
            try {
                FileWriter file = new FileWriter("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + batchNoInput + ".json");
                file.write(batchObject.toJSONString());
                file.close();
            } catch (IOException e) {
                //
                e.printStackTrace();
            }
            //System.out.println("JSON file created: "+jsonObject);
            Logs logs = new Logs();
            logs.logJSON(batchObject);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    //Check for transactions on this date
    public static int transactionChecker(String dateToCheck) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        /* Function to get File Name */
        File folder = new File("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles");
        File[] listOfFiles = folder.listFiles();

        int dateCounter = 0;


        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();

            try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + fileName)) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);
                //System.out.println(obj);

                JSONObject batchObject = (JSONObject) obj;
                //System.out.println(batchObject);

                //Get batch number
                String receivedDate = (String) batchObject.get("Received Date");

                if(dateToCheck.equals(receivedDate)){
                    dateCounter++;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateCounter;
    }


    //List transactions for this date
    public static void transactionList(String dateToCheck) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        /* Function to get File Name */
        File folder = new File("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles");
        File[] listOfFiles = folder.listFiles();

        //get total price FOR THIS SPECIFIC DATE
        double totalDateCost = 0;
        //For FORMATTING pricing
        DecimalFormat df = new DecimalFormat("0.00");


        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();

            try (FileReader reader = new FileReader("C:/Users/GA/Documents/StrathclydeUni/Year 1/Programming 3 (CS112)/week5/nfb19202_SoftFruits/batchFiles/" + fileName)) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);
                JSONObject batchObject = (JSONObject) obj;

                //Get batch number
                String receivedDate = (String) batchObject.get("Received Date");

                if(dateToCheck.equals(receivedDate)){

                    //Get batch number
                    String batchNo = (String) batchObject.get("Batch Number");
                    //Get farm number
                    String farmNo = (String) batchObject.get("Farm Number");
                    //Get date which order was received
                    String date = (String) batchObject.get("Received Date");
                    //Get batch fruit type
                    String fruitType = (String) batchObject.get("Fruit Type");
                    //Get batch fruit weight
                    long fruitWeight = (long) batchObject.get("Fruit Weight(KG)");

                    try{
                        //get total price
                        double value = (double) batchObject.get("Total Price (£)");
                        String roundedValue = df.format(value);
                        System.out.println(batchNo + "  " + fruitType + "  " + farmNo + "  " + fruitWeight + "KG  " + date + "  £" + roundedValue);
                        totalDateCost = totalDateCost + value;
                    }
                    catch(NullPointerException e){
                        System.out.println(batchNo + "  " + fruitType + "  " + farmNo + "  " + fruitWeight + "KG  " + date + "  Not Graded");
                    }

//                    try{
//                        //get total price FOR THIS SPECIFIC DATE
//                        double value = (double) batchObject.get("Total Price (£)");
//                        //System.out.println("");
//                        System.out.println("TOTAL PAID:     £" + totalDateCost);
//                    }
//                    catch(NullPointerException e){
//                        System.out.println("NO ORDERS HERE ARE GRADED");
//                    }

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        try{
            //get total price FOR THIS SPECIFIC DATE
            String roundedTotalDateCost = df.format(totalDateCost);
            System.out.println("TOTAL PAID:  £" + roundedTotalDateCost);
        }
        catch(NullPointerException e){
            System.out.println("NO ORDERS HERE ARE GRADED");
        }
    }


}
