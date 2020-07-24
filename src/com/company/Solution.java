package com.company;

import sun.reflect.generics.tree.Tree;
import sun.security.util.Length;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author : xuhuizhe
 * @date : 2019/9/9 22:58
 * @description:
 * @modified :
 */
public class Solution {
    private static class SingletonHolder {
        private static final Solution INSTANCE = new Solution();
    }

    private Solution() {
    }

    public static Solution getInstance() {
        return SingletonHolder.INSTANCE;
    }


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

//    List<List<Integer>> res = new ArrayList<>();
//
//    public List<List<Integer>> permuteUnique(int[] nums) {
//        ArrayList<Integer> num = new ArrayList<>();
//        for (int n : nums) {
//            num.add(n);
//        }
//        Collections.sort(num);
//        perm(num, 0, num.size() - 1);
//        return res;
//    }
//
//    public void perm(ArrayList<Integer> num, int begin, int end) {
//        if (begin == end) {
//            ArrayList<Integer> temp = new ArrayList<>(num);
//            res.add(temp);
//        } else {
//            int before = num.get(begin);
//            for (int i = begin; i <= end; i++) {
//                if (i != begin && num.get(i).equals(before)) {
//                    continue;
//                }
//                before = num.get(i);
//                Collections.swap(num, begin, i);
//                perm(num, begin + 1, end);
//                Collections.swap(num, begin, i);
//            }
//        }
//    }

    private List<List<Integer>> res = new ArrayList<>();
    private boolean[] used;

    private void findPermuteUnique(int[] nums, int depth, Stack<Integer> stack) {
        if (depth == nums.length) {
            res.add(new ArrayList<>(stack));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                // 修改 2：因为排序以后重复的数一定不会出现在开始，故 i > 0
                // 和之前的数相等，并且之前的数还未使用过，只有出现这种情况，才会出现相同分支
                // 这种情况跳过即可
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                    continue;
                }
                used[i] = true;
                stack.add(nums[i]);
                findPermuteUnique(nums, depth + 1, stack);
                stack.pop();
                used[i] = false;
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return res;
        }
        // 修改 1：首先排序，之后才有可能发现重复分支
        Arrays.sort(nums);

        // 如果是降序，需要把 nums 变为包装数组类型，输入 Arrays.sort() 方法才生效，并且还要传入一个比较器，搜索之前，再转为基本类型数组，因此不建议降序排序
        // Integer[] numsBoxed = IntStream.of(nums).boxed().collect(Collectors.toList()).toArray(new Integer[0]);
        // Arrays.sort(numsBoxed, Collections.reverseOrder());
        // nums = Arrays.stream(numsBoxed).mapToInt(Integer::valueOf).toArray();

