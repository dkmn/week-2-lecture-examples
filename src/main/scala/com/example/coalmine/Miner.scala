package com.example.coalmine

import akka.actor.{Actor, ActorLogging, Props}

import scala.util.Random

/**
  * Actor representing a poor, beleaguered coal miner.
  */
object Miner {
  def props = Props[Miner]

  // Messages a Miner introduces
  case object DoValuableWork
  case class WorkDone(dollarValue: Int)
}


class Miner extends Actor with ActorLogging {
  import Miner._

  def random = new Random()

  override def receive: Receive = {
    case DoValuableWork =>
      // Randomly either send back WorkDone of random value or die tragically
      random.nextDouble() match {
        case v if v < 0.70 =>
          val valueOfWork = random.nextInt(100)
          log.info(s"$self produced $valueOfWork worth of work")
          sender ! WorkDone(valueOfWork)
        case _ =>
          log.error(s"The mine is collapsing! I, $self, am doomed!")
          throw new MineCaveInException("AAIIIEEEEE!") // Simulate messy end!
      }
  }
}

class MineCaveInException(s: String) extends Exception
