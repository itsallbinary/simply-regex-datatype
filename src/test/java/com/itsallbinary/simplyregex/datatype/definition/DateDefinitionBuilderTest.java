package com.itsallbinary.simplyregex.datatype.definition;

import static com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex.date;
import static com.itsallbinary.simplyregex.datatype.utils.NumberUtils.length;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

import com.itsallbinary.simplyregex.SimpleRegex;
import com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex;

public class DateDefinitionBuilderTest {

	@Test
	public void testDateFormat() {
		String regex = SimpleRegex.regex().anywhereInText().def(date().withDateFormat("dd-MM-yy").def()).build();
		System.out.println("Regex of date = " + regex);
		Pattern pattern = Pattern.compile(regex);
		String[] inputs = { "01-01-98", "31-12-09" };
		for (String input : inputs) {
			boolean isMatch = pattern.matcher(input).matches();
			assertEquals("Testing for regex = " + regex + " input =" + input, true, isMatch);
		}

		String[] failInputs = { "00-01-98", "00-00-00", "01-00-98", "01-01-xx", "32-01-98", "01-13-98" };
		for (String input : failInputs) {
			boolean isMatch = pattern.matcher(input).matches();
			assertEquals("Testing for regex = " + regex + " input =" + input, false, isMatch);
		}

	}

	@Ignore
	@Test
	public void test() {

		Date date = new Date();

		printFormattedDate(date, "G - y - M - w - W - D - d - F - E - u - a - H - k - K - h - m - s - S - z - Z - X");
		printFormattedDate(date,
				"GG - yy - MM - ww - WW - DD - dd - FF - EE - uu - aa - HH - kk - KK - hh - mm - ss - SS - zz - ZZ - XX");

		printFormattedDate(date,
				"GGG - yyy - MMM - www - WWW - DDD - ddd - FFF - EEE - uuu - aaa - HHH - kkk - KKK - hhh - mmm - sss - SSS - zzz - ZZZ - XXX");

		printFormattedDate(date,
				"GGG - yyyy - MMMM - www - WWW - DDD - ddd - FFF - EEEE - uuu - aaaa - HHH - kkk - KKK - hhh - mmm - sss - SSS - zzzz - ZZZZ - XXX");

		printFormattedDate(date,
				"GGG - yyyyy - MMMMM - www - WWW - DDD - ddd - FFF - EEEEE - uuu - aaaaa - HHH - kkk - KKK - hhh - mmm - sss - SSS - zzzzz - ZZZZZ - XXX");
		printFormattedDate(date,
				"GGG - yyyyyy - MMMMMM - www - WWW - DDD - ddd - FFF - EEEEEE - uuu - aaaaaa - HHH - kkk - KKK - hhh - mmm - sss - SSS - zzzzzz - ZZZZZZ - XXX");

		Calendar calendar = Calendar.getInstance();
		for (int i = 1; i <= 12; i++) {
			calendar.set(1998, i, 1);
			printFormattedDate(calendar.getTime(), "M");
		}

		printAllValues("y", 1995, 2010, -1, 1, 1, 0, 0, 0);
		printAllValues("yy", 1995, 2010, -1, 1, 1, 0, 0, 0);
		printAllValues("yyy", 1995, 2010, -1, 1, 1, 0, 0, 0);
		printAllValues("yyyy", 1995, 2010, -1, 1, 1, 0, 0, 0);

		printAllValues("M", 0, 11, 1998, -1, 1, 0, 0, 0);
		printAllValues("MM", 0, 11, 1998, -1, 1, 0, 0, 0);
		printAllValues("MMM", 0, 11, 1998, -1, 1, 0, 0, 0);
		printAllValues("MMMM", 0, 11, 1998, -1, 1, 0, 0, 0);

		printAllValues("d", 1, 31, 1998, 6, -1, 0, 0, 0);
		printAllValues("dd", 1, 31, 1998, 6, -1, 0, 0, 0);
		printAllValues("ddd", 1, 31, 1998, 6, -1, 0, 0, 0);
		printAllValues("dddd", 1, 31, 1998, 6, -1, 0, 0, 0);

		printAllValues("E", 0, 6, 2018, 9, -1, 0, 0, 0);
		printAllValues("EE", 0, 6, 2018, 9, -1, 0, 0, 0);
		printAllValues("EEE", 0, 6, 2018, 9, -1, 0, 0, 0);
		printAllValues("EEEE", 0, 6, 2018, 9, -1, 0, 0, 0);

	}

	private void printAllValues(String regex, int start, int end, int year, int month, int date, int hourOfDay,
			int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		String values = "";
		for (int i = start; i <= end; i++) {
			calendar.set(ifNegavtiveReturnIElseSameValue(year, i), ifNegavtiveReturnIElseSameValue(month, i),
					ifNegavtiveReturnIElseSameValue(date, i), ifNegavtiveReturnIElseSameValue(hourOfDay, i),
					ifNegavtiveReturnIElseSameValue(minute, i), ifNegavtiveReturnIElseSameValue(second, i));
			values = values + "," + printFormattedDate(calendar.getTime(), regex);
		}
		System.out.println("Regex = " + regex + " - " + values);
	}

	private int ifNegavtiveReturnIElseSameValue(int year, int i) {
		return year == -1 ? i : year;
	}

	private String printFormattedDate(Date date, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(date);
	}

}
