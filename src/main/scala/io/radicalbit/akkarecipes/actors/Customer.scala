package io.radicalbit.akkarecipes.actors

import java.util.Date

import akka.actor.{ActorLogging, Actor, ActorRef}
import io.radicalbit.akkarecipes.messages.{MakePizza, IssueAnOrder}

import scala.concurrent.duration._


class Customer(pizzaMaker: ActorRef, customerName: String) extends Actor with ActorLogging {

  var orderNumber = 0
  val name = customerName

  override def receive: Receive = {
    case IssueAnOrder => {
      orderNumber += 1
      log.info("{} is sending orderNumber #{}", customerName, orderNumber)
      pizzaMaker ! MakePizza(orderNumber)
    }
  }
}
