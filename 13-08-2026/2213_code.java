class Solution {
    
    class Node {
        int maxLen;
        int prefixLen;
        int suffixLen;
        char prefixChar;
        char suffixChar;
        int len;
    }
    
    Node[] tree;
    String s;
    
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        tree = new Node[4 * n];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Node();
        }
        
        this.s = s;
        build(1, 0, n - 1);
        
        int k = queryCharacters.length();
        int[] ans = new int[k];
        
        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = tree[1].maxLen;
        }
        
        return ans;
    }
    
    private void merge(Node parent, Node left, Node right) {
        parent.len = left.len + right.len;
        parent.prefixChar = left.prefixChar;
        parent.suffixChar = right.suffixChar;
        
        parent.prefixLen = left.prefixLen;
        if (left.prefixLen == left.len && left.prefixChar == right.prefixChar) {
            parent.prefixLen += right.prefixLen;
        }
        
        parent.suffixLen = right.suffixLen;
        if (right.suffixLen == right.len && right.suffixChar == left.suffixChar) {
            parent.suffixLen += left.suffixLen;
        }
        
        parent.maxLen = Math.max(left.maxLen, right.maxLen);
        if (left.suffixChar == right.prefixChar) {
            parent.maxLen = Math.max(parent.maxLen, left.suffixLen + right.prefixLen);
        }
    }
    
    private void build(int node, int start, int end) {
        if (start == end) {
            char c = s.charAt(start);
            tree[node].maxLen = 1;
            tree[node].prefixLen = 1;
            tree[node].suffixLen = 1;
            tree[node].prefixChar = c;
            tree[node].suffixChar = c;
            tree[node].len = 1;
            return;
        }
        
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;
        
        build(leftChild, start, mid);
        build(rightChild, mid + 1, end);
        
        merge(tree[node], tree[leftChild], tree[rightChild]);
    }
    
    private void update(int node, int start, int end, int idx, char c) {
        if (start == end) { 
            tree[node].prefixChar = c;
            tree[node].suffixChar = c;
            return;
        }
        
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;
        
        if (idx <= mid) {
            update(leftChild, start, mid, idx, c);
        } else {
            update(rightChild, mid + 1, end, idx, c);
        }
        
        merge(tree[node], tree[leftChild], tree[rightChild]);
    }
}