import static java.util.Arrays.*;

import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> TwoSum = new HashMap<>();
        int[] index = new int[2];
        for(int i=0;i<nums.length;i++){
            if(TwoSum.containsKey(nums[i])){
                index[0] = i;
                index[1] = TwoSum.get(nums[i]) ;
                break;
            }
            TwoSum.put(target-nums[i], i);
        }
        return index;

    }
}