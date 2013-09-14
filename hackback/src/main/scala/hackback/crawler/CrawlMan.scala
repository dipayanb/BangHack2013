package hackback.crawler

import akka.actor.{ActorRef, Props, Actor, ActorSystem}
import utils.Utils
import hackback.BingTranslator
import scala.concurrent.duration._

/**
 * @author Winash
 */

class CrawlMan {

  val actorSystem = ActorSystem()
  private val jdActor = actorSystem.actorOf(Props(classOf[PullFromUrlActor]))
  private val translateActor = actorSystem.actorOf(Props(classOf[TranslateActor]))
  private val dbDumper = actorSystem.actorOf(Props(classOf[DBDumper]))
  private val starter = actorSystem.actorOf(Props(classOf[Starter]))
  actorSystem.scheduler.scheduleOnce(5 minutes, starter, Start())

}


class PullFromUrlActor extends Actor {

  val justDialURL = "http://hack2013.justdial.com/index.php"
  val token = "Pg7lyRku2CT6cCc"
  val eventToken = "R1nev3n7t0k3nd0m"

  def receive = {
    case PullData(query, lat, lng) =>
      Utils.makeGetRequest(justDialURL + "?")
  }
}


class TranslateActor extends Actor {
  val translator = new BingTranslator

  def receive = {
    case TranslateData(qr) =>

  }
}

class DBDumper extends Actor {
  def receive = {
    case DumpData(qr) =>
  }
}

class Starter extends Actor {
  def receive = {
    case Start =>

  }
}

