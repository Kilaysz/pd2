package hw_pd2.hw1;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class RegExp2 {

    public static void isPalindrome(String str) {
        int left = 0;
        int right = str.length() - 1;
        for(int index=0; index < str.length()/2; index++){
            if (str.charAt(left) != str.charAt(right)) { 
                System.out.print("N");
                return;
            }
            left++;
            right--;
        }
        System.out.print("Y");
    }
    
    // OK
    public static boolean isContaining(String line, String str1) {
        if (str1.length() > line.length()) {
            return false;
        }
        for (int i = 0; i <= line.length() - str1.length(); i++) {
            boolean found = true;
            for (int j = 0; j < str1.length(); j++) {
                if (line.charAt(i + j) != str1.charAt(j)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return true;
            }
        }
        return false;
    }
    
    // OK
    public static boolean isRepeatedNtimes(String line, int Ntimes, String str2){ 
        if (line == null || str2 == null || str2.length() > line.length()) {
            return false;
        }
        int count = 0;
        for (int i = 0; i <= line.length() - str2.length(); i++) {
            boolean found = true;
            for (int j = 0; j < str2.length(); j++) {
                if (line.charAt(i + j) != str2.charAt(j)) {
                    found = false;
                    break;
                }
            }
            if (found) {
               count++;
               if(count == Ntimes){
                    return true;
               }
            }
        }
        return false;
    }

    // OK
    public static void isAXBB(String line){
        if(!isContaining(line, "a")||!isContaining(line, "bb")){
            System.out.println("N");
            return;
        }
        int i = 0;
        while(i<line.length()){
            if(line.charAt(i) == 'a'){
                break;
            } 
            i++;
        }
        // continue to iterate through the string
        while(i<line.length()-1){
            if(line.charAt(i)=='b'&&line.charAt(i+1)=='b'){
                System.out.println("Y");
                return;
            }
            i++;
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
                isPalindrome(line);
                System.out.print(",");          
                System.out.print(isContaining(line, str1)?"Y":"N");
                System.out.print(",");
                System.out.print(isRepeatedNtimes(line, s2Count, str2)?"Y":"N");
                System.out.print(",");            
                isAXBB(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 