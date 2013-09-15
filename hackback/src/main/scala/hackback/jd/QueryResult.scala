package hackback.jd

import com.google.gson.{JsonObject, JsonParser}
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
 * @author Winash
 */
class QueryResult() {

  var _id: String = _
  var jdId: String = _
  var companyName: String = _
  var address: String = _
  var city: String = _
  var pinCode: String = _
  var landLine: String = _
  var mobile: String = _
  var website: String = _
  var email: String = _
  var lat: String = _
  var lng: String = _
  var avgRating: String = _
  var totalRatings: String = _
  var toIndex:String = _


}

object QueryResult {

  val parser = new JsonParser


  def parseText(data: JsonObject, str: String): String = {
    try {
      val string: String = data.get(str).getAsString
      return string
    } catch {
      case e: Exception => ""
    }
  }

  def apply(json: String): List[QueryResult] = {
    val jsonElement = parser.parse(json)
    val jsonArray = jsonElement.getAsJsonArray
    val list = ListBuffer[QueryResult]()
    for (i <- 0 to jsonArray.size - 1) {
      val entry = jsonArray.get(i)
      val queryResult = new QueryResult()
      val data = entry.getAsJsonObject
      queryResult.jdId = parseText(data, "justdial_id")
      queryResult.companyName = parseText(data, "companyname")
      queryResult.address = parseText(data, "address")
      queryResult.city = parseText(data, "city")
      queryResult.pinCode = parseText(data, "pincode")
      queryResult.landLine = parseText(data, "landline")
      queryResult.mobile = parseText(data, "mobile")
      queryResult.email = parseText(data, "email")
      queryResult.website = parseText(data, "website")
      val jsonObject = data.getAsJsonObject("location")
      queryResult.lat = parseText(jsonObject, "lat")
      queryResult.lng = parseText(jsonObject, "lng")
      queryResult.avgRating = parseText(data, "avg_rating")
      queryResult.totalRatings = parseText(data, "total_ratings")
      queryResult.toIndex = queryResult.companyName +" "+ queryResult.address
      list+=queryResult
    }
    list.toList
  }

  def apply(qr: QueryResult): QueryResult = {
    val result: QueryResult = new QueryResult()
    result.address = qr.address
    result.companyName = qr.companyName
    result.avgRating = qr.avgRating
    result.city = qr.city
    result.email = qr.email
    result.jdId = qr.jdId
    result.landLine = qr.landLine
    result.lat = qr.lat
    result.lng = qr.lng
    result.mobile = qr.mobile
    result.pinCode = qr.pinCode
    result.totalRatings = qr.totalRatings
    result.website = qr.website
    result.toIndex = qr.toIndex
    result
  }

}


