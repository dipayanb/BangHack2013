package hackback.crawler

import akka.actor.{Actor, ActorSystem}
import utils.Utils

/**
 * @author Winash
 */

class CrawlMan {

  val actorSystem = ActorSystem()

}


class PullFromUrlActor extends Actor {

  val justDialURL = "http://hack2013.justdial.com/index.php"
  val token = "Pg7lyRku2CT6cCc"
  val eventToken = "R1nev3n7t0k3nd0m"

  def receive = {
    case PullData(query, lat, lng) =>
       Utils.makeGetRequest(justDialURL+"?")
  }
}
