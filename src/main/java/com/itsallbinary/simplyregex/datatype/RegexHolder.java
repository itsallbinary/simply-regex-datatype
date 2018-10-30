package com.itsallbinary.simplyregex.datatype;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegexHolder {
	private List<String> regexList;

	public RegexHolder() {
		regexList = new ArrayList<String>();
	}

	public void addNext(String nextPatttern) {
		regexList.add(nextPatttern);
	}

	public String build() {
		return regexList.stream().collect(Collectors.joining());
	}

}
