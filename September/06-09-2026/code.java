class Solution {
    private static int[] rightMin = new int[100005]; 

    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return -1;
        
        if (rightMin.length < n) {
            rightMin = new int[n];
        }
        
        int currentMin = nums[n - 1];
        rightMin[n - 1] = currentMin;
        
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < currentMin) {
                currentMin = nums[i];
            }
            rightMin[i] = currentMin;
        }
        
        int leftMax = nums[0];
        
        for (int i = 0; i < n; i++) {
            if (nums[i] > leftMax) {
                leftMax = nums[i];
            }
            
            if ((long) leftMax - rightMin[i] <= k) {
                return i;
            }
        }
        
        return -1;
    }
}