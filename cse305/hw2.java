/**
 * Created by Dean on 3/3/2016.
 */
import java.io.*;
import java.util.Stack;

//TODO: Check rem and div functions - make sure they're working properly
//TODO: Fix file writing
//TODO: Finish other ops - check edge cases

public class hw2 {
    public static void hw2(String inFile, String outFile){
        try {
            FileReader fr = new FileReader(inFile);
            BufferedReader bReader = new BufferedReader(fr);
            FileWriter fw = new FileWriter(outFile);
            BufferedWriter bWriter = new BufferedWriter(fw);
            Stack<String> elemStack = new Stack<>();
            String buffer;
            while((buffer = bReader.readLine()) != null){
                //read each line do operations
                String[] splitter = buffer.split("\\s+");
                if(splitter[0].equals("quit")) break;
                ops_Check(splitter, elemStack);
                //bWriter.write(elemStack);
                //System.out.println(elemStack);
            }
            System.out.println(elemStack); //need to reverse?
            System.out.println("size is : " + elemStack.size());
            while(elemStack.isEmpty() != true){
                //System.out.println(elemStack.pop());
                bWriter.write(elemStack.pop() + '\n');
            }
            bWriter.close();
            bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void ops_Check(String[] op, Stack<String> tarStack){

        switch(op[0]) {
            case "push":
                try {
                    tarStack.push(String.valueOf(Integer.parseInt(op[1])));
                } catch (NumberFormatException nonInt) {
                    tarStack.push(":error:");
                }
                break;
            case "pop":
                if (tarStack.isEmpty()) {
                    tarStack.push(":error:");
                } else {
                    tarStack.pop();
                }
                break;
            case ":true:":
                tarStack.push(":true:");
                break;
            case ":false:":
                tarStack.push(":false:");
                break;
            case ":error:":
                tarStack.push(":error:");
                break;
            case "add":
                if (tarStack.size() < 2) {
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(x) + Integer.parseInt(y)));
                    } catch (NumberFormatException nonInt) {
                        tarStack.push(y);
                        tarStack.push(x);
                        tarStack.push(":error:");
                    }
                }
                break;
            case "sub":
                if (tarStack.size() < 2) {
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(y) - Integer.parseInt(x)));
                    } catch (NumberFormatException nonInt) {
                        tarStack.push(y);
                        tarStack.push(x);
                        tarStack.push(":error:");
                    }
                }
                break;
            case "mul":
                if (tarStack.size() < 2) {
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(y) * Integer.parseInt(x)));
                    } catch (NumberFormatException nonInt) {
                        tarStack.push(y);
                        tarStack.push(x);
                        tarStack.push(":error:");
                    }
                }
                break;
            case "div":
                if (tarStack.size() < 2) {
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(y) / Integer.parseInt(x)));
                    } catch (NumberFormatException | ArithmeticException  badInt) {
                        tarStack.push(y);
                        tarStack.push(x);
                        tarStack.push(":error:");
                    }
                }
                break;
            case "rem":
                if (tarStack.size() < 2) {
                    tarStack.push(":error:");
                }else{
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    try{
                        tarStack.push(String.valueOf(Integer.parseInt(y) % Integer.parseInt(x)));
                    } catch (NumberFormatException | ArithmeticException badInt) {
                        tarStack.push(y);
                        tarStack.push(x);
                        tarStack.push(":error:");
                    }
                }
                break;
            case "neg":
                if(tarStack.isEmpty()){
                    tarStack.push(":error:");
                } else {
                    String negString = tarStack.pop();
                    try {
                        int negative = Integer.parseInt(negString);
                        if(negative == 0){
                            tarStack.push(String.valueOf(negative));
                        }else{
                            tarStack.push(String.valueOf(-negative));
                        }
                    } catch(NumberFormatException e) {
                        tarStack.push(negString);
                        tarStack.push(":error:");
                    }
                }
                break;
            case "swap":
                if(tarStack.size() < 2){
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    tarStack.push(x);
                    tarStack.push(y);
                }
                break;
        }
    }
}
