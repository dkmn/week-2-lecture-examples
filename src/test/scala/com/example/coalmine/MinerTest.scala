package com.example.coalmine

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.example.coalmine.Miner.{DoValuableWork, WorkDone}
import org.scalatest._

import scala.util.Random

class MinerTest (_system: ActorSystem) extends TestKit(_system)
                                        with ImplicitSender
                                        with WordSpecLike
                                        with MustMatchers
                                        with BeforeAndAfterAll {

  def this() = this(ActorSystem("MinerTestActorSystem"))

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  ///////////////////////////////////////////////////////////////////////////
  //
  //        T  E  S  T      C  A  S  E  S  .  .  .
  //
  //  There's not a lot to see in here... Feel free to ignore this section
  //  for this week.  We will flesh more of this stuff out when we revisit
  //  supervision in detail, and look at actor testing a bit more
  //  systematically.
  //
  ///////////////////////////////////////////////////////////////////////////

  "Telling a miner to do work a bunch of times" must {
    "cause some work to get done and some mine disasters" in {
      // Use a test actor ref for single threaded testing
      val testActorRef = TestActorRef.create(system, Props[Miner], "testMiner")
      var sawAThrow = false
      var sawWorkDone = false

      for { i <- 1 to 10 } {
        try {
          // Stuff a message directly into the actor's receive behavior;
          // exceptions will be visible to us, and we could even do
          // become/unbecome if necessary.
          testActorRef.receive(DoValuableWork)
          sawWorkDone = true
        } catch {
          case e: MineCaveInException => sawAThrow = true
        }
      }

      sawAThrow mustEqual true
      sawWorkDone mustEqual true
    }
  }

}
