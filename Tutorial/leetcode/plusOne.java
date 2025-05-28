class Solution {
    public int[] plusOne(int[] digits) {
        digits[digits.length-1]+=1;
        if(digits[digits.length-1]!=10){
            return digits;
        }
        for(int i=digits.length-1;i>0;i--){
            if(digits[0]==10) break;
            digits[i]=0;
            digits[i-1]+=1;
            if(digits[i-1]!=10){
                return digits;
            }
        }
        int[] digits2 = new int[digits.length+1];
        digits2[0]=1;
        digits2[1]=0;
        int i=2 ;
        while(i<digits2.length){
            digits2[i]=digits[i-1];
            i++;
        }
        return digits2;
    }
}