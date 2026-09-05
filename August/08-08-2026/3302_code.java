import java.util.Arrays;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        
        int[] back = new int[m];
        Arrays.fill(back, -1);
        
        int ptr = n - 1;
        for (int j = m - 1; j >= 0; j--) {
            while (ptr >= 0 && word1.charAt(ptr) != word2.charAt(j)) {
                ptr--;
            }
            if (ptr >= 0) {
                back[j] = ptr;
                ptr--;
            } else {
                break;
            }
        }
        
        int[] ans = new int[m];
        int i = 0;
        boolean changed = false;
        
        for (int j = 0; j < m; j++) {
            boolean found = false;
            while (i < n) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    ans[j] = i;
                    i++;
                    found = true;
                    break;
                } else if (!changed && (j == m - 1 || back[j + 1] > i)) {
                    ans[j] = i;
                    changed = true;
                    i++;
                    found = true;
                    break;
                }
                i++;
            }
            
            if (!found) {
                return new int[0];
            }
        }
        
        return ans;
    }
}