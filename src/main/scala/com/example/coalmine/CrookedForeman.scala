package com.example.coalmine

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, Terminated}
import com.example.coalmine.Miner.{DoValuableWork, WorkDone}

/**
  * Created by jerryk on 4/12/16.
  */

object CrookedForeman {
  val props = Props[CrookedForeman]

  case object HireMiners
  case object BerateMiners
  case class DeathNotice(miner: ActorRef)
}

class CrookedForeman extends Actor with ActorLogging {
  import CrookedForeman._

  // We'll learn more about supervisor strategies next week... for now we
  // install this one so that dead children stay dead, to make our point.
  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => Stop
  }

  override def receive: Receive = {

    case HireMiners => 1 to 5 foreach { i => {
                                        val miner = context.actorOf(Miner.props,
                                          s"Miner-no-$i")
                                        log.info(s"Hiring miner $i")
                                        context.watch(miner) }
                                      }

    case BerateMiners =>
      val numMiners = context.children.size
      log.info(s"I have $numMiners miner children, at your service! Berating!")
      context.children.foreach { miner => miner ! DoValuableWork }

    case WorkDone(v) => context.parent forward WorkDone(v)

    case Terminated(miner) =>
      log.info(s"Oh dear, $miner has died!")
      context.parent ! DeathNotice(miner)
  }
}
