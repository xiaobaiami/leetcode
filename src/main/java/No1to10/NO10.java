package No1to10;


/**
 * 10. 正则表达式匹配
 * 动态规划，先递归解
 */
public class NO10 {


    public static void main(String[] args) {
        NO10 no10 = new NO10();
        System.out.println(no10.isMatch3("aa", "a"));
        System.out.println(no10.isMatch3("aa", ".a"));
        System.out.println(no10.isMatch3("aa", "a*"));
        System.out.println(no10.isMatch3("aa", "*a"));
        System.out.println(no10.isMatch3("aa", ".*"));
        System.out.println(no10.isMatch3("caab", "c*a*b*"));
        System.out.println(no10.isMatch3("aab", "c*a*b*"));
        System.out.println(no10.isMatch3("bbab", "b*a*"));
        System.out.println(no10.isMatch3("a", "a"));
        System.out.println(no10.isMatch3("a", "ab*"));
        System.out.println(no10.isMatch3("bbbba", ".*a*a"));

        System.out.println("------------------------------------------");

        System.out.println(no10.isMatch3("aa", "a") == no10.isMatch1("aa", "a"));
        System.out.println(no10.isMatch3("aa", ".a") == no10.isMatch1("aa", ".a"));
        System.out.println(no10.isMatch3("aa", "a*") == no10.isMatch1("aa", "a*"));
        System.out.println(no10.isMatch3("aa", "*a") == no10.isMatch1("aa", "*a"));
        System.out.println(no10.isMatch3("aa", ".*") == no10.isMatch1("aa", ".*"));
        System.out.println(no10.isMatch3("caab", "c*a*b*") == no10.isMatch1("caab", "c*a*b*"));
        System.out.println(no10.isMatch3("aab", "c*a*b*") == no10.isMatch1("aab", "c*a*b*"));
        System.out.println(no10.isMatch3("bbab", "b*a*") == no10.isMatch1("bbab", "b*a*"));
        System.out.println(no10.isMatch3("a", "a") == no10.isMatch1("a", "a"));
        System.out.println(no10.isMatch3("a", "ab*") == no10.isMatch1("a", "ab*"));
        System.out.println(no10.isMatch3("bbbba", ".*a*a") == no10.isMatch1("bbbba", ".*a*a"));


    }

    /**
     * 从头部开始判断，满足就把头部的元素给删除了
     * 如果p的后面是*，
     * 那么如果当前的s和p不相等，那么跳过p的*，
     * <p>
     * 减而治之
     */
    public boolean isMatch1(String s, String p) {
        return isMatch1_help(s, p, 0, 0);
    }

    private boolean isMatch1_help(String s, String p, int cur1, int cur2) {
        if (s.length() == cur1) {
            if (cur2 == p.length())
                return true;
            boolean res = true;
            while (res && cur2 < p.length()) {
                res = p.charAt(cur2) != '*' && cur2 + 1 < p.length() && p.charAt(cur2 + 1) == '*';
                cur2 += 2;
            }
            return res;
        } else {
            if (cur2 == p.length())
                return false;
        }

        char sourceChar = s.charAt(cur1);
        char patternChar = p.charAt(cur2);
        char nextPatternChar = 0;
        if (cur2 + 1 < p.length())
            nextPatternChar = p.charAt(cur2 + 1);
        if (nextPatternChar == '*') {
            if (sourceChar != patternChar && patternChar != '.') {
                return isMatch1_help(s, p, cur1, cur2 + 2);
            } else {
                boolean res1 = isMatch1_help(s, p, cur1 + 1, cur2);
                boolean res2 = isMatch1_help(s, p, cur1 + 1, cur2 + 2);
                boolean res3 = isMatch1_help(s, p, cur1, cur2 + 2);
                return res1 | res2 | res3;
            }
        }

        if (sourceChar == patternChar || patternChar == '.')
            return isMatch1_help(s, p, cur1 + 1, cur2 + 1);
        return false;
    }

    /**
     * 从头部开始判断改动态规划麻烦
     * 仿照改为从尾部开始判断
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch2(String s, String p) {
        return isMatch1_help2(s, p, s.length() - 1, p.length() - 1);
    }

    private boolean isMatch1_help2(String s, String p, int cur1, int cur2) {
        if (cur1 == -1) {
            if (cur2 == -1)
                return true;
            boolean res = true;
            while (res && cur2 >= 0) {
                res = p.charAt(cur2) == '*' && cur2 - 1 >= 0 && p.charAt(cur2 - 1) != '*';
                cur2 -= 2;
            }
            return res;
        } else {
            if (cur2 < 0)
                return false;
        }

        char sourceChar = s.charAt(cur1);
        char patternChar = p.charAt(cur2);
        char prevPatternChar = 0;
        if (cur2 - 1 >= 0)
            prevPatternChar = p.charAt(cur2 - 1);
        if (patternChar == '*') {
            boolean res1 = isMatch1_help2(s, p, cur1, cur2 - 2);
            boolean res2 = sourceChar == prevPatternChar || prevPatternChar == '.';
            return res1
                    | (res2 && isMatch1_help2(s, p, cur1 - 1, cur2 - 2))
                    | (res2 && isMatch1_help2(s, p, cur1 - 1, cur2));
        }
        return (sourceChar == patternChar || patternChar == '.')
                && isMatch1_help2(s, p, cur1 - 1, cur2 - 1);
    }

    /**
     * 递归改为动态规划
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch3(String s, String p) {
        boolean[][] arr = new boolean[s.length() + 1][p.length() + 1];
        arr[0][0] = true;
        int start = 0;
        while (start + 1 < p.length() && p.charAt(start) != '*' && p.charAt(start + 1) == '*') {
            arr[0][start + 2] = true;
            start += 2;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[0].length; j++) {
                if (p.charAt(j - 1) == '*') {
                    // 如果p当前字符是*，那么找到前面一个字符和s进行判断
                    // *代表了3种可能，
                    // 1是代表0，那么直接忽略掉p的2个字符
                    // 2是在比较成功的情况下代表1，那么p忽略掉2个字符，s忽略掉一个字符
                    // 3是在比较成功的情况下代表多个，那么p不忽略字符，s忽略掉一个字符

                    boolean b = j - 2 >= 0 && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.');
                    arr[i][j] = (j - 2 >= 0 && arr[i][j - 2]) | (b && arr[i - 1][j - 2]) | (b && arr[i - 1][j]);
                } else {
                    // p当前的字符不是*，那么s和p应该各拿出一个字符来进行比较
                    arr[i][j] = arr[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
                }
            }
        }
        return arr[s.length()][p.length()];
    }
}