        used = new boolean[len];
        findPermuteUnique(nums, 0, new Stack<>());
        return res;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = getLengthOfListNode(headA);
        int lenB = getLengthOfListNode(headB);
        if (lenA == 0 || lenB == 0) {
            return null;
        }
        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; i++) {
                headA = headA.next;
            }
        } else {
            for (int i = 0; i < lenB - lenA; i++) {
                headB = headB.next;
            }
        }
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }

    public int getLengthOfListNode(ListNode head) {
        if (head == null) {
            return 0;
        }
        int length = 1;
        ListNode p = head;
        while (p.next != null) {
            p = p.next;
            length++;
        }
        return length;
    }

    public int trailingZeroes(int n) {
        //就是算5的个数
        int ans = 0;
        while (n >= 5) {
            ans += n / 5;
            n /= 5;
        }
        return ans;
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int maxCount = 0;
        int count = 0;
        for (int num : nums) {
            if (num == 1) {
                count++;
            } else {
                maxCount = Math.max(maxCount, count);
                count = 0;
            }
        }
        maxCount = Math.max(maxCount, count);
        return maxCount;
    }

    public int minFlipsMonoIncr(String S) {
        if (S.length() <= 1) {
            return 0;
        }
        int[] dp = new int[S.length() + 1];
        for (int i = 0; i < S.length(); i++) {
            //记录前面需要反转的1的个数
            dp[i + 1] = dp[i] + (S.charAt(i) == '1' ? 1 : 0);
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= S.length(); i++) {
            //dp[length]-dp[i]为后面一段需要反转的1的个数，但是我们需要的是后面需要反转的0的个数，所以要使用length-i来获得后面这一段的长度，再减去个数就是需要反转的0的个数了。
            ans = Math.min(ans, dp[i] + S.length() - i - (dp[S.length()] - dp[i]));
        }
        return ans;
    }

    public int smallestDistancePair(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int n = nums.length;
        Arrays.sort(nums);
        int length = 1;
        while (k > (n - length)) {
            k -= n - length;
            length++;
        }
        return nums[k + length] - nums[k];
    }


    public int myAtoi(String str) {
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }
        int flag = 0;
        int begin = 0;
        if (str.charAt(0) == '+' || str.charAt(0) == '-') {
            if (str.charAt(0) == '+') {
                flag = 1;
            } else {
                flag = -1;
            }
            begin = 1;
        }
        long sum = 0;
        for (int i = begin; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                break;
            }
            sum = sum * 10 + (str.charAt(i) - '0');
            if (sum > Integer.MAX_VALUE) {
                if (flag == 0) {
                    return Integer.MAX_VALUE;
                } else if (flag > 0) {
                    return Integer.MAX_VALUE;
                }
                return Integer.MIN_VALUE;
            }
        }
        if (flag != 0) {
            return (int) (flag * sum);
        }
        return (int) sum;
    }


    int max = 0;

    public int getMax(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return max;
    }

    public int getLength(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftLength = 1 + getLength(root.left);
        int rightLength = 1 + getLength(root.right);
        int maxLength = leftLength + rightLength - 1;
        max = Math.max(max, maxLength);
        return Math.max(leftLength, rightLength);
    }

    boolean ans = false;

    public boolean makesquare(int[] nums) {
        int length = 0;
        for (int i : nums) {
            length += i;
        }
        if (length % 4 != 0) {
            return false;
        }
        length /= 4;
        for (int i : nums) {
            if (i > length) {
                return false;
            }
        }
        Arrays.sort(nums);
        back(nums, nums.length - 1, length, new int[4]);
        return ans;
    }

    public void back(int[] nums, int cur, int target, int[] temp) {
        if (ans) {
            return;
        }
        if (cur == -1) {
            for (int num : temp) {
                if (num != target) {
                    return;
                }
            }
            ans = true;
            return;
        }
        for (int i = 0; i < temp.length; i++) {
            int last = temp[i];
            temp[i] += nums[cur];
            if (temp[i] <= target) {
                back(nums, cur - 1, target, temp);
            }
            temp[i] = last;
        }
    }

    public int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0) {
            return 0;
        }
        int[][] dp = new int[m + 1][n + 1];
        for (String s : strs) {
            int zero = 0;
            int one = 0;
            for (int i = 0; i < s.length(); i++) {
                //统计0，1个数
                if (s.charAt(i) == '0') {
                    zero++;
                } else {
                    one++;
                }
            }
            for (int i = m; i >= zero; i--) {
                for (int j = n; j >= one; j--) {
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i - zero][j - one]);
                }
            }
        }
        return dp[m][n];
    }

