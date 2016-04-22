/**
 * Created by Dean on 4/7/2016.
 */
import java.io.*;
import java.util.HashMap;
import java.util.Stack;


public class hw3 {
    public static void hw3(String inFile, String outFile){
        try {
            FileReader fr = new FileReader(inFile);
            BufferedReader bReader = new BufferedReader(fr);
            FileWriter fw = new FileWriter(outFile);
            BufferedWriter bWriter = new BufferedWriter(fw);
            Stack<String> elemStack = new Stack<>();
            HashMap<String, String> bind_stack = new HashMap<>();
            String buffer;
            while((buffer = bReader.readLine()) != null){
                //read each line do operations
                String[] splitter = buffer.split("\\s+");
                if(splitter[0].equals("quit")) break;
                ops_Check(splitter, elemStack, bind_stack);
            }

            //System.out.println(elemStack); //need to reverse?
            System.out.println("bind_stack: " + bind_stack);
            while(elemStack.isEmpty() != true){
                //System.out.println(elemStack.pop());
                String b = elemStack.pop();
                if(b.startsWith("\"") && b.endsWith("\"")){
                    b = b.replaceAll("^\"|\"$", "");
                }
                bWriter.write(b + '\n');
            }
            bWriter.close();
            bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void ops_Check(String[] op, Stack<String> tarStack, HashMap<String, String> bind_stack){

        switch(op[0]) {
            case "push":
                if(op[1].contains(".")){
                    tarStack.push(":error:");
                }else {
                    tarStack.push(op[1]);
                }
                break;
            case "pop":
                if (tarStack.isEmpty()) {
                    tarStack.push(":error:");
                } else if(tarStack.peek().equals(":unit:")) {
                    System.out.println("Womp");
                    tarStack.pop();
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
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        yVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(x) + Integer.parseInt(y)));
                    } catch (NumberFormatException nonInt) {
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy);
                            tarStack.push(varHoldx);
                            tarStack.push(":error:");
                        }else {
                            tarStack.push(y);
                            tarStack.push(x);
                            tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "sub":
                if (tarStack.size() < 2) {
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(y) - Integer.parseInt(x)));
                    } catch (NumberFormatException nonInt) {
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy);
                            tarStack.push(varHoldx);
                            tarStack.push(":error:");
                        }
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
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(y) * Integer.parseInt(x)));
                    } catch (NumberFormatException nonInt) {
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy);
                            tarStack.push(varHoldx);
                            tarStack.push(":error:");
                        }
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
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    try {
                        tarStack.push(String.valueOf(Integer.parseInt(y) / Integer.parseInt(x)));
                    } catch (NumberFormatException | ArithmeticException  badInt) {
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy);
                            tarStack.push(varHoldx);
                            tarStack.push(":error:");
                        }else {
                            tarStack.push(y);
                            tarStack.push(x);
                            tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "rem":
                if (tarStack.size() < 2) {
                    tarStack.push(":error:");
                }else{
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    try{
                        tarStack.push(String.valueOf(Integer.parseInt(y) % Integer.parseInt(x)));
                    } catch (NumberFormatException | ArithmeticException badInt) {
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy);
                            tarStack.push(varHoldx);
                            tarStack.push(":error:");
                        }else {
                            tarStack.push(y);
                            tarStack.push(x);
                            tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "neg":
                if(tarStack.isEmpty()){
                    tarStack.push(":error:");
                } else {
                    String negString = tarStack.pop();
                    String varHoldx = negString; Boolean xVar = false;
                    if(negString.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(negString)){
                            negString = bind_stack.get(negString);
                        }else{
                            tarStack.push(negString);tarStack.push(":error:");
                            break;
                        }
                    }
                    try {
                        int negative = Integer.parseInt(negString);
                        if(negative == 0){
                            tarStack.push(String.valueOf(negative));
                        }else{
                            tarStack.push(String.valueOf(-negative));
                        }
                    } catch(NumberFormatException e) {
                        if(xVar == true){
                            tarStack.push(varHoldx);
                            tarStack.push(":error:");
                        }else {
                            tarStack.push(negString);
                            tarStack.push(":error:");
                        }
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
            case "and":
                if(tarStack.size() < 2){
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(varHoldy);tarStack.push(varHoldx);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(x.equals(":false:") && y.equals(":true:")){
                        tarStack.push(":false:");
                    } else if(x.equals(":false:") && y.equals(":false:")){
                        tarStack.push(":false:");
                    } else if(x.equals(":true:") && y.equals(":false:")){
                        tarStack.push(":false:");
                    } else if(x.equals(":true:") && y.equals(":true:")){
                        tarStack.push(":true:");
                    } else {
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy); tarStack.push(varHoldx); tarStack.push(":error:");
                        }else{
                            tarStack.push(y); tarStack.push(x); tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "or":
                if(tarStack.size() < 2){
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(y);tarStack.push(x);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(y);tarStack.push(x);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(x.equals(":false:") && y.equals(":false:")){
                        tarStack.push(":false:");
                    } else if(x.equals(":false:") && y.equals(":true:")){
                        tarStack.push(":true:");
                    } else if(x.equals(":true:") && y.equals(":false:")){
                        tarStack.push(":true:");
                    } else if(x.equals(":true:") && y.equals(":true:")){
                        tarStack.push(":true:");
                    } else {
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy); tarStack.push(varHoldx); tarStack.push(":error:");
                        }else {
                            tarStack.push(y); tarStack.push(x); tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "equal":
                if(tarStack.size() < 2){
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(y);tarStack.push(x);tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(y); tarStack.push(x); tarStack.push(":error:");
                            break;
                        }
                    }
                    try{
                        if(Integer.parseInt(x) == Integer.parseInt(y)){
                            tarStack.push(":true:");
                        }else{
                            tarStack.push(":false:");
                        }
                    }catch(NumberFormatException nonInt){
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy); tarStack.push(varHoldx); tarStack.push(":error:");
                        }else {
                            tarStack.push(y); tarStack.push(x); tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "not":
                if(tarStack.isEmpty()){
                    tarStack.push(":error:");
                } else {
                    String x = tarStack.pop();
                    String varHoldx = x;
                    Boolean xVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(x);
                            tarStack.push(":error:");
                            break;
                        }
                    }
                    if(x.equals(":true:")){
                        tarStack.push(":false:");
                    }else if(x.equals(":false:")){
                        tarStack.push(":true:");
                    }else{
                        if(xVar == true){
                            tarStack.push(varHoldx);
                            tarStack.push(":error:");
                        }else {
                            tarStack.push(x);
                            tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "lessThan":
                if(tarStack.size() < 2){
                    tarStack.push(":error:");
                } else{
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    String varHoldx = x; String varHoldy = y; Boolean xVar = false; Boolean yVar = false;
                    if(x.matches("^[a-zA-Z]+[0-9]*")){
                        xVar = true;
                        if(bind_stack.containsKey(x)){
                            x = bind_stack.get(x);
                        }else{
                            tarStack.push(y);
                            tarStack.push(x);
                            tarStack.push(":error:");
                            break;
                        }
                    }
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        yVar = true;
                        if(bind_stack.containsKey(y)){
                            y = bind_stack.get(y);
                        }else{
                            tarStack.push(y);
                            tarStack.push(x);
                            tarStack.push(":error:");
                            break;
                        }
                    }
                    try{
                        if(Integer.parseInt(y) < Integer.parseInt(x)){
                            tarStack.push(":true:");
                        }else{
                            tarStack.push(":false:");
                        }
                    }catch(NumberFormatException nonInt){
                        if(xVar == true || yVar == true){
                            tarStack.push(varHoldy); tarStack.push(varHoldx); tarStack.push(":error:");
                        }else {
                            tarStack.push(y); tarStack.push(x); tarStack.push(":error:");
                        }
                    }
                }
                break;
            case "if":
                if(tarStack.size() < 3){
                    tarStack.push(":error:");
                } else{
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    String z = tarStack.pop();
                    if(z.equals(":true:")){
                        tarStack.push(x);
                    } else if(z.equals(":false:")){
                        tarStack.push(y);
                    } else{
                        tarStack.push(z); tarStack.push(y); tarStack.push(x); tarStack.push(":error:");
                    }
                }
                break;
            case "bind":
                if(tarStack.size() < 2){
                    tarStack.push(":error:");
                } else{
                    String x = tarStack.pop();
                    String y = tarStack.pop();
                    if(y.matches("^[a-zA-Z]+[0-9]*")){
                        if(x.matches("^[a-zA-Z]+[0-9]*")){
                            if(bind_stack.containsKey(x)){
                                x = bind_stack.get(x);
                                bind_stack.put(y, x);
                                tarStack.push(":unit:");
                            }
                        }else if(x.equals(":unit:")) {
                            tarStack.push(":unit:");
                        }else if(bind_stack.containsKey(y)){
                            bind_stack.put(y, x);
                        }else{
                            if(x.equals(":error:")){
                                tarStack.push(y);
                                tarStack.push(x);
                                tarStack.push(":error:");
                            }else {
                                bind_stack.put(y, x);
                                tarStack.push(":unit:");
                            }
                        }
                    }else{
                        tarStack.push(y);
                        tarStack.push(x);
                        tarStack.push(":error:");
                    }
                }
                break;
            case "let":
                tarStack.push("let");
                break;
            case "end":
                String return_val = tarStack.pop();
                String throw_away = return_val;
                while(!throw_away.equals("let")){
                    throw_away = tarStack.pop();
                }
                tarStack.push(return_val);
                break;
        }
    }
}

