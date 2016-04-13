package com.example.counters

import akka.actor.Actor

/**
  * TODO: Comment this and slap in a unit test...
  */
class CounterWithAlternateBehavior extends Actor {

  def counter(n: Int): Receive = {
    case "increment" => context.become(counter(n+1))
    case "get" => sender ! n
    case ("become_blabber", phrase: String) => context.become(blabber(phrase))
  }

  def blabber(phrase: String): Receive = {
    case "get" => sender ! phrase
    case ("become_counter", n: Int) => context.become(counter(n))
  }

  // Initial behavior is a counter with value zero...
  def receive = counter(0)

}
