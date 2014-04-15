JSONPath library for Play
=====================================

This library enables JSONPath queries on the Play JsValue.

Explanation of JSONPath can be found here: [http://goessner.net/articles/JsonPath/](http://goessner.net/articles/JsonPath/)

## Install

`libraryDependencies += "com.josephpconley" %% "play-jsonpath" % "1.0"`

## Example Usage

```scala
val json = Json.parse("""
{
 "id": 1,
 "name": "Joe",
 "tags": ["programmer", "husband", "father", "golfer"],
 "address": [
 {"id": 2, "street": "123 Main St.", "city": "Springfield", "state": "PA"},
 {"id": 3, "street": "456 Main St.", "city": "Devon", "state": "PA", "work": true}
 ]
}
"""

import com.josephpconley.jsonpath.JSONPath


//returns id field from root
JSONPath.query("$.id", json)

//returns all id fields
JSONPath.query("$..id", json)

//returns an array of cities from the address array
JSONPath.query("$.address[*].city", json)
```



