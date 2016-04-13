package com.example.counters

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive

/**
  * Created by jerryk on 4/11/16.
  */
class CounterMain extends Actor {

  val counter = context.actorOf(Props[CounterButMoreFunctionalish],
                                "well_named_counter")

  1 to 3 foreach { _ => counter ! "increment" }
  counter ! "get"

  override def receive: Receive = {

    case n: Int => println(s"Received integer $n")
                   context.stop(self)
  }
}
