lass Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        
        int dp1 = 0; 
        int dp2 = 0; 
        int dp3 = 0; 
        
        for (int i = n - 1; i >= 0; i--) {
            int maxDiff = Integer.MIN_VALUE;
            int sum = 0;
            
            if (i < n) {
                sum += stoneValue[i];
                maxDiff = Math.max(maxDiff, sum - dp1);
            }
            
            if (i + 1 < n) {
                sum += stoneValue[i + 1];
                maxDiff = Math.max(maxDiff, sum - dp2);
            }
            
            if (i + 2 < n) {
                sum += stoneValue[i + 2];
                maxDiff = Math.max(maxDiff, sum - dp3);
            }
            
            dp3 = dp2;
            dp2 = dp1;
            dp1 = maxDiff; 
        }
        
        if (dp1 > 0) {
            return "Alice";
        } else if (dp1 < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }
}