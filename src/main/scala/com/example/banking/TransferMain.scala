package com.example.banking

import akka.actor.{Actor, Props}
import akka.event.LoggingReceive

/**
  * Teaching example for using a multi-actor system to solve a classic
  * sample problem for locks-based concurrency.
  *
  * This example is originally due to Roland Kuhn, with slight modifications
  * for our purposes.
  */
class TransferMain extends Actor {

  val accountA = context.actorOf(Props[BankAccount], "accountA")
  val accountB = context.actorOf(Props[BankAccount], "accountB")

  accountA ! BankAccount.Deposit(100)

  def receive = LoggingReceive {
    case BankAccount.Done => transfer(75)
  }

  def transfer(amount: BigInt): Unit = {
    val transaction = context.actorOf(Props[WireTransfer], "transfer")
    transaction ! WireTransfer.Transfer(accountA, accountB, amount)
    context.become(LoggingReceive {
      case WireTransfer.Done =>
        println("success")
        context.stop(self)
      case WireTransfer.Failed =>
        println("failed")
        context.stop(self)
    })
  }
}