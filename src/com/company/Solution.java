package com.company;

import sun.reflect.generics.tree.Tree;
import sun.security.util.Length;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author : xuhuizhe
 * @date : 2019/9/9 22:58
 * @description:
 * @modified :
 */
public class Solution {
    public String LeftRotateString(String str, int n) {
        int length = str.length();
        if (length <= 0) {
            return str;
        }
        int k = n % length;
        return str.substring(k, length) + str.substring(0, k);
    }

    public String ReverseSentence(String str) {
        if (str.length() <= 0) {
            return str;
        }
        StringBuilder ans = new StringBuilder();
        String[] res = str.split(" ");
        if (res.length <= 0) {
            return str;
        }
        for (int i = res.length - 1; i >= 0; i--) {
            ans.append(res[i]).append(" ");
        }
        return ans.substring(0, ans.length() - 1);
    }

    public boolean isContinuous(int[] numbers) {
        if (numbers.length <= 0) {
            //没有就说明没有顺子，返回true可能有点不对
            return false;
        }
        ArrayList<Integer> nums = new ArrayList<>();
        for (int num : numbers) {
            nums.add(num);
        }
        Collections.sort(nums);
        int not = 0;
        int zeroCount = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) == 0) {
                zeroCount++;
                continue;
            }
            if (i == 0) {
                continue;
            }
            if (nums.get(i - 1) == 0) {
                continue;
            }
            if (nums.get(i).equals(nums.get(i - 1))) {
                return false;
            }
            if (nums.get(i) > nums.get(i - 1) + 1) {
                not += nums.get(i) - nums.get(i - 1) - 1;
            }
        }
        //这里一开始是==号，并不用相等，大于或等于即可，这里逻辑不够严谨。
        return zeroCount >= not;
    }

    public int LastRemaining_Solution(int n, int m) {
        if (n <= 0) {
            return -1;
        }
        //只有一个人，所以返回下标0
        if (n == 1) {
            return 0;
        }
        //上 次结果的下标加上m就是该次的下标
        return (LastRemaining_Solution(n - 1, m) + m) % n;
    }

    public int Sum_Solution(int n) {
        //短路截断递归。。。。压根没想到短路截断这种东西。。。
        boolean t = (n != 0) && ((n += Sum_Solution(n - 1)) != 0);
        return n;
    }

    /*
     *
     * @author     : HuiZhe Xu
     * @date       : Created in 2019/9/10 19:20
     * @parameter  : num1
     * @parameter  : num2
     * @return     : int
     */
    public int Add(int num1, int num2) {
        while (num2 != 0) {
            int temp = num1 ^ num2;
            num2 = (num1 & num2) << 1;
            num1 = temp;
        }
        return num1;
    }

    public int StrToInt(String str) {
        str = str.trim();
        int flag = 0;
        if (str.length() <= 0) {
            return 0;
        }
        if (str.charAt(0) == '+' || str.charAt(0) == '-') {
            if (str.charAt(0) == '-') {
                flag = -1;
            }
            str = str.substring(1);
        }
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            char now = str.charAt(i);
            if (now >= '0' && now <= '9') {
                sum *= 10;
                sum += (now - '0');
            } else {
                return 0;
            }
        }
        if (flag == -1) {
            return -sum;
        }
        return sum;
    }

    // Parameters:
    //    numbers:     an array of integers
    //    length:      the length of array numbers
    //    duplication: (Output) the duplicated number in the array number,length of duplication array is 1,so using duplication[0] = ? in implementation;
    //                  Here duplication like pointor in C/C++, duplication[0] equal *duplication in C/C++
    //    这里要特别注意~返回任意重复的一个，赋值duplication[0]
    // Return value:       true if the input is valid, and there are some duplications in the array number
    //                     otherwise false
    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (numbers == null || numbers.length <= 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            //当前数字
            int index = numbers[i];
            if (index >= length) {
                //数字大于长度，说明该数字已经被加过，减去加的长度
                index -= length;
            }
            if (numbers[index] >= length) {
                //这个数字被加过，这里再次遇到，说明该index重复
                duplication[0] = index;
                return true;
            }
            //这里标记该数字已经遍历了。
            numbers[index] = numbers[index] + length;
        }
        return false;
