package hackback

import org.scalatest.FunSuite
import java.io.File
import java.nio.file.Files
import scala.io.Source

/**
 * @author Winash
 */
class QueryParserTests extends FunSuite{


  test("parse just dial API results"){
    println (Source.fromFile("test_data/query.txt").mkString)
  }

}
