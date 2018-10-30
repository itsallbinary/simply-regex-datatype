package com.itsallbinary.simplyregex.datatype.definition;

import static com.itsallbinary.simplyregex.SimpleRegex.charThatIs;
import static com.itsallbinary.simplyregex.SimpleRegex.regex;
import static com.itsallbinary.simplyregex.datatype.utils.DateUtils.ERA_VALUES;

import java.util.Arrays;

import com.itsallbinary.simplyregex.SimpleRegex;
import com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex;
import com.itsallbinary.simplyregex.datatype.utils.DateUtils;

public enum DateTimePattern {

	G(regex().anywhereInText().oneOfTheStrings(ERA_VALUES).build(),
			regex().anywhereInText().oneOfTheStrings(ERA_VALUES).build(),
			regex().anywhereInText().oneOfTheStrings(ERA_VALUES).build(),
			regex().anywhereInText().oneOfTheStrings(ERA_VALUES).build()),
	y(regex().anywhereInText().occurancesExact(4).of(charThatIs().anyDigitChar().build()).build(),
			regex().anywhereInText().occurancesExact(2).of(charThatIs().anyDigitChar().build()).build(),
			regex().anywhereInText().occurancesExact(4).of(charThatIs().anyDigitChar().build()).build(),
			regex().anywhereInText().occurancesExact(4).of(charThatIs().anyDigitChar().build()).build()),
	Y("", "", "", ""),
	M(regex().anywhereInText().def(SimpleDataTypeRegex.number().between(1, 12).def()).build(),
			regex().anywhereInText().def(SimpleDataTypeRegex.number().between(1, 12).withLeadingZerosOptional().def())
					.build(),
			regex().anywhereInText().oneOfTheStrings(DateUtils.MONTH_SHORT_VALUES).build(),
			regex().anywhereInText().oneOfTheStrings(DateUtils.MONTH_LON_VALUES).build()),
	w("", "", "", ""), W("", "", "", ""), D("", "", "", ""),
	d(regex().anywhereInText().def(SimpleDataTypeRegex.number().between(1, 31).def()).build(),
			regex().anywhereInText().def(SimpleDataTypeRegex.number().between(1, 31).withLeadingZerosOptional().def())
					.build(),
			regex().anywhereInText().def(SimpleDataTypeRegex.number().between(1, 31).withLeadingZerosOptional().def())
					.build(),
			regex().anywhereInText().def(SimpleDataTypeRegex.number().between(1, 31).withLeadingZerosOptional().def())
					.build()),
	F("", "", "", ""), E("", "", "", ""), u("", "", "", ""), a("", "", "", ""), H("", "", "", ""), k("", "", "", ""),
	K("", "", "", ""), h("", "", "", ""), m("", "", "", ""), s("", "", "", ""), S("", "", "", ""), z("", "", "", ""),
	Z("", "", "", ""), X("", "", "", "");

	private String singleRegex;

	private String doubleRegex;

	private String tripleRegex;

	private String quadrapleRegex;

	private DateTimePattern(String singleRegex, String doubleRegex, String tripleRegex, String quadrapleRegex) {
		regex().anywhereInText().def(SimpleDataTypeRegex.number().between(1, 31).withLeadingZerosOptional().def())
				.build();
		this.singleRegex = singleRegex;
		this.doubleRegex = doubleRegex;
		this.tripleRegex = tripleRegex;
		this.quadrapleRegex = quadrapleRegex;
	}

	public static DateTimePattern parse(char c) {
		return Arrays.stream(values()).filter(v -> v.toString().equals("" + c)).findAny().orElse(null);
	}

	public String getRegex(int countOfOccurance) {
		switch (countOfOccurance) {
		case 1:
			return singleRegex;
		case 2:
			return doubleRegex;
		case 3:
			return tripleRegex;
		case 4:
			return quadrapleRegex;
		default:
			return null;
		}
	}

}
