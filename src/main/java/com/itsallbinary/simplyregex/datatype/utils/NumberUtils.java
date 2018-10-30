package com.itsallbinary.simplyregex.datatype.utils;

public class NumberUtils {
	/**
	 * Get length or number of digits of given int
	 * 
	 * @param start number
	 * @return length
	 */
	public static int length(int start) {
		return String.valueOf(start).length();
	}

	/**
	 * Get digits of int as int[].
	 * 
	 * @param number number
	 * @return array of all digits
	 */
	public static int[] digits(int number) {

		String numberString = String.valueOf(number);
		int[] digits = new int[numberString.length()];
		for (int i = 0; i < numberString.length(); i++) {
			int j = Character.digit(numberString.charAt(i), 10);
			digits[i] = j;
		}

		return digits;
	}

	/**
	 * Join all digits in int[] to form Integer.
	 * 
	 * @param digits all digits in form of int[]
	 * @return Final formed Integer
	 */
	public static Integer joinDigitsToInteger(int[] digits) {
		String finalString = "";
		for (int digit : digits) {
			finalString = finalString + digit;
		}
		Integer finalInt = Integer.parseInt(finalString);
		return finalInt;
	}

	public static String intArrayToString(int[] ints) {
		String string = "[";
		for (int i : ints) {
			string = string + i + ",";
		}
		return string + "]";
	}

	public static String intArrayToString(int[][] ints) {
		String string = "[";
		for (int[] array : ints) {
			string = string + "{";
			for (int i : array) {
				string = string + i + ",";
			}
			string = string + "}";
		}
		return string + "]";
	}
}
