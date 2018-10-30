# Project Simply Regex 

Creator - [Its All Binary](http://itsallbinary.com/project-simply-regex/)

License - Apache 2.0

Releases/Dependencies - [![Maven Central](https://img.shields.io/maven-central/v/com.itsallbinary/simply-regex.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.itsallbinary%22%20AND%20a:%22simply-regex%22) Refer release page http://itsallbinary.com/project-simply-regex/simply-regex-releases/

User Guide - http://itsallbinary.com/project-simply-regex/simply-regex-user-guide/

More Details - Refer to http://itsallbinary.com/project-simply-regex/ for more details

# [Simply Regex Quick Start](http://itsallbinary.com/project-simply-regex/simply-regex-quick-start/)

### Basic Example:
This is the simplest regex example using simply regex.
```java
// Basic Example
import com.itsallbinary.simplyregex.SimpleRegex;
 
String builtRegex = SimpleRegex.regex().startingWith().exactString("abc").build();
 
Pattern pattern = Pattern.compile(builtRegex);
 
boolean isMatch = pattern.matcher(testString).matches();
```
This will build regex as = ^abc
Input: testString = “abc” Output: isMatch = true
Input: testString = “def” Output: isMatch = false

### Chaining multiple patterns
You can chain multiple patterns to create entire complex regex as shown in below example.

```java
// Chaining example
import com.itsallbinary.simplyregex.SimpleRegex;
 
String builtRegex = SimpleRegex.regex().startingWith().exactString("abc")
                                       .then().oneOfTheCharacters('d', 'e', 'f')
                                       .build();
 
Pattern pattern = Pattern.compile(builtRegex);
 
boolean isMatch = pattern.matcher(testString).matches();
```
This will build regex as = ^abc[def]
Input: testString = “abcd” Output: isMatch = true
Input: testString = “abcg” Output: isMatch = false

### Special wildcard character classes
Simply regex API provides easy readable methods for wild card character classes as shown in below example.

```java
// Special wildcard example
import com.itsallbinary.simplyregex.SimpleRegex;
 
String builtRegex = SimpleRegex.regex().startingWith().anyDigitChar().build();
 
Pattern pattern = Pattern.compile(builtRegex);
 
boolean isMatch = pattern.matcher(testString).matches();
```
This will build regex as = ^\d
Input: testString = “1” Output: isMatch = true
Input: testString = “a” Output: isMatch = false

### Easy & simple quantifiers
Simply regex API provides easy readable methods for quantifiers as shown below.

```java
// Quantifier example
import com.itsallbinary.simplyregex.SimpleRegex;
 
String builtRegex = SimpleRegex.regex().anywhereInText().oneOrMoreOf('a').build();
 
Pattern pattern = Pattern.compile(builtRegex);
 
boolean isMatch = pattern.matcher(testString).matches();
```
This will build regex as = a+
Input: testString = “” Output: isMatch = false
Input: testString = “a” Output: isMatch = true
Input: testString = “aaa” Output: isMatch = true

### Simple way to mix & match groups
Simply regex API provides easy & readable way to create groups & mix with other regex functions.

```java
// Groups example
import com.itsallbinary.simplyregex.SimpleRegex;
 
String builtRegex = SimpleRegex.regex().anywhereInText()
		       .oneOrMoreOf(groupHaving().exactString("abc").then().anyDigitChar().build())
                       .build()
 
Pattern pattern = Pattern.compile(builtRegex);
 
boolean isMatch = pattern.matcher(testString).matches();
```
This will build regex as = (abc\d)+
Input: testString = “abc1abc2” Output: isMatch = true
Input: testString = “abcz” Output: isMatch = false

### Simple capture groups
Simple & fluent way to create capture groups.

```java
// Capture Group Example
String builtRegex = SimpleRegex.regex().anywhereInText()
                      .exactString("My Name is ")
                      .then()
		      .group(
                          groupHaving().oneOrMoreOf(charThatIs().anyWordChar().build()
                       ).build())
                      .then()
                      .exactString(". ")
		      .build();
		
String testString = "My Name is John. My Name is Merry. My Name is Rock. ";
 
Matcher matcher = Pattern.compile(builtRegex).matcher(testString);
matcher.find();
assertEquals("John", matcher.group(1));
matcher.find();
assertEquals("Merry", matcher.group(1));
matcher.find();
assertEquals("Rock", matcher.group(1));
```
