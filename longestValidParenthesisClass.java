import java.util.Stack;

public class longestValidParenthesisClass {
    public static void main(String[] args) {

        String[] testCases = {
            "",
            "(",
            ")",
            "()",
            ")(",
            "(()",
            "())",
            "()()",
            "(())",
            "(()())",
            "())(())",
            "(((((((((())))))))))",
            "(((((((()",
            ")))))",
            "(()((())()))",
            "())())())())",
            "()(()",
            "()(())",
            "(()(((())))())",
            "()()()()()",
        };

        for (String test : testCases) {
	    int ans = longestValidParentheses(test);
            System.out.printf("Input: %-20s | Longest Valid Length: %d%n",
                              "\"" + test + "\"",
                              ans);
        }
    }


    public static int longestValidParentheses(String s) {
        Stack<Integer> st = new Stack<>();
        st.push(-1);

        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '(') {
                st.push(i);
            }

	    else {
                st.pop();

                if (st.isEmpty()) {

                    st.push(i);
                }

		else {
                    maxLength = Math.max(maxLength, i - st.peek());
                }
            }
        }

        return maxLength;
    }

}
