package io.radicalbit.akkarecipes.actors

import java.util.Date

import akka.actor.{Actor, ActorRef}
import io.radicalbit.akkarecipes.messages.{MakePizza, Order}

import scala.concurrent.duration._



class Customer(pizzaMaker: ActorRef) extends Actor {

  implicit val dispatcher = context.dispatcher

  val tick =
    context.system.scheduler.schedule(1000 millis, 2000 millis, self, Order)

  var number = 0;

  override def postStop() = tick.cancel()

  override def receive: Receive = {
    case Order => {
      pizzaMaker ! MakePizza(number % 10 + 1, new Date)
      number += 1
    }
  }
}
