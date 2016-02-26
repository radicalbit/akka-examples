package io.radicalbit.akkarecipes.actors

import akka.actor._
import io.radicalbit.akkarecipes.messages.{ MakePizza, Pizza }

class PizzaMaker extends Actor with ActorLogging {

  override def receive: Receive = {
    case MakePizza(number) => {
      log.info("Received order #{}", number)
      val p = Pizza(number)
      log.info("{} is ready", p)
    }
  }

}
