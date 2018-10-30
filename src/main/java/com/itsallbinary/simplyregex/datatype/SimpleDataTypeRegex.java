package com.itsallbinary.simplyregex.datatype;

import com.itsallbinary.simplyregex.datatype.definition.DateDefinitionBuilder;
import com.itsallbinary.simplyregex.datatype.definition.NumberDefinitionBuilder;

/**
 * Starting class to start building regex.
 * 
 * @author ravik
 *
 */
public class SimpleDataTypeRegex {

	public static NumberDefinitionBuilder number() {
		return new NumberDefinitionBuilder();
	}

	public static DateDefinitionBuilder date() {
		return new DateDefinitionBuilder();
	}
}
