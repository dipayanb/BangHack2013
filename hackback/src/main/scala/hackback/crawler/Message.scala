package hackback.crawler

import hackback.jd.QueryResult

/**
 * @author Winash
 */
sealed class Message

case class PullData(query: String,city:String, latLng: String,queryId:String) extends Message
case class TranslateData(qr:List[QueryResult],queryId:String) extends  Message
case class DumpData(qr:QueryResult,queryId:String,languageCode:String) extends Message
case class Start() extends Message

