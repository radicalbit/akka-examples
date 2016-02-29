package io.radicalbit.akkarecipes.actors

import java.util.Date

import akka.actor._
import akka.persistence.PersistentActor
import io.radicalbit.akkarecipes.messages.{ YouOweMe, HowMuch, MakePizza, Pizza }

case class Counter(number: Int)

case class MakePizzaEvent(pizzaOrder: Int, time: Date)

class PizzaMaker extends PersistentActor with ActorLogging {

  var counter = Counter(0)
  val euro = 3

  override def persistenceId: String = "PizzaMaker1"

  override def receiveRecover: Receive = {
    case event @ MakePizzaEvent(pizzaOrder, time) => {
      counter = Counter(counter.number + 1)
      log.info("Recover with event {}", event)
    }
  }

  override def receiveCommand: Receive = {
    case MakePizza(number) => {
      val makePizzaEvent = MakePizzaEvent(number, new Date)
      persist(makePizzaEvent) { (event: MakePizzaEvent) =>
        log.info("Saved event {}", makePizzaEvent)
        log.info("Received order #{}", number)
        Thread.sleep(500)
        val p = Pizza(number)
        sender ! p
        counter = Counter(counter.number + 1)
        log.info("{} is ready", p)
      }
    }
    case HowMuch => {
      val oweMe = YouOweMe(counter.number * euro)
      log.info("Making total.. {}", oweMe)
      sender ! oweMe
    }
  }

}
