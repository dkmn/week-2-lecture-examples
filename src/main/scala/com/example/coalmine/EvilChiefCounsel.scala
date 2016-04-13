package com.example.coalmine

import akka.actor.{Actor, ActorLogging, Props}
import com.example.coalmine.EvilChiefCounsel.DoWhateverYouDoEsquire

object EvilChiefCounsel {
  val props = Props[EvilChiefCounsel]

  case object DoWhateverYouDoEsquire
}

class EvilChiefCounsel extends Actor with ActorLogging {

  override def receive: Receive = {
    case DoWhateverYouDoEsquire => log.info("We chief counsels are well paid.")
  }

}
