import java.util.Arrays;

class Solution {
    int[][] dp;

    public String smallestNumber(String num, long t) {
        long temp = t;
        int[] req = new int[4];
        int[] primes = {2, 3, 5, 7};
        for (int i = 0; i < 4; i++) {
            while (temp % primes[i] == 0) {
                req[i]++;
                temp /= primes[i];
            }
        }
        
        if (temp > 1) return "-1";

        dp = new int[55][35];
        for (int[] row : dp) Arrays.fill(row, -1);

        int n = num.length();
        int[][] prefRem = new int[n + 1][4];
        prefRem[0] = req.clone();
        
        boolean hasZero = false;
        int firstZero = n;

        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            if (d == 0) {
                hasZero = true;
                firstZero = Math.min(firstZero, i);
                break;
            }
            prefRem[i + 1] = prefRem[i].clone();
            subtractFactors(prefRem[i + 1], d);
        }

        if (!hasZero && prefRem[n][0] == 0 && prefRem[n][1] == 0 
            && prefRem[n][2] == 0 && prefRem[n][3] == 0) {
            return num;
        }

        for (int i = Math.min(n - 1, firstZero); i >= 0; i--) {
            int currentDigit = num.charAt(i) - '0';
            
            for (int d = currentDigit + 1; d <= 9; d++) {
                int[] rem = prefRem[i].clone();
                subtractFactors(rem, d);
                
                int requiredLen = rem[2] + rem[3] + getMinDigits(rem[0], rem[1]);
                int allowedLen = n - 1 - i;
                
                if (requiredLen <= allowedLen) {
                    String suffix = buildSuffix(rem, allowedLen);
                    return num.substring(0, i) + d + suffix;
                }
            }
        }

        for (int len = n + 1; ; len++) {
            int requiredLen = req[2] + req[3] + getMinDigits(req[0], req[1]);
            if (requiredLen <= len) {
                return buildSuffix(req, len);
            }
        }
    }

    private void subtractFactors(int[] rem, int digit) {
        if (digit == 2) rem[0] -= 1;
        else if (digit == 3) rem[1] -= 1;
        else if (digit == 4) rem[0] -= 2;
        else if (digit == 5) rem[2] -= 1;
        else if (digit == 6) { rem[0] -= 1; rem[1] -= 1; }
        else if (digit == 7) rem[3] -= 1;
        else if (digit == 8) rem[0] -= 3;
        else if (digit == 9) rem[1] -= 2;
        
        for (int i = 0; i < 4; i++) {
            if (rem[i] < 0) rem[i] = 0;
        }
    }

    private int getMinDigits(int r2, int r3) {
        if (r2 <= 0 && r3 <= 0) return 0;
        
        // Clamp bounds for array lookup
        r2 = Math.max(0, r2);
        r3 = Math.max(0, r3);
        
        if (dp[r2][r3] != -1) return dp[r2][r3];

        int res = 1000000;
        
        if (r2 > 0) res = Math.min(res, 1 + getMinDigits(r2 - 1, r3));   
        if (r3 > 0) res = Math.min(res, 1 + getMinDigits(r2, r3 - 1));  
        if (r2 > 0) res = Math.min(res, 1 + getMinDigits(r2 - 2, r3));   
        if (r2 > 0 || r3 > 0) res = Math.min(res, 1 + getMinDigits(r2 - 1, r3 - 1)); 
        if (r2 > 0) res = Math.min(res, 1 + getMinDigits(r2 - 3, r3));   
        if (r3 > 0) res = Math.min(res, 1 + getMinDigits(r2, r3 - 2));    

        dp[r2][r3] = res;
        return res;
    }

    private String buildSuffix(int[] req, int allowedLength) {
        StringBuilder sb = new StringBuilder();
        int[] currReq = req.clone();
        
        for (int idx = 0; idx < allowedLength; idx++) {
            for (int d = 1; d <= 9; d++) {
                int[] nextReq = currReq.clone();
                subtractFactors(nextReq, d);
                int needed = nextReq[2] + nextReq[3] + getMinDigits(nextReq[0], nextReq[1]);
                
                if (needed <= allowedLength - 1 - idx) {
                    sb.append(d);
                    currReq = nextReq;
                    break; 
                }
            }
        }
        return sb.toString();
    }
}