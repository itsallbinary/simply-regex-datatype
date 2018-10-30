package com.itsallbinary.simplyregex.datatype.definition;

import com.itsallbinary.simplyregex.definition.Definition;

public class DateDefinition implements Definition {

	private String dateRegex;

	public DateDefinition(String dateRegex) {
		super();
		this.dateRegex = dateRegex;
	}

	@Override
	public String regex() {
		return dateRegex;
	}
}