//    public int findIntegers(int num) {
//        return find(0, 0, num, false);
//    }
//
//    public int find(int i, int sum, int num, boolean prev) {
//        if (sum > num) {
//            return 0;
//        }
//        if (1 << i > num) {
//            return 1;
//        }
//        if (prev) {
//            return find(i + 1, sum, num, false);
//        }
//        return find(i + 1, sum, num, false) + find(i + 1, sum + (1 << i), num, true);
//    }


    public int findIntegers(int num) {
        int[] f = new int[32];
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < f.length; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        int i = 30, sum = 0, prev_bit = 0;
        while (i >= 0) {
            if ((num & (1 << i)) != 0) {
                sum += f[i];
                if (prev_bit == 1) {
                    sum--;
                    break;
                }
                prev_bit = 1;
            } else {
                prev_bit = 0;
            }
            i--;
        }
        return sum + 1;
    }

    public char nextGreatestLetter(char[] letters, char target) {
        int[] t = new int[26];
        for (char c : letters) {
            t[c - 'a'] = 1;
        }
        for (int i = target - 'a' + 1; ; i++) {
            if (i >= 26) {
                i = 0;
            }
            if (t[i] == 1) {
                return (char) ('a' + i);
            }
        }
    }

    public List<String> readBinaryWatch(int num) {
        ArrayList<String> ans = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 60; j++) {
                if (getOnes(i) + getOnes(j) == num) {
                    ans.add(i + ":" + (j < 10 ? "0" + j : j));
                }
            }
        }
        return ans;
    }

    public int getOnes(int n) {
        int h = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                h++;
            }
            n >>= 1;
        }
        return h;
    }

    //h很简单的思路，如果有前面的数大于后面的数，说明移除这个就对了。位数越前越好。
    public String removeKdigits(String num, int k) {
        StringBuilder n = new StringBuilder(num);
        while (k > 0) {
            int i;
            int length = n.length();
            for (i = 0; i < n.length() - 1; i++) {
                if (n.charAt(i) > n.charAt(i + 1)) {
                    n.deleteCharAt(i);
                    break;
                }
            }
            if (length == n.length()) {
                n.deleteCharAt(i);
            }
            while (n.length() > 1 && n.charAt(0) == '0') {
                n.deleteCharAt(0);
            }
            k--;
        }
        return n.length() > 0 ? n.toString() : "0";
    }

    //    public boolean canCross(int[] stones) {
