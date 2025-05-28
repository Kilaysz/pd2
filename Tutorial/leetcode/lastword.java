class Solution {
    public int lengthOfLastWord(String s) {
        int index = s.length()-1;
        if(index==0) return 1;
        if(s.charAt(s.length()-1)==' '){
            while(s.charAt(index)==' '){
                index--;
            }
        }
        if(index==0) return 1;
        for(int i = index;i>=0;i--){
            if(s.charAt(i)==' '){
                return index-i;
            } else if(i==0&&s.charAt(0)!=' '){
                return index-i+1;
            }
        }
        return 0;
    }
}