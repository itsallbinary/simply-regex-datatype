package com.itsallbinary.simplyregex.datatype.definition;

import java.util.ArrayList;
import java.util.List;

import com.itsallbinary.simplyregex.definition.DefinitionBuilder;

public class NumberDefinitionBuilderWorking implements DefinitionBuilder<NumberDefinition> {

	public NumberDefinitionBuilderWorking between(int start, int end) {

		return this;
	}

	public String buildNumberRegex(int start, int end) {

		String finalRegex = "";

		int[][] boundaries = { { 0, 9 }, { 10, 99 }, { 100, 999 }, { 1000, 9999 } };

		for (int boundaryCount = 0; boundaryCount < String.valueOf(end).length(); boundaryCount++) {
			System.out.println("boundaryCount = " + boundaryCount + " start = " + start + " end = " + end);
			int bottom = boundaries[boundaryCount][0];
			int top = boundaries[boundaryCount][1];
			System.out.println("bottom = " + bottom + " top = " + top);

			int reduceTill = end <= top ? boundaryCount : 0;
			int expandTill = start >= bottom && start < top ? boundaryCount : 0;
			System.out.println("preloop boundaryCount = " + boundaryCount + " reduceTill =  " + reduceTill
					+ " expandTill = " + expandTill);

			for (int reduceCount = boundaryCount; (boundaryCount - reduceCount) <= reduceTill; reduceCount--) {
				System.out.println("reduceCount = " + reduceCount + " boundaryCount = " + boundaryCount
						+ " reduceTill =  " + reduceTill + " expandTill = " + expandTill);
				int endForThisLoop = top;

				if (end <= top) {
					if (reduceCount != 0) {
						endForThisLoop = reduceEnd(end, reduceCount);
						System.out.println("endForThisLoop reduced to = " + endForThisLoop);
					} else {
						System.out.println("endForThisLoop settings to end = " + end);
						endForThisLoop = end;
					}

				}
				System.out.println("endForThisLoop = " + endForThisLoop);

				if (start >= bottom && start <= top) {

					int[] startDigits = digits(start);
					int[] endDigits = digits(endForThisLoop);

					System.out.println(
							"startDigits = " + printIntArray(startDigits) + " endDigits = " + printIntArray(endDigits));

					for (int i = 0; i <= boundaryCount; i++) {

						System.out.println("Rotating for i = " + i);

						finalRegex = finalRegex + getClassForDigit(startDigits[i], endDigits[i]);
						System.out.println("finalRegex in loop = " + finalRegex);
					}

					finalRegex = finalRegex + "|";
					System.out.println("--- finalRegex in reduce loop  = " + finalRegex);

					System.out.println("end = " + end + " endForThisLoop = " + endForThisLoop);
					if (endForThisLoop <= end) {
						start = endForThisLoop + 1;
					}

				}
			}
			// }
			System.out.println("------------------------- finalRegex in boundary loop  = " + finalRegex);
			System.out.println("Remaining = " + start + " till " + end);
		}

		return finalRegex;

	}

	private String getClassForDigit(int start, int end) {
		if (end > 0 && end <= 9) {
			return "[" + start + "-" + end + "]";
		} else {
			return "[" + start + "-9]";
		}

	}

	private int[] digits(int number) {

		String numberString = String.valueOf(number);
		int[] digits = new int[numberString.length()];
		for (int i = 0; i < numberString.length(); i++) {
			int j = Character.digit(numberString.charAt(i), 10);
//			System.out.println("digit: " + j);
			digits[i] = j;
		}

		return digits;
	}

	private int reduceEnd(int end, int numberOfDigitsToReduce) {

		int[] digits = digits(end);
		System.out.println(
				"Digits to reduce = " + printIntArray(digits) + " numberOfDigitsToReduce = " + numberOfDigitsToReduce);

		int reducedTill = 0;

		for (int i = digits.length; (digits.length - i) < numberOfDigitsToReduce; i--) {
			System.out.println("digits[" + (i - 1) + "]=9");
			digits[i - 1] = 9;
			reducedTill = i - 1;
		}
		digits[reducedTill - 1] = digits[reducedTill - 1] - 1;

		String finalString = "";
		for (int digit : digits) {
			finalString = finalString + digit;
		}
		System.out.println("reduceEnd = " + finalString);
		return Integer.parseInt(finalString);

	}

	private int expandStart(int start, int numberOfDigitsToExpand) {

		int[] digits = digits(start);
		System.out.println(
				"Digits to expand = " + printIntArray(digits) + " numberOfDigitsToExpand = " + numberOfDigitsToExpand);

		int expandTill = 0;

		for (int i = digits.length; (digits.length - i) < numberOfDigitsToExpand; i--) {
			System.out.println("digits[" + (i - 1) + "]=9");
			digits[i - 1] = 9;
			expandTill = i - 1;
		}
		digits[expandTill - 1] = digits[expandTill - 1] + 1;

		String finalString = "";
		for (int digit : digits) {
			finalString = finalString + digit;
		}
		System.out.println("reduceEnd = " + finalString);
		return Integer.parseInt(finalString);

	}

	private String printIntArray(int[] ints) {
		String string = "[";
		for (int i : ints) {
			string = string + i + ",";
		}
		return string + "]";
	}

	@Override
	public NumberDefinition def() {
		// TODO Auto-generated method stub
		return null;
	}
}
