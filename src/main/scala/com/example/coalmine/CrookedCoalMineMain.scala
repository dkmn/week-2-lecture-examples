package com.example.coalmine

import akka.actor.{Actor, ActorLogging, Props}
import akka.actor.Actor.Receive
import com.example.coalmine.AmoralMineCeo.FoundMiningCompany

/**
  * Created by jerryk on 4/12/16.
  */
class CrookedCoalMineMain extends Actor with ActorLogging {
  val ceo = context.actorOf(AmoralMineCeo.props, "Amoral-Mine-CEO")


  log.info("Starting up CrookedCoalMine...")

  ceo ! FoundMiningCompany      // Tell terrible CEO to start the company

  override def receive: Receive = {
    case _ =>
  }
}
