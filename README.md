# Project Simply Regex Datatype

Creator - [Its All Binary](http://itsallbinary.com/project-simply-regex/)

License - Apache 2.0

Releases/Dependencies - [![Build Status](https://travis-ci.org/itsallbinary/simply-regex-datatype.svg?branch=master)](https://travis-ci.org/itsallbinary/simply-regex-datatype)
Refer release page http://itsallbinary.com/project-simply-regex/simply-regex-releases/

User Guide - http://itsallbinary.com/project-simply-regex/simply-regex-user-guide/

More Details - Refer to http://itsallbinary.com/project-simply-regex/ for more details

# [Simply Regex Quick Start](http://itsallbinary.com/project-simply-regex/simply-regex-quick-start/)

### Number Datatype Example:
This is example of simple way of creating number regex.
```java
// Basic Example
import com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex.*;
 
String ipRegex = regex().anywhereInText().def(number().between(0, 255).def())
				.then().exactString(".")
				.then()	.def(number().between(0, 255).def())
				.then().exactString(".")
				.then().def(number().between(0, 255).def())
				.then().exactString(".")
				.then().def(number().between(0, 255).def()).build();
 
		Pattern pattern = Pattern.compile(ipRegex);
		boolean isMatch = pattern.matcher(input).matches();
```
This will build complex regex - ipRegex = ([0-9]|[1-9][0-9]|[1-1][0-9][0-9]|[2-2][0-4][0-9]|[2-2][5-5][0-5]|)\Q.\E([0-9]|[1-9][0-9]|[1-1][0-9][0-9]|[2-2][0-4][0-9]|[2-2][5-5][0-5]|)\Q.\E([0-9]|[1-9][0-9]|[1-1][0-9][0-9]|[2-2][0-4][0-9]|[2-2][5-5][0-5]|)\Q.\E([0-9]|[1-9][0-9]|[1-1][0-9][0-9]|[2-2][0-4][0-9]|[2-2][5-5][0-5]|)


Input: testString = “20.0.255.100” Output: isMatch = true
Input: testString = “20.0.256.100” Output: isMatch = false

### Date Datatype Example:
This is example of simple way of creating date regex using format accepted by [SimpleDateFormat]( https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)
```java
// Basic Example
import com.itsallbinary.simplyregex.datatype.SimpleDataTypeRegex.*;
 
String regex = regex().anywhereInText().def(date().withDateFormat("MM/dd/yyyy").def()).build();
 
		String input = "Today is 10/06/2018.";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean isMatch = matcher.find();
		String matchedDate = matcher.group();
		assertEquals("Testing for regex = " + regex + " input =" + input, "10/06/2018", matchedDate);
```
This will build complex regex - regex = ((0{0,1}[1-9]|[1-1][0-2]|))\Q/\E((0{0,1}[1-9]|[1-2][0-9]|[3-3][0-1]|))\Q/\E([\d]{4})


