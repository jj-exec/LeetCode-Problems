import java.util.HashMap;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < nums.length; right++) {
            int currentElement = nums[right];
            
            frequencyMap.put(currentElement, frequencyMap.getOrDefault(currentElement, 0) + 1);
            
            while (frequencyMap.get(currentElement) > k) {
                int leftElement = nums[left];
                frequencyMap.put(leftElement, frequencyMap.get(leftElement) - 1);
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}