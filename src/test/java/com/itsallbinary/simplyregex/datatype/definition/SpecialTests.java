package com.itsallbinary.simplyregex.datatype.definition;

import static com.itsallbinary.simplyregex.SimpleRegex.regex;
import static com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex.date;
import static com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex.number;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.itsallbinary.simplyregex.SimpleRegex;
import com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex;

public class SpecialTests {

	@Test
	public void testIpAddress() {
		String ipRegex = regex().anywhereInText().def(number().between(0, 255).def()).then().exactString(".").then()
				.def(number().between(0, 255).def()).then().exactString(".").then().def(number().between(0, 255).def())
				.then().exactString(".").then().def(number().between(0, 255).def()).build();
		System.out.println("ipRegex = " + ipRegex);
		String input = "20.0.255.100";
		Pattern pattern = Pattern.compile(ipRegex);
		boolean isMatch = pattern.matcher(input).matches();
		assertEquals("Testing for regex = " + ipRegex + " input =" + input, true, isMatch);
	}

	@Test
	public void testFindDate() {
		String regex = regex().anywhereInText().def(date().withDateFormat("MM/dd/yyyy").def()).build();

		System.out.println("ipRegex = " + regex);
		String input = "Today is 10/06/2018.";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean isMatch = matcher.find();
		String matchedDate = matcher.group();
		assertEquals("Testing for regex = " + regex + " input =" + input, "10/06/2018", matchedDate);
	}

}