//        int target = stones[stones.length - 1];
//        return cross(stones, 1, target, 0);
//    }
//没去重，超时了。
    public boolean cross(int[] stone, int k, int target, int nowStep) {
        if (k <= 0) {
            return false;
        }
        if (k >= target || nowStep >= target) {
            return true;
        }
        //Arrays.binarySearch(stone,nowStep);
        if (binarySearch0(stone, 0, stone.length, nowStep) < 0) {
            return false;
        }
        return cross(stone, k, target, nowStep + k) || cross(stone, k + 1, target, nowStep + k + 1) || cross(stone, k - 1, target, nowStep + k - 1);
    }

    public int binarySearch0(int[] a, int fromIndex, int toIndex,
                             int key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];

            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                // key found
                return mid;
            }
        }
        // key not found.
        return -(low + 1);
    }

    public boolean canCross(int[] stones) {
        HashMap<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < stones.length; i++) {
            map.put(stones[i], new HashSet<>());
        }
        map.get(0).add(0);
        //对每一个石头，都对每一步进行处理，这里之所以没超时，是因为hashset去重了。。。
        for (int i = 0; i < stones.length; i++) {
            for (int k : map.get(stones[i])) {
                for (int step = k - 1; step <= k + 1; step++) {
                    if (step > 0 && map.containsKey(stones[i] + step)) {
                        map.get(stones[i] + step).add(step);
                    }
                }
            }
        }
        //判断也没有走到这里
        return map.get(stones[stones.length - 1]).size() > 0;
    }

    public int[] prevPermOpt1(int[] A) {
        int len = A.length;
        int curMax = -1;
        int index = -1;
        boolean hasResult = false;
        for (int i = len - 2; i >= 0; i--) {
            if (A[i + 1] < A[i]) {
                // 此处逆序，需要移动A[i]
                for (int j = i + 1; j < len; j++) {
                    // 寻找与 A[i] 交换的位置
                    if (A[i] > A[j]) {
                        // 必须满足 A[i] > A[j]，否则不能满足交换后的字典序小于原始字典序
                        hasResult = true;
                        if (A[j] > curMax) {
                            curMax = A[j];
                            index = j;
                        }
                    }
                }
                if (hasResult) {
                    int tmp = A[i];
                    A[i] = A[index];
                    A[index] = tmp;
                    return A;
                }
            }
        }
        return A;
    }

    public int[] sortArrayByParity(int[] A) {
        int oddIndex = 0;
        int evenIndex = 0;
        while (true) {
            while (evenIndex < A.length && A[evenIndex] % 2 == 0) {
                evenIndex++;
            }
            while (oddIndex < A.length) {
                if (A[oddIndex] % 2 == 0) {
                    if (oddIndex < evenIndex) {
                        oddIndex++;
                    } else {
                        break;
                    }
                }
                oddIndex++;
            }
            if (oddIndex >= A.length || evenIndex >= A.length) {
                break;
            }
            swap(A, oddIndex, evenIndex);
        }
        return A;
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public int superpalindromesInRange(String sL, String sR) {
        long L = Long.valueOf(sL);
        long R = Long.valueOf(sR);
        int MAGIC = 100000;
        int ans = 0;

        // count odd length;
        for (int k = 1; k < MAGIC; ++k) {
            StringBuilder sb = new StringBuilder(Integer.toString(k));
            for (int i = sb.length() - 2; i >= 0; --i) {
                sb.append(sb.charAt(i));
            }
            long v = Long.valueOf(sb.toString());
            v *= v;
            if (v > R) {
                break;
            }
            if (v >= L && isPalindrome(v)) {
                ans++;
            }
        }

        // count even length;
        for (int k = 1; k < MAGIC; ++k) {
            StringBuilder sb = new StringBuilder(Integer.toString(k));
            for (int i = sb.length() - 1; i >= 0; --i) {
                sb.append(sb.charAt(i));
            }
            long v = Long.valueOf(sb.toString());
            v *= v;
            if (v > R) {
                break;
            }
            if (v >= L && isPalindrome(v)) {
                ans++;
            }
        }

        return ans;
    }

    //判断数字回文
    public boolean isPalindrome(long x) {
        return x == reverse(x);
    }

    //调转数
    public long reverse(long x) {
        long ans = 0;
        while (x > 0) {
            ans = 10 * ans + x % 10;
            x /= 10;
        }
        return ans;
    }

//    public int[] threeEqualParts(int[] A) {
//
//    }

    //N字排列字符串
    public String convert(String s, int numRows) {

        if (numRows == 1) {
            return s;
        }

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }

        int curRow = 0;
        boolean goingDown = false;
        //很容易知道哪个字符是哪一列的。
        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) {
                goingDown = !goingDown;
            }
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) {
            ret.append(row);
        }
        return ret.toString();
    }

    //这里很简单，只是建堆
    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> count = new HashMap();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }
        //这里学到了，优先级队列传入比较器建堆
        PriorityQueue<Integer> heap =
                new PriorityQueue<>((n1, n2) -> count.get(n2) - count.get(n1));
        heap.addAll(count.keySet());
        List<Integer> top_k = new LinkedList();
        while (k-- != 0) {
            top_k.add(heap.poll());
        }
        return top_k;
    }

    public int findKthNumber(int m, int n, int k) {
        int lo = 1, hi = m * n + 1;
        int mid, count;
        while (lo < hi) {
            mid = lo + (hi - lo) / 2;
            count = 0;
            for (int i = 1; i <= m; i++) {
                count += (Math.min(mid / i, n));
            }
            if (count >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

//    public boolean isSelfCrossing(int[] x) {
//
//    }

    List<Integer> access;
//    public List<Integer> postorderTraversal(TreeNode root) {
//        access=new ArrayList<>();
//        backFind(root);
//        return access;
//    }

    public void backFind(TreeNode node) {
        if (node == null) {
            return;
        }
        backFind(node.left);
        backFind(node.right);
        access.add(node.val);
    }

    //非递归解法
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) {
            return output;
        }
        stack.add(root);
        while (!stack.isEmpty()) {
            //这里会获得
            TreeNode node = stack.pollLast();
            assert node != null;
            //把这个放在最前
            output.addFirst(node.val);
            //这里有个左右的次序，被访问时会依次把右放入，再把左放入，因为是插入顶端，所以就倒过来。
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }
        return output;
    }


    //后续遍历
    ArrayList<Integer> postOrderList;

    public List<Integer> postorder(Node root) {
        postOrderList = new ArrayList<>();
        postOrder(root);
        return postOrderList;
    }

    public void postOrder(Node node) {
        if (node == null) {
            return;
        }
        for (Node n : node.children) {
            postOrder(n);
        }
        postOrderList.add(node.val);
    }

    //中序遍历
    ArrayList<Integer> inOrderList;

    public List<Integer> inorderTraversal(TreeNode root) {
        inOrderList = new ArrayList<>();
        inOrder(root);
        return inOrderList;
    }

    public void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        inOrderList.add(node.val);
        inOrder(node.right);
    }

    //中序遍历
