class Solution {
    public int singleNumber(int[] nums) {
        int length = nums.length;
        int check = nums[0];
        for(int i = 1;i<length;i++){
            check ^= nums[i];
        }
        return check;
    }
}