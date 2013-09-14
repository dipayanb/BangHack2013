package hackback

import hackback.jd.QueryResult

/**
 * @author Winash
 */
trait Translator {
  def translate(obj:QueryResult):QueryResult
}