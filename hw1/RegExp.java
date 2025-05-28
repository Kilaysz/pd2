package hw_pd2.hw1;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class RegExp {
    // OK 
    public static void isPallindrome(String line){ 
        char ch;
        String reverseString = "";
            for (int i=0; i<line.length(); i++){
                ch= line.charAt(i); // extracts each character
                reverseString = ch+reverseString; // adds each character in front of the existing string
            }
        System.out.print(line.equals(reverseString)?"Y":"N");
    }

    // needs to be modified    
    public static void isRepeatedNtimes(String line, int Ntimes, String str2){ // needs to be changed
        int count = 0;
        int index = line.indexOf(str2); // index for the first occurrence
        while (index != -1) { // looking until the str2 can't be found
            count++;
            index = line.indexOf(str2, index + 1); // looking for the next occurence
        }
        System.out.print(count>=Ntimes?"Y":"N"); // if the str2 occurs more than N times
    }
    
    // needs to be modified
    public static void isAXBB(String line){

        int indexofA = line.indexOf("a");
        int indexofBB = line.indexOf("bb");
        if(!line.contains("a")||!line.contains("bb")){
            System.out.println("N");
            return;
        } else if (indexofA<indexofBB){
            System.out.println("Y");
            return;
        } 
        while(line.indexOf("bb",indexofBB)!=-1){
            if(line.indexOf("a", indexofA)<line.indexOf("bb", indexofBB)){
                System.out.println("Y");
                return;
            } 
            indexofBB++;
        }
        System.out.println("N");
    }
    
    public static void main(String[] args) {
        String str1 = args[1].toLowerCase();
        String str2 = args[2].toLowerCase();
        int s2Count = Integer.parseInt(args[3]);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                isPallindrome(line);
                System.out.print(",");          
                System.out.print(line.contains(str1)==true?"Y":"N");
                System.out.print(",");
                isRepeatedNtimes(line, s2Count, str2);
                System.out.print(",");            
                isAXBB(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 