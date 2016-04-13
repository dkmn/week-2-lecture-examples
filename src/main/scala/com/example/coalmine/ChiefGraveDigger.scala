package com.example.coalmine

import akka.actor.{Actor, ActorLogging, Props}
import com.example.coalmine.CrookedForeman.DeathNotice
import com.example.coalmine.GraveDigger.Bury

/**
  * Created by jerryk on 4/12/16.
  */
object ChiefGraveDigger {
  val props = Props[ChiefGraveDigger]

  case object StaffCemetery
}

class ChiefGraveDigger extends Actor with ActorLogging {
  import ChiefGraveDigger._

  override def receive: Receive = {

    case StaffCemetery =>
      1 to 5 foreach
        { i => { context.actorOf(GraveDigger.props, s"Gravedigger-$i")
                 log.info(s"Hired Gravedigger $i!") } }

    case d@DeathNotice(miner) =>
      context.children.head ! Bury(miner) // The chief's a bad manager!
  }
}
