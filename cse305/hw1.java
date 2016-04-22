import java.io.*;
import java.util.Arrays;

/**
 * Created by Dean on 2/17/2016.
 */
public class hw1 {
    public static void hw1(String inFile, String outFile) {
        try {
            // instantiate BufferedReader
            FileReader fr = new FileReader(inFile);
            BufferedReader bReader = new BufferedReader(fr);
            // instantiate BufferedWriter
            FileWriter fw = new FileWriter(outFile);
            BufferedWriter bWriter = new BufferedWriter(fw);
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            String buffer;
            //read line by line
            while((buffer = bReader.readLine())!= null){
                //create an initially empty array to fill with a-z characters as the line is read
                char[] target = new char[26];
                char[] characters = buffer.toLowerCase().toCharArray();
                for(char c: characters){
                    if(c<=127 && c >= 97){
                        //fill array with a-z
                        target[c-97] = c;
                    }
                }

                String target_to_string = new String(target);
                //check if all characters in target exist in alphabet
                if(alphabet.equals(target_to_string)){
                    bWriter.write("true\n");
                }else{
                    bWriter.write("false\n");
                }
            }
            //close BufferedReader and BufferedWriter
            bWriter.close();
            bReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}