import com.josephpconley.jsonpath.JSONPath
import play.api.libs.json.Json

val store = Json.parse("""{"store":{"book":[{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95},{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99},{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99},{"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}],"bicycle":{"color":"red","price":19.95}}}""")
JSONPath.query("$.store.book[*].author", store)
JSONPath.query("$..author", store)
JSONPath.query("$.store.*", store)
JSONPath.query("$.store..price", store)
JSONPath.query("$..book[2]", store)
JSONPath.query("$..book[-1]", store)
JSONPath.query("$..book[0,1]", store)
JSONPath.query("$..book[:2]", store)
JSONPath.query("$..book[?(@.isbn)]", store)
JSONPath.query("$..book[?(@.price<10)]", store)
JSONPath.query("$..*", store)