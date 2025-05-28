import java.util.HashMap;
import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> Buffer = new Stack<>();
        HashMap <Character, Character> check = new HashMap<>();
        check.put(')', '(');
        check.put(']', '[');
        check.put('}', '{');
        int i = 0;
        while(i<s.length()){
            if(s.charAt(i)=='{'){
                Buffer.push('{');
            } else if(s.charAt(i)=='('){
                Buffer.push('(');
            } else if(s.charAt(i)=='['){
                Buffer.push('[');
            } else if(Buffer.pop()!=check.get(s.charAt(i))){
                return false;
            } 
            i++;
        }
        return Buffer.empty()?true:false;
    }
}