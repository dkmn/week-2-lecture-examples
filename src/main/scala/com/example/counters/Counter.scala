package com.example.counters

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive

/**
  * Example counter with mutable state encapsulated.
  */
class Counter extends Actor {
  // NOTE: Outsiders don't access this state directly. If they want to inspect
  //       it or change it, they have to send an appropriate message...
  var countValue = 0

  override def receive: Receive = {
    case "increment" => countValue += 1
    case "get" => sender ! countValue
    case ("pass_on", interestedParty: ActorRef) => interestedParty ! countValue
  }
}
