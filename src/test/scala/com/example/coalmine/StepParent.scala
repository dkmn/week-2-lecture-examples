package com.example.coalmine

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

/**
  * Created by jerryk on 4/12/16.
  */
class StepParent(target: ActorRef) extends Actor {

  override val supervisorStrategy = OneForOneStrategy() {
    case thr: Throwable => target !thr
                           Restart
  }

  def receive = {
    case p: Props => sender ! context.actorOf(p, "child")
  }
}
