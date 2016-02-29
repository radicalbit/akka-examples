package io.radicalbit.akkarecipes.actors

import akka.actor._
import io.radicalbit.akkarecipes.messages.{ YouOweMe, HowMuch, MakePizza, Pizza }

case class Counter(number: Int)

class PizzaMaker extends Actor with ActorLogging {

  var counter = Counter(0)
  val euro = 3

  override def receive: Receive = {
    case MakePizza(number) => {
      log.info("Received order #{}", number)
      Thread.sleep(500)
      val p = Pizza(number)
      sender ! p
      counter = Counter(counter.number + 1)
      log.info("{} is ready", p)
    }
    case HowMuch => {
      val oweMe = YouOweMe(counter.number * euro)
      log.info("Making total.. {}", oweMe)
      sender ! oweMe
    }
  }

}
