package com.example

import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

object ApplicationMain extends App {
  val system = ActorSystem("Week2DemoActorSystem")

  // This example app will ping pong 3 times and thereafter terminate the ActorSystem -
  // see counter logic in PingActor
  Await.result(system.whenTerminated, Duration(10, SECONDS))
}
