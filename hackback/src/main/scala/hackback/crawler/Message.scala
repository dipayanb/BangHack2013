package hackback.crawler

import hackback.jd.QueryResult

/**
 * @author Winash
 */
sealed class Message

case class PullData(query: String, lat: String, lng: String) extends Message
case class TranslateData(qr:QueryResult) extends  Message
case class DumpData(qr:QueryResult) extends Message
case class Start() extends Message

