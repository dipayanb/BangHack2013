package hackback.jd

import com.google.gson.{JsonElement, JsonParser}

/**
 * @author Winash
 */
class QueryResult() {

  var id: String = _
  var jdId: String = _
  var companyName: String = _
  var address: String = _
  var city: String = _
  var pinCode: String = _
  var landLine: String = _
  var mobile: String = _
  var email: String = _
  var lat: String = _
  var lng: String = _
  var avgRating: String = _
  var totalRatings: String = _

}

object QueryResult {

  val parser = new JsonParser

  def apply(json: String) = {
    val jsonElement = parser.parse(json)
    //    jsonElement.get("")


  }

}
