package com.itsallbinary.simplyregex.datatype.definition;

import static com.itsallbinary.simplyregex.datatype.utils.NumberUtils.length;
import static com.itsallbinary.simplyregex.datatype.utils.NumberUtils.digits;
import static com.itsallbinary.simplyregex.datatype.utils.NumberUtils.joinDigitsToInteger;
import static com.itsallbinary.simplyregex.datatype.utils.NumberUtils.intArrayToString;

import java.util.Arrays;

import com.itsallbinary.simplyregex.SimpleRegex;
import com.itsallbinary.simplyregex.definition.DefinitionBuilder;

public class NumberDefinitionBuilder implements DefinitionBuilder<NumberDefinition> {

	private int start;

	private int end;

	private boolean withLeadingZerosOptional;

	private boolean withLeadingZerosMandatory;

	public NumberDefinitionBuilder between(int start, int end) {

		/*
		 * TODO - Add validations
		 * 
		 * - if start grater or equal end, then error
		 * 
		 * - If start/end negative error
		 * 
		 * - If end is closer to Integer.MAX error as boundary cannot be calculated in
		 * digit count in same as MAX
		 */
		if (start >= end || start < 0 || end < 0) {
			throw new IllegalArgumentException("Start should be less than end.");
		}

		this.start = start;
		this.end = end;

		return this;
	}

	public NumberDefinitionBuilder withLeadingZerosOptional() {
		this.withLeadingZerosOptional = true;
		return this;
	}

	public NumberDefinitionBuilder withLeadingZerosMandatory() {
		this.withLeadingZerosMandatory = true;
		return this;
	}

	private String buildNumberRegex(int start, int end, boolean withLeadingZerosOptional,
			boolean withLeadingZerosMandatory) {

		int constantDigitLength = (withLeadingZerosOptional || withLeadingZerosMandatory) ? length(end) : -1;

		String finalRegex = "";

		int[][] boundaries = buildBoundaries(end);

		for (int boundaryCount = length(start) - 1; boundaryCount < length(end); boundaryCount++) {

			int lowerBoundary = boundaries[boundaryCount][0];
			int higherBoundary = boundaries[boundaryCount][1];

			int expandTill = start > lowerBoundary ? boundaryCount : 0;

			log("boundaryCount = " + boundaryCount + " start = " + start + " end = " + end + " lowerBoundary = "
					+ lowerBoundary + " higherBoundary = " + higherBoundary + " expandTill = " + expandTill);

			int startForExpandLoop = start;
			int endForExpandLoop = start;
			for (int expandCount = 1; expandCount < expandTill + 1; expandCount++) {

				if (!(end < higherBoundary && expandCount == boundaryCount + 1)) {

					int startFilledWithNineFromEnd = fillWithNineFromEnd(startForExpandLoop, expandCount);
					endForExpandLoop = end < startFilledWithNineFromEnd ? end : startFilledWithNineFromEnd;
					log("Should break for end? end = " + end + " startFilledWithNineFromEnd = "
							+ startFilledWithNineFromEnd + " startForExpandLoop= " + startForExpandLoop
							+ " endForExpandLoop = " + endForExpandLoop);

					if (endForExpandLoop < startFilledWithNineFromEnd) {
						break;
					}

					finalRegex = finalRegex + buildCharClassesByAllDigits(boundaryCount, endForExpandLoop,
							startForExpandLoop, constantDigitLength, withLeadingZerosMandatory) + "|";
					startForExpandLoop = endForExpandLoop + 1;

					log("Regex in expand loop " + expandCount + " = " + finalRegex);
				}

			}

			log("Should break? startForExpandLoop = " + startForExpandLoop + " end = " + end);
			if (startForExpandLoop >= end) {
				break;
			}

			log("Changing start " + start + " to " + startForExpandLoop);
			start = startForExpandLoop;

			log("----- Final Regex in expand loop = " + finalRegex);

			int reduceTill = end <= higherBoundary ? boundaryCount : 0;

			for (int reduceCount = boundaryCount; (boundaryCount - reduceCount) <= reduceTill; reduceCount--) {
				log("reduceCount = " + reduceCount + " boundaryCount = " + boundaryCount + " reduceTill =  "
						+ reduceTill);
				int endForThisLoop = higherBoundary;

				if (end <= higherBoundary) {
					if (reduceCount != 0) {
						endForThisLoop = reduceEnd(end, reduceCount);
						log("endForThisLoop reduced to = " + endForThisLoop);

					} else {
						log("endForThisLoop settings to end = " + end);
						endForThisLoop = end;
					}

				}
				log("start = " + start + " endForThisLoop = " + endForThisLoop + " lowerBoundary = " + lowerBoundary
						+ " higherBoundary = " + higherBoundary);

				if (start >= lowerBoundary && start <= higherBoundary && start <= endForThisLoop) {

					finalRegex = finalRegex + buildCharClassesByAllDigits(boundaryCount, endForThisLoop, start,
							constantDigitLength, withLeadingZerosMandatory) + "|";

					log("--- finalRegex in reduce loop  = " + finalRegex);

					if (endForThisLoop <= end) {
						start = endForThisLoop + 1;
					}
					log("New start = " + start + "end = " + end + " endForThisLoop = " + endForThisLoop);

				}
			}

			log("------------------------- finalRegex in boundary loop  = " + finalRegex);
			log("Remaining = " + start + " till " + end);
		}
		log("------------------------------------------------------------------------------------");
		log("### Regex = " + finalRegex);
		log("------------------------------------------------------------------------------------");

		return finalRegex;

	}