//        Set<Integer> res=new HashSet<>(length);
//        for(int num:numbers){
//            if(num<0||num>length-1){
//                return false;
//            }
//            if(!res.contains(num)){
//                res.add(num);
//            }else{
//                duplication[0]=num;
//                return true;
//            }
//        }
//        return false;
    }

    public int[] multiply(int[] A) {
        if (A == null || A.length == 0) {
            return null;
        }
        int[] B = new int[A.length];
        //该位为零
        B[0] = 1;
        for (int i = 1; i < A.length; i++) {
            //计算上三角
            B[i] = B[i - 1] * A[i - 1];
        }
        int temp = 1;
        for (int i = A.length - 2; i >= 0; i--) {
            //下三角
            temp *= A[i + 1];
            B[i] *= temp;
        }
        return B;
    }

    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        int strIndex = 0;
        int patternIndex = 0;
        return matchCore(str, strIndex, pattern, patternIndex);
    }

    public boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex) {
        //有效性检验：str到尾，pattern到尾，匹配成功
        if (strIndex == str.length && patternIndex == pattern.length) {
            return true;
        }
        //pattern先到尾，匹配失败
        if (strIndex != str.length && patternIndex == pattern.length) {
            return false;
        }
        //模式第2个是*，且字符串第1个跟模式第1个匹配,分3种匹配模式；如不匹配，模式后移2位
        if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
            if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
                return matchCore(str, strIndex, pattern, patternIndex + 2)//模式后移2，视为x*匹配0个字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex + 2)//视为模式匹配1个字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex);//*匹配1个，再匹配str中的下一个
            } else {
                return matchCore(str, strIndex, pattern, patternIndex + 2);
            }
        }
        //模式第2个不是*，且字符串第1个跟模式第1个匹配，则都后移1位，否则直接返回false
        if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
            return matchCore(str, strIndex + 1, pattern, patternIndex + 1);
        }
        return false;
    }

    public boolean isNumeric(char[] str) {
        if (str == null || str.length == 0) {
            return false;
        }
        int begin = 0;
        if (str[0] == '+' || str[0] == '-') {
            begin = 1;
        }
        for (int i = begin; i < str.length; i++) {
            if (str[i] == 'e' || str[i] == 'E') {
                if (i != begin) {
                    return afterE(str, i + 1);
                }
            } else {
                if (!(str[i] >= '0' && str[i] <= '9')) {
                    if (str[i] == '.') {
                        return afterDot(str, i + 1);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean afterE(char[] str, int begin) {
        if (str.length <= begin) {
            return false;
        }
        int beginIndex = begin;
        if (str[begin] == '+' || str[begin] == '-') {
            beginIndex += 1;
        }
        if (str.length <= beginIndex) {
            return false;
        }
        for (int i = beginIndex; i < str.length; i++) {
            if (str[i] < '0' || str[i] > '9') {
                return false;
            }
        }
        return true;
    }

    public boolean afterDot(char[] str, int begin) {
        if (str.length <= begin) {
            return false;
        }
        for (int i = begin; i < str.length; i++) {
            if (str[i] == 'e' || str[i] == 'E') {
                if (i != begin) {
                    return afterE(str, i + 1);
                }
            }
            if (str[i] < '0' || str[i] > '9') {
                return false;
            }
        }
        return true;
    }


    //Insert one char from stringstream
    HashMap<Character, Integer> count = new HashMap<>();
    Queue<Character> charQueue = new ArrayDeque<>();

    public void Insert(char ch) {
        if (!count.containsKey(ch)) {
            count.put(ch, 1);
            charQueue.add(ch);
        } else {
            count.replace(ch, count.get(ch) + 1);
        }
    }

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        while (!charQueue.isEmpty() && count.get(charQueue.peek()) >= 2) {
            charQueue.poll();
        }
        if (charQueue.isEmpty()) {
            return '#';
        }
        return (char) charQueue.peek();
    }

    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return null;
        }
        ListNode weekP = pHead;
        ListNode fastP = pHead.next;
        while (weekP != null && fastP != null && weekP != fastP) {
            weekP = weekP.next;
            fastP = fastP.next.next;
        }
        if (fastP == null || weekP == null) {
            return null;
        }
        ListNode startNode = pHead;
        while (startNode != fastP.next) {
            startNode = startNode.next;
            fastP = fastP.next;
        }
        return startNode;
    }

    public ListNode deleteDuplication(ListNode pHead) {
        ListNode p = pHead;
        ListNode before = pHead;
        while (p != null && p.next != null) {
            if (p.next.val == p.val) {
                while (p.next != null && p.next.val == p.val) {//p.next需要存在
                    ListNode temp = p.next;
                    p.next = temp.next;
                    temp = null;
                }
                if (pHead != p) {
                    ListNode temp = p;
                    before.next = temp.next;
                    temp = null;
                    p = before.next;
                } else {
                    ListNode temp = p;
                    pHead = p.next;
                    before = pHead;
                    p = pHead;
                    temp = null;
                }
            } else {
                before = p;
                p = p.next;
            }
        }
        return pHead;
    }

//    public TreeLinkNode GetNext(TreeLinkNode pNode) {
//        if (pNode.next == null) {
//            return getMostLeft(pNode.right);
//        }
//        if (pNode == pNode.next.left) {
//            if (pNode.right != null) {
//                return getMostLeft(pNode.right);
//            }
//            return pNode.next;
//        } else {
//            TreeLinkNode next = getMostLeft(pNode.right);
//            if (next == null) {
//                if (pNode.next.next != null && pNode.next == pNode.next.next.left) {
//                    //这里能过可能是数据不好。。。应该是过不了的。
//                    return pNode.next.next;
//                }
//                return null;
//            } else {
//                return next;
//            }
//        }
//    }

    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }
        if (pNode.right != null) {
            //如果有右子树，则找右子树的最左节点
            pNode = pNode.right;
            while (pNode.left != null) {
                pNode = pNode.left;
            }
            return pNode;
        }
        while (pNode.next != null) {
            //没右子树，则找第一个当前节点是父节点左孩子的节点
            if (pNode.next.left == pNode) {
                return pNode.next;
            }
            pNode = pNode.next;
        }
        return null;
    }

    public TreeLinkNode getMostLeft(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }
        if (pNode.left == null) {
            return pNode;
        }
        return getMostLeft(pNode.left);
    }

    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) {
            return true;
        }
        return isSymmetrical(pRoot.left, pRoot.right);
    }

    private boolean isSymmetrical(TreeNode pLeft, TreeNode pRight) {
        if (pLeft == null && pRight == null) {
            return true;
        } else if (pLeft == null || pRight == null) {
            return false;
        } else if (pLeft.val == pRight.val) {
            return isSymmetrical(pLeft.left, pRight.right) && isSymmetrical(pLeft.right, pRight.left);
        }
        return false;
    }

