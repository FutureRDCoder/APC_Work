import java.util.*;
import java.lang.Math;

public class largestRectangle {

    public static int largestRectangleArea(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i]; // Sentinel at the end

            while (!st.isEmpty() && currHeight < heights[st.peek()]) {
                int height = heights[st.pop()];

                int width;
                if (st.isEmpty()) {
                    width = i; // no smaller bar to the left
                } else {
                    width = i - st.peek() - 1; // between st.peek()+1 and i-1
                }

                maxArea = Math.max(maxArea, height * width);
            }

            st.push(i);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[][] testCases = {
            {2, 1, 5, 6, 2, 3},      // classic example
            {2, 4},                  // small array
            {1, 1, 1, 1},            // all same heights
            {4},                     // single bar
            {},                      // empty array
            {1000000000},            // very large single bar
            {6, 2, 5, 4, 5, 1, 6},   // mixed up heights
            {1, 2, 3, 4, 5},         // increasing heights
            {5, 4, 3, 2, 1},         // decreasing heights
            {2, 1, 2},               // valley shape
            {0, 0, 0},               // all zero heights
            {1000, 1000, 1000, 1000} // large same height bars
        };

        for (int i = 0; i < testCases.length; i++) {
            int result = largestRectangleArea(testCases[i]);
            System.out.println("Test case " + (i + 1) + ": " + Arrays.toString(testCases[i])
                    + " -> Max Area = " + result);
        }
    }
}
