import java.util.*;

public class InfixToPostfix {

    // Function to get precedence of operators
    private static int precedence(char ch) {
        switch (ch) {
            case '^': return 3; // highest precedence
            case '*': 
            case '/': return 2;
            case '+': 
            case '-': return 1;
            default: return -1;
        }
    }

    // Function to check if operator is right-associative
    private static boolean isRightAssociative(char ch) {
        return ch == '^';
    }

    // Core conversion method
    public static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Skip spaces
            if (c == ' ') continue;

            // If operand, add directly to output
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            }

            // If '(', push to stack
            else if (c == '(') {
                stack.push(c);
            }

            // If ')', pop until '('
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    throw new IllegalArgumentException("Invalid expression: mismatched parentheses");
                }
            }

            // If operator
            else {
                while (!stack.isEmpty() && precedence(stack.peek()) > precedence(c) ||
                       (!stack.isEmpty() && precedence(stack.peek()) == precedence(c) && !isRightAssociative(c))) {
                    if (stack.peek() == '(') break;
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            if (stack.peek() == '(' || stack.peek() == ')') {
                throw new IllegalArgumentException("Invalid expression: mismatched parentheses");
            }
            result.append(stack.pop());
        }

        return result.toString();
    }

    // Test runner
    public static void main(String[] args) {
        String[] testExpressions = {
            "A+B*C",                  // Normal precedence
            "(A+B)*C",                // Parentheses
            "A+B*C-D/E",              // Multiple ops
            "A^B^C",                  // Right-associative
            "((A+B)*C)-D",            // Nested parentheses
            "A",                      // Single operand
            "((A))",                  // Redundant parentheses
            "A*(B+C*(D-E))",          // Nested with precedence
            "A+B+C",                  // Left-associative chain
            "A+(B*C-(D/E^F)*G)*H",    // Mixed operators
            "",                       // Empty string
            "((A+B)",                 // Unbalanced parentheses
            "A+B)",                   // Unbalanced - closing extra
            "3+4*2/(1-5)^2^3"         // Classic example with numbers
        };

        for (String expr : testExpressions) {
            try {
                System.out.println("Infix:   " + expr);
                System.out.println("Postfix: " + infixToPostfix(expr));
                System.out.println("------");
            } catch (Exception e) {
                System.out.println("Infix:   " + expr);
                System.out.println("Error:   " + e.getMessage());
                System.out.println("------");
            }
        }
    }
}