//    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
//        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
//        if (pRoot == null) {
//            return res;
//        }
//        ArrayList<Integer> list = new ArrayList<>();
//        LinkedList<TreeNode> queue = new LinkedList<>();
//        LinkedList<TreeNode> next;
//        queue.addLast(pRoot);
//        boolean isLeft = true;
//        while (!queue.isEmpty()) {
//            Iterator<TreeNode> ite;
//            if (isLeft) {
//                ite = queue.iterator();
//            } else {
//                ite = queue.descendingIterator();
//            }
//            while (ite.hasNext()) {
//                list.add(ite.next().val);
//            }
//            res.add(new ArrayList<>(list));
//            list.clear();
//            isLeft = !isLeft;
//
//            ite = queue.iterator();
//            next = new LinkedList<>();
//            while (ite.hasNext()) {
//                TreeNode temp=ite.next();
//                if (temp.left != null) {
//                    next.add(temp.left);
//                }
//                if (temp.right != null) {
//                    next.add(temp.right);
//                }
//            }
//            queue = next;
//        }
//        return res;
//    }

    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (pRoot == null) {
            return res;
        }
        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        LinkedList<TreeNode> next;
        queue.addLast(pRoot);
        while (!queue.isEmpty()) {
            Iterator<TreeNode> ite = queue.iterator();
            while (ite.hasNext()) {
                list.add(ite.next().val);
            }
            res.add(new ArrayList<>(list));
            list.clear();
            ite = queue.iterator();
            next = new LinkedList<>();
            while (ite.hasNext()) {
                TreeNode temp = ite.next();
                if (temp.left != null) {
                    next.add(temp.left);
                }
                if (temp.right != null) {
                    next.add(temp.right);
                }
            }
            queue = next;
        }
        return res;
    }

    int index = 0;

    String Serialize(TreeNode root) {
        if (root == null) {
            return "#";
        }
        return root.val + "!" + Serialize(root.left) + Serialize(root.right);
    }

    TreeNode Deserialize(String str) {
        TreeNode root = null;
        if (index >= str.length()) {
            return null;
        }
        if (str.charAt(index) == '#') {
            index++;
            return null;
        }
        for (int i = index; i < str.length(); i++) {
            if (str.charAt(i) == '!') {
                root = new TreeNode(Integer.parseInt(str.substring(index, i)));
                index = i + 1;
                root.left = Deserialize(str);
                root.right = Deserialize(str);
                break;
            }
        }
        return root;
    }

    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null) {
            return null;
        }
        if (leftTree(pRoot.left) > k - 1) {
            return KthNode(pRoot.left, k);
        } else if (leftTree(pRoot.left) == k - 1) {
            return pRoot;
        } else {
            return KthNode(pRoot.right, k - leftTree(pRoot.left) - 1);
        }
    }

    int leftTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + leftTree(root.left) + leftTree(root.right);
    }


    LinkedList<Integer> nums = new LinkedList<>();

    public void Insert(Integer num) {
        if (!nums.isEmpty()) {
            int i;
            for (i = 0; i < nums.size(); i++) {
                if (nums.get(i) > num) {
                    nums.add(i, num);
                    return;
                }
            }
        }
        nums.add(num);
    }

    public Double GetMedian() {
        if (nums.size() == 0) {
            return 0.0;
        }
        if (1 == (nums.size() & 1)) {
            return (double) nums.get(((nums.size() + 1) >> 1) - 1);
        } else {
            return (double) (nums.get(nums.size() / 2 - 1) + nums.get(nums.size() / 2 + 1 - 1)) / 2;
        }
    }

    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> res = new ArrayList<>();
        if (num.length == 0 || size == 0 || size > num.length) {
            return res;
        }
        int start = 0;
        while (start + size <= num.length) {
            int max = num[start];
            for (int i = start + 1; i < start + size; i++) {
                if (num[i] > max) {
                    max = num[i];
                }
            }
            res.add(max);
            start++;
        }
        return res;
    }

    int[] directX = {0, 0, -1, 1};
    int[] directY = {-1, 1, 0, 0};

