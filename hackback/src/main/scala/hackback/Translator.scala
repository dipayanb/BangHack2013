package hackback

import hackback.jd.QueryResult

/**
 * @author Winash
 */
trait Translator {
  def translate(obj:QueryResult):QueryResult
}



object Languages extends Enumeration{
  type Languages = Value
  val Eng,Hin = Value
}