	/**
	 * Build boundaries appropriate for end value like { { 0, 9 }, { 10, 99 }, {
	 * 100, 999 }, { 1000, 9999 } }
	 * 
	 * @param end end number
	 * @return boundary array
	 */
	private int[][] buildBoundaries(int end) {
		int endDigitCount = length(end) + 1;

		int[][] boundaries = new int[endDigitCount + 1][2];
		int currentDigitCount = 1;
		for (int i = 0; i < boundaries.length; i++) {
			boundaries[i][0] = boundaryStart(currentDigitCount);
			boundaries[i][1] = boundaryEnd(currentDigitCount);
			currentDigitCount = currentDigitCount + 1;

		}
		log("======Final Boundaries = " + intArrayToString(boundaries));
		return boundaries;
	}

	private int boundaryStart(int digitsCount) {
		int[] boundaryStart = new int[digitsCount];
		if (digitsCount == 1) {
			boundaryStart[0] = 0;
		} else {
			boundaryStart[0] = 1;
			for (int i = 1; i < digitsCount; i++) {
				boundaryStart[i] = 0;

			}
		}
		return joinDigitsToInteger(boundaryStart);

	}

	private int boundaryEnd(int digitsCount) {
		int[] boundaryEnd = new int[digitsCount];

		for (int i = 0; i < digitsCount; i++) {
			boundaryEnd[i] = 9;
		}

		return joinDigitsToInteger(boundaryEnd);

	}

	/**
	 * This method builds character classes by digits on start & end
	 * 
	 * @param digitCount - Count of digits in start and stop.
	 * @param end        - end
	 * @param start      start
	 * @return character classes by digits like if start = 10 & end = 19 then output
	 *         [1-0][0-9]
	 */
	private String buildCharClassesByAllDigits(int digitCount, int end, int start, int constantDigitLength,
			boolean withLeadingZerosMandatory) {
		String finalRegex = "";

		int[] startDigits = digits(start);
		int[] endDigits = digits(end);
		System.out.println(
				"Zero fill required? digitCount " + digitCount + " constantDigitLength " + constantDigitLength);
		if ((digitCount + 1) < constantDigitLength) {
			int digitsToFill = constantDigitLength - (digitCount + 1);
			System.out.println("Adding zero filling = " + digitsToFill);
			if (withLeadingZerosMandatory) {
				finalRegex = SimpleRegex.regex().anywhereInText().occurancesExact(digitsToFill).of('0').build();
			} else {
				finalRegex = SimpleRegex.regex().anywhereInText().occurancesBetween(0, digitsToFill).of('0').build();

			}
		}

		log("[buildCharClassesByAllDigits] digitCount = " + digitCount + " start = " + start + " end = " + end
				+ " startDigits = " + intArrayToString(startDigits) + " endDigits = " + intArrayToString(endDigits));

		for (int i = 0; i <= digitCount; i++) {

			log("Rotating for i = " + i);

			finalRegex = finalRegex + buildCharClassForSingleDigit(startDigits[i], endDigits[i]);
			log("finalRegex in loop = " + finalRegex);
		}
		return finalRegex;
	}

	/**
	 * Builds character class for single digit start & end. For ex: start =0 end = 9
	 * then output will be [0-9]
	 * 
	 * @param start - Single digit start
	 * @param end   - Single digit end
	 * @return returns character class for single digit like [0-9]
	 */
	private String buildCharClassForSingleDigit(int start, int end) {
		if (end >= 0 && end <= 9) {
			return "[" + start + "-" + end + "]";
		} else {
			return "[" + start + "-9]";
		}

	}

	private int reduceEnd(int end, int numberOfDigitsToReduce) {

		int[] digits = digits(end);
		log("Digits to reduce = " + intArrayToString(digits) + " numberOfDigitsToReduce = " + numberOfDigitsToReduce);

		int reducedTill = 0;

		for (int i = digits.length; (digits.length - i) < numberOfDigitsToReduce; i--) {
			log("digits[" + (i - 1) + "]=9");
			digits[i - 1] = 9;
			reducedTill = i - 1;
		}
		log("reducedTill = " + reducedTill);
		if (digits.length == 1) {
			digits[reducedTill - 1] = digits[reducedTill - 1] - 1;
		} else {
			int[] remainingDigits = Arrays.copyOfRange(digits, 0, reducedTill);
			Integer remainingNumber = joinDigitsToInteger(remainingDigits) - 1;
			log("remainingNumber = " + remainingNumber);
			remainingDigits = digits(remainingNumber);
			log("remainingDigits = " + intArrayToString(remainingDigits));

			for (int i = 0; i < remainingDigits.length; i++) {
				digits[i] = remainingDigits[i];
			}
		}
		return joinDigitsToInteger(digits);

	}

	private int fillWithNineFromEnd(int end, int numberOfDigitsToExpand) {

		int[] digits = digits(end);
		log("Digits to expand = " + intArrayToString(digits) + " numberOfDigitsToExpand = " + numberOfDigitsToExpand);

		for (int i = digits.length; (digits.length - i) < numberOfDigitsToExpand; i--) {
			digits[i - 1] = 9;
		}

		String finalString = "";
		for (int digit : digits) {
			finalString = finalString + digit;
		}
		log("fillWithNineFromEnd = " + finalString);
		return Integer.parseInt(finalString);

	}

	private void log(String message) {
		System.out.println(message);
	}

	@Override
	public NumberDefinition def() {
		return new NumberDefinition(buildNumberRegex(start, end, withLeadingZerosOptional, withLeadingZerosMandatory));
	}

}
