package com.example.coalmine

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.example.coalmine.AmoralMineCeo.GetTheMinionsWorking
import com.example.coalmine.ChiefGraveDigger.StaffCemetery
import com.example.coalmine.CrookedForeman.{BerateMiners, DeathNotice, HireMiners}
import com.example.coalmine.Miner.WorkDone

object MineBoss {
  val props = Props[MineBoss]

  case object StaffMine
}

class MineBoss extends Actor with ActorLogging {
  import MineBoss._

  var crookedForeman: ActorRef = null
  var chiefGraveDigger: ActorRef = null

  override def receive: Receive = {

    case StaffMine =>
      log.info("It feels good to be a mine boss, hiring people...")
      log.info("What I really need is a loyal, crooked foreman!")
      crookedForeman = context.actorOf(CrookedForeman.props,
                                       "Crooked-Foreman")
      crookedForeman ! HireMiners

      log.info("Tough business, I may need someone to bury bodies...")
      chiefGraveDigger = context.actorOf(ChiefGraveDigger.props,
                                         "Chief-Gravedigger")
      chiefGraveDigger ! StaffCemetery

    case GetTheMinionsWorking =>
      crookedForeman ! BerateMiners

    case WorkDone(v) =>
      context.parent forward WorkDone(v)

    case d@DeathNotice(miner) => chiefGraveDigger ! d
  }
}
