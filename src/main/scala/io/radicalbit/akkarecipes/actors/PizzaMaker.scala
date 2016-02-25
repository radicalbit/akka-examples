package io.radicalbit.akkarecipes.actors

import akka.actor.Actor.Receive
import akka.actor._
import io.radicalbit.akkarecipes.messages.MakePizza

class PizzaMaker extends Actor with ActorLogging{

  override def receive: Receive = {
    case MakePizza(number) => {
      log.info("Received order #{}", number)
    }
  }

}
