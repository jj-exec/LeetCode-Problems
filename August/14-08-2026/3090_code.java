class Solution {
    public int maximumLengthSubstring(String s) {
        int[] freq = new int[26];
        
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            int rightCharIdx = s.charAt(right) - 'a';
            freq[rightCharIdx]++;
            
            while (freq[rightCharIdx] > 2) {
                int leftCharIdx = s.charAt(left) - 'a';
                freq[leftCharIdx]--;
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}