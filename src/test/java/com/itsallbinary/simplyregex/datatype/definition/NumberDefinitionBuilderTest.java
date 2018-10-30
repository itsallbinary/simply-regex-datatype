package com.itsallbinary.simplyregex.datatype.definition;

import static com.itsallbinary.simplyregex.SimpleRegex.regex;
import static com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex.number;
import static com.itsallbinary.simplyregex.datatype.utils.NumberUtils.digits;
import static com.itsallbinary.simplyregex.datatype.utils.NumberUtils.length;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.itsallbinary.simplyregex.SimpleRegex;
import com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex;
import com.itsallbinary.simplyregex.datatype.utils.NumberUtils;

@RunWith(Parameterized.class)
public class NumberDefinitionBuilderTest {

	static Object[][] tempTestParams = new Object[][] { { 8, 11 }, };

	static Object[][] testParams = new Object[][] { { 4, 5 }, { 8, 11 }, { 0, 9 }, { 3, 8 }, { 5, 80 }, { 22, 93 },
			{ 1, 31 }, { 1, 12 }, { 5, 255 }, { 15, 255 }, { 101, 255 }, { 101, 189 }, { 100, 999 }, { 199, 255 },
			{ 5, 2050 }, { 752, 8649 }, { 7528, 8649 }, { 0, 9999 }, { 1980, 2030 }, { 12345, 45678 },
			{ 3827635, 9827635 }, { 0, 9999999 } };

	@Parameters(name = "{index}: Start = {0} | End = {1} ")
	public static Collection<Object[]> data() {
		return Arrays.asList(testParams);
//		return Arrays.asList(tempTestParams);
	}

	@Parameter // first data value (0) is default
	public /* NOT private */ int start;

	@Parameter(1)
	public /* NOT private */ int end;

	@Test
	public void test() {
		// int start = 201;
		// int end = 255;
//		String regex = new NumberDefinitionBuilder().buildNumberRegex(start, end);

		String regexNoLeadingZeros = regex().anywhereInText().def(number().between(start, end).def()).build();
		System.out.println(regexNoLeadingZeros);
		assertAllValues(regexNoLeadingZeros, false, false);

		String regexAllowLeadingZeros = regex().anywhereInText()
				.def(number().between(start, end).withLeadingZerosOptional().def()).build();
		System.out.println(regexAllowLeadingZeros);
		assertAllValues(regexAllowLeadingZeros, true, false);

		String regexAllowLeadingZerosMandatory = regex().anywhereInText()
				.def(number().between(start, end).withLeadingZerosMandatory().def()).build();
		System.out.println(regexAllowLeadingZerosMandatory);
		assertAllValues(regexAllowLeadingZerosMandatory, true, true);

	}

	private void assertAllValues(String regex, boolean allowLeadingZeros, boolean withLeadingZerosMandatory) {
		assertForAllValuesBetween(regex, start, end, true, length(end), withLeadingZerosMandatory);
		assertForAllValuesWithZeroPaddingBetween(regex, start, end, true, length(end), allowLeadingZeros);

		assertForAllValuesBetween(regex, 0, start - 1, false, length(end), withLeadingZerosMandatory);
		assertForAllValuesWithZeroPaddingBetween(regex, 0, start - 1, false, length(end), allowLeadingZeros);

		assertForAllValuesBetween(regex, end + 1, end + 100, false, length(end), withLeadingZerosMandatory);
		assertForAllValuesWithZeroPaddingBetween(regex, end + 1, end + 100, false, length(end), allowLeadingZeros);
	}

	private void assertForAllValuesBetween(String regex, int start, int end, boolean expectedResult,
			int constantDigitLength, boolean withLeadingZerosMandatory) {
		if (end >= start) {

			Pattern pattern = Pattern.compile(regex);
			for (int i = start; i <= end; i++) {

				boolean finalExpectedResult = expectedResult;
				if (expectedResult && withLeadingZerosMandatory && length(i) != constantDigitLength) {
					finalExpectedResult = false;
				}

				String numberString = String.valueOf(i);
				// System.out.println("Testing for " + numberString + " regex = " + regex);

				boolean isMatch = pattern.matcher(numberString).matches();

				assertEquals("Testing for " + numberString + " regex = " + regex, finalExpectedResult, isMatch);
			}
			System.out.println("JUNIT - good for " + start + " till " + end + " Matched = " + expectedResult);
		}
	}

	private void assertForAllValuesWithZeroPaddingBetween(String regex, int start, int end, boolean expectedResult,
			int constantDigitLength, boolean allowLeadingZeros) {
		if (end >= start) {

			Pattern pattern = Pattern.compile(regex);
			for (int i = start; i <= end; i++) {
				boolean finalExpectedResult = expectedResult;
				String numberString = padZeros(i, constantDigitLength);

				boolean isMatch = pattern.matcher(numberString).matches();

				// If allowLeadingZeros = false, then only match number which are not padded.If
				// padded match = false
				if (!allowLeadingZeros && !String.valueOf(i).equals(numberString)) {
					finalExpectedResult = false;
				}

//				System.out.println("Testing for, without padding " + i + " with padding " + numberString
//						+ " expectedResult " + expectedResult + " finalExpectedResult " + finalExpectedResult
//						+ " allowLeadingZeros " + allowLeadingZeros + " i " + String.valueOf(i) + " numberString "
//						+ numberString + " String.valueOf(i).equals(numberString) "
//						+ String.valueOf(i).equals(numberString)
//						+ " !allowLeadingZeros && !String.valueOf(i).equals(numberString) "
//						+ (!allowLeadingZeros && !String.valueOf(i).equals(numberString)) + " regex = " + regex);

				assertEquals("Testing for " + numberString + " regex = " + regex, finalExpectedResult, isMatch);
			}
			System.out.println("JUNIT - good for " + padZeros(start, constantDigitLength) + " till "
					+ padZeros(end, constantDigitLength) + " Matched = " + expectedResult);
		}
	}

	private String padZeros(int number, int constantDigitLength) {
		String finalNumber = "" + number;
		if (length(number) < constantDigitLength) {
			// int[] finalNumberArr = new int[constantDigitLength];
			// Arrays.fill(finalNumberArr, 0);
			// int[] originalNumberArr = digits(number);
			for (int i = 0; i < constantDigitLength - length(number); i++) {
				// finalNumberArr[length(number) + i] = originalNumberArr[i];
				finalNumber = "0" + finalNumber;
			}

//			System.out.println("Original number = " + number + " final = " + finalNumber + " constantDigitLength = "
//					+ constantDigitLength + " length(number) = " + length(number));

		}
		return finalNumber;
	}

}
