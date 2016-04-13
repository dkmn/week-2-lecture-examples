package com.example.coalmine

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

/**
  * Created by jerryk on 4/12/16.
  */
object GraveDigger {
  val props = Props[GraveDigger]

  case class Bury(miner: ActorRef)
}

class GraveDigger extends Actor with ActorLogging {
  import GraveDigger._

  override def receive: Receive = {
    case Bury(miner) => log.info(s"Oh Danny Boy, Oh Danny Boy, burying $miner")
  }
}
