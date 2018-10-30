package com.itsallbinary.simplyregex.datatype.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.itsallbinary.simplyregex.definition.Definition;
import com.itsallbinary.simplyregex.definition.DefinitionBuilder;

public class DateDefinitionBuilder implements DefinitionBuilder<Definition> {

	private String dateFormat;

	public DateDefinitionBuilder withDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		return this;
	}

	private String createDateRegex(String dateFormat) {
		String finalRegex = "";

		char[] dateFormatChars = dateFormat.toCharArray();

		DateTimePattern earlierPattern = null;
		int earlierPatternCount = 0;

		List<FormatCharacter> formatCharacters = new ArrayList<>();

		int charListCounter = 0;

		for (int i = 0; i < dateFormatChars.length; i++) {
			char c = dateFormatChars[i];

			DateTimePattern pattern = DateTimePattern.parse(c);
			log("DateTimePattern = " + pattern + " earlierPattern = " + earlierPattern + " earlierPatternCount = "
					+ earlierPatternCount);
			if (pattern != null) {
				if (charListCounter != 0 && formatCharacters.get(charListCounter - 1).getPattern() != null
						&& formatCharacters.get(charListCounter - 1).getPattern().equals(pattern)) {
					formatCharacters.get(charListCounter - 1).increaseOccurance();
				} else {
					formatCharacters.add(new FormatCharacter(pattern, 1));
					charListCounter = charListCounter + 1;
				}
			} else {
				formatCharacters.add(new FormatCharacter(c));
				charListCounter = charListCounter + 1;
			}

			log("------- finalRegex at end of loop = " + finalRegex);
		}
		log("formatCharacters = " + formatCharacters);
		finalRegex = "";
		for (FormatCharacter character : formatCharacters) {
			log("c = " + character.getC() + " pattern = " + character.getPattern() + " occurance = "
					+ character.getOccurance());
			finalRegex = finalRegex + (character.getPattern() == null ? Pattern.quote("" + character.getC())
					: "(" + character.getPattern().getRegex(character.getOccurance()) + ")");
		}
		log("------- finalRegex  = " + finalRegex);

		return finalRegex;
	}

	@Override
	public Definition def() {
		return new DateDefinition(createDateRegex(dateFormat));
	}

	private void log(String message) {
		System.out.println("[DATE] " + message);
	}

	class FormatCharacter {
		private char c;
		private DateTimePattern pattern;
		private int occurance;

		public FormatCharacter(char c) {
			super();
			this.c = c;
		}

		public FormatCharacter(DateTimePattern pattern, int occurance) {
			super();
			this.pattern = pattern;
			this.occurance = occurance;
		}

		char getC() {
			return c;
		}

		DateTimePattern getPattern() {
			return pattern;
		}

		void increaseOccurance() {
			occurance = occurance + 1;
		}

		int getOccurance() {
			return occurance;
		}

		@Override
		public String toString() {
			return pattern == null ? "" + c : pattern + "(" + occurance + ")";
		}

	}

}
