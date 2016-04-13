package com.example.counters

import akka.actor.Actor

/**
  * A more functional Counter.
  */
class CounterButMoreFunctionalish extends Actor {

  def counter(n: Int): Receive = {
    case "increment" => context.become(counter(n+1))
    case "get" => sender ! n
  }

  def receive = counter(0)
}
