class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        int currentPrefixSum = 0;
        
        for (int stone : stones) {
            currentPrefixSum += stone;
        }
        
        int maxDiff = currentPrefixSum;
        
        for (int i = n - 2; i >= 1; i--) {
            currentPrefixSum -= stones[i + 1];
            
            maxDiff = Math.max(maxDiff, currentPrefixSum - maxDiff);
        }
        
        return maxDiff;
    }
}