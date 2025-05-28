package leetcode;

import java.util.HashMap;

public class HashTable {
    public int romanToInt(String s) {
        HashMap<Character, Integer> Roman = new HashMap<>();
        Roman.put('I', 1);
        Roman.put('V', 5);
        Roman.put('X', 10);
        Roman.put('L', 50);
        Roman.put('C', 100);
        Roman.put('D', 500);
        Roman.put('M', 1000);
        
        int result = 0;
        for(int i = 0;i < s.length(); i++){
            if(i == s.length()-1){
                result += Roman.get(s.charAt(i));
            } else if(s.charAt(i)=='I'&&s.charAt(i+1)=='V'){
                result += 4;
                i++;
            } else if (s.charAt(i)=='I'&&s.charAt(i+1)=='X'){
                result += 9;
                i++;
            } else if(s.charAt(i)=='X'&&s.charAt(i+1)=='L'){
                result += 40;
                i++;
            } else if(s.charAt(i)=='X'&&s.charAt(i+1)=='C'){
                result += 90;
                i++;
            } else if(s.charAt(i)=='C'&&s.charAt(i+1)=='D'){
                result += 400;
                i++;
            } else if(s.charAt(i)=='C'&&s.charAt(i+1)=='M'){
                result += 900;
                i++;
            } else {
                result += Roman.get(s.charAt(i));
            }
        } 
        return result;
    }
}
