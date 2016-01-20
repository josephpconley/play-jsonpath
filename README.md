JSONPath library for Play
=====================================

[![Join the chat at https://gitter.im/josephpconley/play-jsonpath](https://badges.gitter.im/josephpconley/play-jsonpath.svg)](https://gitter.im/josephpconley/play-jsonpath?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This library enables JSONPath queries on the Play JsValue.

Explanation of JSONPath can be found here: [http://goessner.net/articles/JsonPath/](http://goessner.net/articles/JsonPath/)

This version should work with Play apps running 2.1.x through 2.3.x (update for Play 2.4.x is [pending](https://github.com/josephpconley/play-jsonpath/issues/4)). 

## Install

`libraryDependencies += "com.josephpconley" %% "play-jsonpath" % "1.1"`

## Example Usage

```scala
val json = Json.parse("""
{
 "id": 1,
 "name": "Joe",
 "tags": ["programmer", "husband", "father", "golfer"],
 "address": [
    {"id": 2, "street": "123 Main St.", "city": "Springfield", "state": "PA"},
    {"id": 3, "street": "456 Main St.", "city": "Sea Isle City", "state": "NJ", "beach": true}
 ]
}
"""

import com.josephpconley.jsonpath.JSONPath

JSONPath.query("$.id", json)                    //JsNumber(1)
JSONPath.query("$..id", json)                   //JsArray(JsNumber(1), JsNumber(2), JsNumber(3))
JSONPath.query("$.address[*].city", json)       //JsArray(JsString("Springfield"), JsString("Sea Isle City"))
JSONPath.query("$.address[?(@.beach)]", json)   //JsArray(JsObject(Seq("id" -> JsNumber(3), "street" -> JsString("456 Main St."), "city" -> JsString("Sea Isle City"), "state" -> JsString("NJ"), "beach" -> JsBoolean(true)))
```

## Blog post

You can read more about the library [here](http://www.josephpconley.com/2014/04/15/jsonpath-for-play.html)