//    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
//        char[][] MATRIX = new char[rows][cols];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                MATRIX[i][j] = matrix[cols * i + j];
//            }
//        }
//        boolean[][] detect = new boolean[rows][cols];
//        for (boolean[] d : detect) {
//            Arrays.fill(d, false);
//        }
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                if (str[0] == MATRIX[i][j]) {
//                    if (walk(MATRIX, i, j, 0, str, detect)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean walk(char[][] matrix, int i, int j, int index, char[] str, boolean[][] detect) {
//        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length) {
//            return false;
//        }
//        if (detect[i][j]) {
//            return false;
//        }
//        if (str[index] != matrix[i][j]) {
//            return false;
//        }
//        detect[i][j] = true;
//        index++;
//        if (index == str.length) {
//            return true;
//        } else {
//            for (int step = 0; step < directX.length; step++) {
//                if (walk(matrix, i + directX[step], j + directY[step], index, str, detect)) {
//                    return true;
//                }
//            }
//            detect[i][j] = false;
//            return false;
//        }
//    }

    public int movingCount(int threshold, int rows, int cols) {
        if (rows == 0 || cols == 0) {
            return 0;
        }
        int sum = 0;
        boolean[][] detect = new boolean[rows][cols];
        for (boolean[] d : detect) {
            Arrays.fill(d, false);
        }
        sum += walk(threshold, 0, 0, detect);
        return sum;
    }

    public int getCount(int row, int col) {
        int sumX = 0;
        int sumY = 0;
        while (row != 0) {
            sumX += row % 10;
            row /= 10;
        }
        while (col != 0) {
            sumY += col % 10;
            col /= 10;
        }
        return sumX + sumY;
    }

    public int walk(int threshold, int row, int col, boolean[][] detect) {
        if (row < 0 || row >= detect.length || col < 0 || col >= detect[0].length) {
            return 0;
        }
        if (detect[row][col]) {
            return 0;
        }
        if (getCount(row, col) > threshold) {
            return 0;
        }
        int sum = 1;
        detect[row][col] = true;
        for (int i = 0; i < directX.length; i++) {
            sum += walk(threshold, row + directX[i], col + directY[i], detect);
        }
        return sum;
    }

    public int cutRope(int target) {
        if (target == 2) {
            return 1;
        }
        if (target == 3) {
            return 2;
        }
        int x = target % 3;
        int y = target / 3;
        if (x == 0) {
            return (int) Math.pow(3, y);
        }
        if (x == 1) {
            return (int) Math.pow(3, y - 1) * 4;
        }
        return (int) Math.pow(3, y) * 2;
    }

//    public int minimumLengthEncoding(String[] words) {
//        if (words == null || words.length == 0) {
//            return 0;
//        }
//        ArrayList<String> word = new ArrayList<>(Arrays.asList(words));
//        word.sort((o1, o2) -> {
//            if (o2.length() > o1.length()) {
//                return 1;
//            } else if (o2.length() == o1.length()) {
//                return 0;
//            }
//            return -1;
//        });
//        int length = word.get(0).length() + 1;
//        for (int i = 1; i < word.size(); i++) {
//            int j;
//            for (j = 0; j < i; j++) {
//                if (compare(word.get(j), word.get(i))) {
//                    break;
//                }
//            }
//            if (j == i) {
//                length += word.get(i).length() + 1;
//            }
//        }
//        return length;
//    }
//
//    public boolean compare(String s1, String s2) {
//        if (s2.length() > s1.length()) {
//            return false;
//        }
//        for (int i = s2.length() - 1; i >= 0; i--) {
//            if (s1.charAt(i - s2.length() + s1.length()) != s2.charAt(i)) {
//                return false;
//            }
//        }
//        return true;
//    }


    public int minimumLengthEncoding(String[] words) {
        TrieNode trie = new TrieNode();
        HashMap<TrieNode, Integer> nodes = new HashMap<>(16);

        for (int i = 0; i < words.length; ++i) {
            String word = words[i];
            TrieNode cur = trie;
            for (int j = word.length() - 1; j >= 0; --j) {
                cur = cur.get(word.charAt(j));
            }
            nodes.put(cur, i);
        }

        int ans = 0;
        for (TrieNode node : nodes.keySet()) {
            if (node.count == 0) {
                //为0说明开头节点没有更长的单词覆盖，就是说这条路径没人走过。所以要加上。
                //如果不为0的话，说明这条路径还有别的单词经过，因为如果前面不同，这里是不会经过的。
                ans += words[nodes.get(node)].length() + 1;
            }
        }
        return ans;
    }

    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> permuteUnique(int[] nums) {
        ArrayList<Integer> num = new ArrayList<>();
        for (int n : nums) {
            num.add(n);
        }
        Collections.sort(num);
        perm(num, 0, num.size() - 1);
        return res;
    }

    public void perm(ArrayList<Integer> num, int begin, int end) {
        if (begin == end) {
            ArrayList<Integer> temp = new ArrayList<>(num);
            res.add(temp);
        } else {
            int before = num.get(begin);
            for (int i = begin; i <= end; i++) {
                if (i != begin && num.get(i).equals(before)) {
                    continue;
                }
                before = num.get(i);
                Collections.swap(num, begin, i);
                perm(num, begin + 1, end);
                Collections.swap(num, begin, i);
            }
        }
    }

}

class TrieNode {
    private TrieNode[] children;
    //count表示这个节点有多少个字母，就是说有可能别的单词也会到这
    int count;

    TrieNode() {
        children = new TrieNode[26];
        count = 0;
    }

    TrieNode get(char c) {
        if (children[c - 'a'] == null) {

            children[c - 'a'] = new TrieNode();
            count++;
        }
        //如果是新建的字符，返回去的是一个count为0的node，如果node为0的话
        return children[c - 'a'];
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
