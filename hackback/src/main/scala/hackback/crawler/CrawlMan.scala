package hackback.crawler

import akka.actor.{ActorRef, Props, Actor, ActorSystem}
import utils.Utils
import hackback.BingTranslator
import scala.concurrent.duration._
import com.hackback.service.HackbackService
import hackback.jd.QueryResult
import com.hackback.core.CrawlResult

/**
 * @author Winash
 */

class CrawlMan {

  val actorSystem = ActorSystem()
  private val hackbackService: HackbackService = new HackbackService
  private val dbDumper = actorSystem.actorOf(Props(classOf[DBDumper], hackbackService))
  private val translateActor = actorSystem.actorOf(Props(classOf[TranslateActor], hackbackService, dbDumper))
  private val jdActor = actorSystem.actorOf(Props(classOf[PullFromUrlActor], hackbackService, translateActor, dbDumper))
  private val starter = actorSystem.actorOf(Props(classOf[Starter], hackbackService))
  actorSystem.scheduler.scheduleOnce(5 minutes, starter, Start())

}


class PullFromUrlActor(val service: HackbackService, val translator: ActorRef, val dbDumper: ActorRef) extends Actor {

  val justDialURL = "http://hack2013.justdial.com/index.php"
  val token = "Pg7lyRku2CT6cCc"
  val eventToken = "R1nev3n7t0k3nd0m"

  def receive = {
    case PullData(query, city, latLng, queryId) =>
      val json = service.invokeAPI(null, query, city, null, latLng)
      val queryResults = QueryResult(json)
      translator ! TranslateData(queryResults, queryId)
      queryResults.foreach {
        r =>
          dbDumper ! DumpData(r, queryId, "en")

      }
  }
}


class TranslateActor(val service: HackbackService, val dbDumper: ActorRef) extends Actor {
  val translator = new BingTranslator

  def receive = {
    case TranslateData(qr, queryId) =>
      qr.foreach {
        r =>
          val translated = translator.translate(r)
          dbDumper ! DumpData(translated, queryId, "hi")
      }

  }
}

class DBDumper(val service: HackbackService) extends Actor {
  def receive = {
    case DumpData(qr, queryId, lang) =>
      val cr = Mapper(qr, queryId, lang)
      service.dumpCrawlData(cr)

  }
}

class Starter(val service: HackbackService) extends Actor {
  def receive = {
    case Start =>

  }
}

object Mapper {
  def apply(qr: QueryResult, qid: String, languageCode: String): CrawlResult = {
    val crawlResult = new CrawlResult
    crawlResult.address = qr.address
    crawlResult.city = qr.city
    crawlResult.avgRating = qr.avgRating
    crawlResult.companyName = qr.companyName
    crawlResult.email = qr.email
    crawlResult.justDialId = qr.jdId
    crawlResult.landLine = qr.landLine
    crawlResult.lat = qr.lat
    crawlResult.lng = qr.lng
    crawlResult.mobile = qr.mobile
    crawlResult.pinCode = qr.pinCode
    crawlResult.totalRatings = qr.totalRatings
    crawlResult.website = qr.website
    crawlResult.queryId = qid
    crawlResult.languageCode = languageCode
    crawlResult
  }
}

