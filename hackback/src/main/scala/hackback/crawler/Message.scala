package hackback.crawler

/**
 * @author Winash
 */
sealed class Message

case class PullData(query: String, lat: String, lng: String)