//    public boolean isValidBST(TreeNode root) {
//        ArrayList<Integer> inOrderList=new ArrayList<>();
//        inOrder(root,inOrderList);
//        for(int i=0;i<inOrderList.size()-1;i++){
//            if(inOrderList.get(i)>=inOrderList.get(i+1)){
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void inOrder(TreeNode node,List<Integer> inOrderList) {
//        if (node == null) {
//            return;
//        }
//        inOrder(node.left,inOrderList);
//        inOrderList.add(node.val);
//        inOrder(node.right,inOrderList);
//    }

    //    //record up and low
//    public boolean helper(TreeNode node, Integer lower, Integer upper) {
//        if (node == null) {
//            return true;
//        }
//        int val = node.val;
//        if (lower != null && val <= lower) {
//            return false;
//        }
//        if (upper != null && val >= upper) {
//            return false;
//        }
//        if (! helper(node.right, val, upper)) {
//            return false;
//        }
//        return helper(node.left, lower, val);
//    }
//
//    public boolean isValidBST(TreeNode root) {
//        return helper(root, null, null);
//    }

    //优雅的中序遍历
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        double inorder = -Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // If next element in inorder traversal
            // is smaller than the previous one
            // that's not BST.
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }


    public int clumsy(int N) {
        int[] des=new int[]{1,2,2,-1};
        if(N>4){
            return N+des[N%4];
        }
        if(N>2){
            return N+3;
        }
        return N;
    }

    public int[] sortedSquares(int[] A) {
        for(int i=0;i<A.length;i++){
            A[i]=A[i]*A[i];
        }
        Arrays.sort(A);
        return A;
    }

    public boolean isPalindrome(int x) {
        return x==reverse(x);
    }

    public int reverse(int x) {
        boolean count = x > 0;
        if (!count) {
            x = -x;
        }
        long ans = 0;
        while (x > 0) {
            ans *= 10;
            ans += x % 10;
            if (ans > Integer.MAX_VALUE) {
                return 0;
            }
            x /= 10;
        }
        if (!count) {
            ans = -ans;
        }
        return (int) ans;
    }


    public int lenLongestFibSubseq(int[] A) {
        ExecutorService e1=new ThreadPoolExecutor();
        int N = A.length;
        Set<Integer> S = new HashSet();
        for (int x: A) {
            S.add(x);
        }
        int ans = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = i+1; j < N; ++j) {
                int x = A[j], y = A[i] + A[j];
                int length = 2;
                while (S.contains(y)) {
                    // x, y -> y, x+y
                    int tmp = y;
                    y += x;
                    x = tmp;
                    ans = Math.max(ans, ++length);
                }
            }
        }

        return ans >= 3 ? ans : 0;
    }

}

class Node {
    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};

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
