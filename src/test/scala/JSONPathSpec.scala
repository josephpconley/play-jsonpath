import com.josephpconley.jsonpath.JSONPath
import play.api.libs.json._
import play.api.test._
import io.gatling.jsonpath.Parser

class JSONPathSpec extends PlaySpecification {

  "JSON" should {

    "JsonPath parsing" in {
      val tokens = new Parser().compile("$..region[1:]..game").get
      tokens.size must beEqualTo(4)
    }

    "field" in {
      JSONPath.query("$.id", json) must be_==(JsNumber(1))
      JSONPath.query("$['id']", json) must be_==(JsNumber(1))
    }

    "recursive field" in {
      JSONPath.query("$..id", json) must be_==(JsArray(ids.map(JsNumber(_))))
    }

    "multi fields" in {
      val fields = Seq(JsNumber(1), JsString("Joe"))
      JSONPath.query("$['id', 'name']", json) must be_==(JsArray(fields))
    }

    "any field" in {
      JSONPath.query("$.*", json) must be_==(JsArray(json.fields.map(_._2)))
      JSONPath.query("$.tags.*", json) must be_==(JsArray(tags.map(JsString(_))))
      JSONPath.query("$['tags'].*", json) must be_==(JsArray(tags.map(JsString(_))))
    }

    "recursive any" in {
      JSONPath.query("$..*", json) must be_==(json)
    }

    "array slices" in {
      tags.indices.foreach{ i =>
        JSONPath.query("$.tags[" + i + ":]", json) must be_==(JsArray(tags.drop(i).map(JsString(_))))
      }
      JSONPath.query("$.tags[2]", json) must be_==(JsString("father"))
      JSONPath.query("$.tags[0:3:2]", json) must be_==(JsArray(Seq(JsString(tags(0)), JsString(tags(2)))))
      JSONPath.query("$.tags[-2:]", json) must be_==(JsArray(tags.takeRight(2).map(JsString(_))))
      JSONPath.query("$.tags[:-2]", json) must be_==(JsArray(tags.dropRight(2).map(JsString(_))))
    }

    //TODO need to figure out if we should implicitly flatten on the fly
//    "recursive array slices" in {
//      println(JSONPath.query("$..address[1]", json))
//      true must beTrue
//    }

    "array random" in {
      JSONPath.query("$.tags[0,2]", json) must be_==(JsArray(Seq(JsString(tags(0)), JsString(tags(2)))))
      JSONPath.query("$.tags[-1]", json) must be_==(JsString(tags.last))
    }

    "array recursive" in {
      JSONPath.query("$.address[*].city", json).as[JsArray].value.size must be_==(3)
    }

    "has filter" in {
      JSONPath.query("$.address[?(@.work)]", json).as[JsArray].value.size must be_==(1)
    }

    "comparison filter" in {
      JSONPath.query("$.address[?(@.id < 3)]", json).as[JsArray].value.size must be_==(1)
      JSONPath.query("$.address[?(@.id <= 3)]", json).as[JsArray].value.size must be_==(2)

      JSONPath.query("$.address[?(@.id > 2)]", json).as[JsArray].value.size must be_==(2)
      JSONPath.query("$.address[?(@.id >= 2)]", json).as[JsArray].value.size must be_==(3)

      JSONPath.query("$.address[?(@.state == 'PA')]", json).as[JsArray].value.size must be_==(2)
      JSONPath.query("$.address[?(@.city == 'Springfield')]", json).as[JsArray].value.size must be_==(1)
      JSONPath.query("$.address[?(@.city != 'Devon')]", json).as[JsArray].value.size must be_==(2)
    }

    "boolean filter" in {
      JSONPath.query("$.address[?(@.id > 1 && @.state != 'PA')]", json).as[JsArray].value.size must be_==(1)
      JSONPath.query("$.address[?(@.id < 4 && @.state == 'PA')]", json).as[JsArray].value.size must be_==(2)
      JSONPath.query("$.address[?(@.id == 4 || @.state == 'PA')]", json).as[JsArray].value.size must be_==(3)
      JSONPath.query("$.address[?(@.id == 4 || @.state == 'NJ')]", json).as[JsArray].value.size must be_==(1)
    }

    "print tokens" in {
      val jsonPath = "$..book[0][1]"
//      val jsonPath = "$..book[(@.length-1)]"

      val tokens = new Parser().compile(jsonPath).get
      println(tokens.mkString("\n"))

      tokens.isEmpty must beFalse
    }
  }

  lazy val ids = Seq(1,2,3,4)
  lazy val tags = Seq("programmer", "husband", "father", "golfer")
  lazy val json = Json.parse(testJsonStr).as[JsObject]
  val testJsonStr =
    """
      |{
      | "id": 1,
      | "name": "Joe",
      | "tags": ["programmer", "husband", "father", "golfer"],
      | "address": [
      | {
      |   "id": 2,
      |   "street": "123 Main St.",
      |   "city": "Springfield",
      |   "state": "PA"
      | },
      | {
      |   "id": 3,
      |   "street": "456 Main St.",
      |   "city": "Devon",
      |   "state": "PA",
      |   "work": true
      | },
      | {
      |   "id": 4,
      |   "street": "789 Main St.",
      |   "city": "Sea Isle City",
      |   "state": "NJ"
      | }
      | ]
      |}
    """.stripMargin
}