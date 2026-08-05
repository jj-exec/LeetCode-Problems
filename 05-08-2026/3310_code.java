import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] inv : invocations) {
            adj[inv[0]].add(inv[1]);
        }
        
        boolean[] suspicious = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        
        q.offer(k);
        suspicious[k] = true;
        
        while (!q.isEmpty()) {
            int currentMethod = q.poll();
            
            for (int nextMethod : adj[currentMethod]) {
                if (!suspicious[nextMethod]) {
                    suspicious[nextMethod] = true;
                    q.offer(nextMethod);
                }
            }
        }
        
        boolean canRemoveSuspicious = true;
        for (int[] inv : invocations) {
            int caller = inv[0];
            int callee = inv[1];
            
            if (!suspicious[caller] && suspicious[callee]) {
                canRemoveSuspicious = false;
                break;
            }
        }
        
        List<Integer> result = new ArrayList<>();
        
        if (canRemoveSuspicious) {
            for (int i = 0; i < n; i++) {
                if (!suspicious[i]) {
                    result.add(i);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                result.add(i);
            }
        }
        
        return result;
    }
}