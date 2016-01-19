import com.josephpconley.jsonpath.JSONPath
import org.scalatest.{WordSpec, Matchers}
import play.api.libs.json._
import io.gatling.jsonpath.Parser

class JSONPathSpec extends WordSpec with Matchers {

  "JSONPath" should {

    "parse a valid JSONPath query" in {
      val tokens = new Parser().compile("$..region[1:]..game").get
      tokens.size shouldEqual 4
    }

    "match on field" in {
      JSONPath.query("$.id", json) shouldEqual(JsNumber(1))
      JSONPath.query("$['id']", json) shouldEqual(JsNumber(1))
    }

    "match on recursive field" in {
      JSONPath.query("$..id", json) shouldEqual(JsArray(ids.map(JsNumber(_))))
    }

    "multi fields" in {
      val fields = Seq(JsNumber(1), JsString("Joe"))
      JSONPath.query("$['id', 'name']", json) shouldEqual(JsArray(fields))
    }

    "any field" in {
      JSONPath.query("$.*", json) shouldEqual(JsArray(json.fields.map(_._2)))
      JSONPath.query("$.tags.*", json) shouldEqual(JsArray(tagArray.map(JsString(_))))
      JSONPath.query("$['tags'].*", json) shouldEqual(JsArray(tagArray.map(JsString(_))))
    }

    "recursive any" in {
      JSONPath.query("$..*", json) shouldEqual(json)
    }

    "array slices" in {
      tagArray.indices.foreach{ i =>
        JSONPath.query("$.tags[" + i + ":]", json) shouldEqual(JsArray(tagArray.drop(i).map(JsString(_))))
      }
      JSONPath.query("$.tags[2]", json) shouldEqual(JsString("father"))
      JSONPath.query("$.tags[0:3:2]", json) shouldEqual(JsArray(Seq(JsString(tagArray(0)), JsString(tagArray(2)))))
      JSONPath.query("$.tags[-2:]", json) shouldEqual(JsArray(tagArray.takeRight(2).map(JsString(_))))
      JSONPath.query("$.tags[:-2]", json) shouldEqual(JsArray(tagArray.dropRight(2).map(JsString(_))))
    }

    "recursive array slices" ignore {
      println(JSONPath.query("$..address[1]", json))
    }

    "array random" in {
      JSONPath.query("$.tags[0,2]", json) shouldEqual(JsArray(Seq(JsString(tagArray(0)), JsString(tagArray(2)))))
      JSONPath.query("$.tags[-1]", json) shouldEqual(JsString(tagArray.last))
    }

    "array recursive" in {
      JSONPath.query("$.address[*].city", json).as[JsArray].value.size shouldEqual(3)
    }

    "has filter" in {
      JSONPath.query("$.address[?(@.work)]", json).as[JsArray].value.size shouldEqual(1)
    }

    "comparison filter" in {
      JSONPath.query("$.address[?(@.id < 3)]", json).as[JsArray].value.size shouldEqual(1)
      JSONPath.query("$.address[?(@.id <= 3)]", json).as[JsArray].value.size shouldEqual(2)

      JSONPath.query("$.address[?(@.id > 2)]", json).as[JsArray].value.size shouldEqual(2)
      JSONPath.query("$.address[?(@.id >= 2)]", json).as[JsArray].value.size shouldEqual(3)

      JSONPath.query("$.address[?(@.state == 'PA')]", json).as[JsArray].value.size shouldEqual(2)
      JSONPath.query("$.address[?(@.city == 'Springfield')]", json).as[JsArray].value.size shouldEqual(1)
      JSONPath.query("$.address[?(@.city != 'Devon')]", json).as[JsArray].value.size shouldEqual(2)
    }

    "boolean filter" in {
      JSONPath.query("$.address[?(@.id > 1 && @.state != 'PA')]", json).as[JsArray].value.size shouldEqual(1)
      JSONPath.query("$.address[?(@.id < 4 && @.state == 'PA')]", json).as[JsArray].value.size shouldEqual(2)
      JSONPath.query("$.address[?(@.id == 4 || @.state == 'PA')]", json).as[JsArray].value.size shouldEqual(3)
      JSONPath.query("$.address[?(@.id == 4 || @.state == 'NJ')]", json).as[JsArray].value.size shouldEqual(1)
    }

    "print tokens" in {
      val jsonPath = "$..book[0][1]"
      val tokens = new Parser().compile(jsonPath).get
      println(tokens.mkString("\n"))

      tokens.isEmpty shouldEqual false
    }
  }

  lazy val ids = Seq(1,2,3,4)
  lazy val tagArray = Seq("programmer", "husband", "father", "golfer")
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