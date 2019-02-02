package play.learn.java.design.interpreter;

import java.util.Stack;

// https://java-design-patterns.com/patterns/interpreter/
public class InterpreterDemo {
	public static void main(String[] args) {
		String tokenString = "4 3 2 - 1 + *";
		Stack<Expression> stack = new Stack<>();

		String[] tokenList = tokenString.split(" ");
		for (String s : tokenList) {
			if (isOperator(s)) {
				Expression rightExpression = stack.pop();
				Expression leftExpression = stack.pop();
				System.out.printf("popped from stack left: %s right: %s%n", leftExpression.interpret(),
						rightExpression.interpret());
				Expression operator = getOperatorInstance(s, leftExpression, rightExpression);
				System.out.printf("operator: %s%n", operator);
				int result = operator.interpret();
				NumberExpression resultExpression = new NumberExpression(result);
				stack.push(resultExpression);
				System.out.printf("push result to stack: %s%n", resultExpression.interpret());
			} else {
				Expression i = new NumberExpression(s);
				stack.push(i);
				System.out.printf("push to stack: %s%n", i.interpret());
			}
		}
		System.out.printf("result: %s%n", stack.pop().interpret());
	}

	public static boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*");
	}

	/**
	 * Get expression for string
	 */
	public static Expression getOperatorInstance(String s, Expression left, Expression right) {
		switch (s) {
		case "+":
			return new PlusExpression(left, right);
		case "-":
			return new MinusExpression(left, right);
		case "*":
			return new MultiplyExpression(left, right);
		default:
			return new MultiplyExpression(left, right);
		}
	}
}
