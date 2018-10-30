package com.itsallbinary.simplyregex.datatype.definition;

import com.itsallbinary.simplyregex.definition.Definition;

public class NumberDefinition implements Definition {

	private String numberRegex;

	public NumberDefinition(String numberRegex) {
		super();
		this.numberRegex = numberRegex;
	}

	@Override
	public String regex() {
		return "(" + numberRegex + ")";
	}
}
