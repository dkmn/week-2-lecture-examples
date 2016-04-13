package com.example.coalmine

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.example.coalmine.EvilChiefCounsel.DoWhateverYouDoEsquire
import com.example.coalmine.MineBoss.StaffMine
import com.example.coalmine.Miner.WorkDone

import scala.concurrent.duration._

/**
  * Actor representing a horrible coal mine CEO.
  */
object AmoralMineCeo {
  val props = Props[AmoralMineCeo]

  case object FoundMiningCompany
  case object GetTheMinionsWorking
}


class AmoralMineCeo extends Actor with ActorLogging {
  import AmoralMineCeo._
  import context.dispatcher

  var netWorth                    = 0
  var mineBoss: ActorRef          = null
  var evilChiefCounsel: ActorRef  = null

  override def receive: Receive = {

    case FoundMiningCompany =>
      log.info("As CEO I am so excited to start this great venture!")

      mineBoss = context.actorOf(MineBoss.props, "Mine-Boss")
      mineBoss ! StaffMine

      evilChiefCounsel = context.actorOf(EvilChiefCounsel.props,
                                         "Evil-Chief-Counsel")
      evilChiefCounsel ! DoWhateverYouDoEsquire

      log.info("Subject to my greed and whims, I'll regularly demand work")
      context.system.scheduler.schedule(1.second,  // After this delay...
                                        1.second,  // Every this interval...
                                        mineBoss,  // Tell this actor...
                                        GetTheMinionsWorking) // This message.

    case WorkDone(v) =>
      val oldNetWorth = netWorth
      netWorth += v
      log.info(s"CEO net worth just rose from $oldNetWorth by $v to $netWorth")
  }
}
