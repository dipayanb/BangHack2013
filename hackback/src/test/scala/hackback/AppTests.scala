package hackback

import org.scalatest.FunSuite
import java.io.File
import java.nio.file.Files
import scala.io.Source
import hackback.jd.QueryResult

/**
 * @author Winash
 */
class AppTests extends FunSuite {

  test("parse just dial API results") {
    val json = Source.fromFile("test_data/query.txt").mkString
    val result: List[QueryResult] = QueryResult(json)
    assert(result.size == 100)
  }

  test("test translate request generation") {
       val testJson = """ {"080PXX80.XX80.110526175210.M3Z8":{"companyname":"The Orchard","address":"No 15, 2nd Flr, Lower Palace Orchard,  Nxt Shell Petrol Pump, Sadashiva nagar, Bangalore- 560080","city":"Bangalore","pincode":"560080","landline":"+(91)-80-40909337","mobile":"+(91)-9036577672,9901900207","email":"bands.hospitality@gmail.com,ranjit.bijoor@yahoo.com","website":"","latitude":"13.003160000000","longitude":"77.583649000000","avg_rating":"4.0","total_ratings":366}}"""
       val result = QueryResult(testJson)
       val qr = new BingTranslator().translate(result.head)
    println(qr)


  }

}
