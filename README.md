# Project Simply Regex Datatype

Creator - [Its All Binary](http://itsallbinary.com/project-simply-regex/)

License - Apache 2.0

Releases/Dependencies - Refer release page http://itsallbinary.com/project-simply-regex/simply-regex-releases/

User Guide - http://itsallbinary.com/project-simply-regex/simply-regex-user-guide/

More Details - Refer to http://itsallbinary.com/project-simply-regex/ for more details

# [Simply Regex Quick Start](http://itsallbinary.com/project-simply-regex/simply-regex-quick-start/)

### Basic Example:
This is the simplest regex example using simply regex.
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
Input: testString = “20.0.255.100” Output: isMatch = true
Input: testString = “20.0.256.100” Output: isMatch = false
