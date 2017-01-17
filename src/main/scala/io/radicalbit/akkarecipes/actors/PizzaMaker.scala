package io.radicalbit.akkarecipes.actors

import java.util.Date

import akka.actor._
import akka.persistence.{ SnapshotOffer, PersistentActor }
import io.radicalbit.akkarecipes.messages.{ YouOweMe, HowMuch, MakePizza, Pizza }

case class Counter(number: Int)

case class PizzaMadeEvent(pizzaOrder: Int, time: Date)

class PizzaMaker extends PersistentActor with ActorLogging {

  var counter = Counter(0)
  val euro = 3

  override def persistenceId: String = "PizzaMaker1"

  override def receiveRecover: Receive = {
    case event @ PizzaMadeEvent(pizzaOrder, time) => {
      counter = Counter(counter.number + 1)
      log.info("Recover with event {}", event)
    }
    case SnapshotOffer(metadata, data: Counter) => {
      log.info("Snapshot offer with data {}", data)
      counter = data
    }
  }

  override def receiveCommand: Receive = {
    case MakePizza(number) => {
      val pizzaMade = PizzaMadeEvent(number, new Date)
      persist(pizzaMade) { (event: PizzaMadeEvent) =>
        log.info("Saved event {}", event)
        log.info("Received order #{}", number)
        Thread.sleep(500)
        val p = Pizza(number)
        sender ! p
        counter = Counter(counter.number + 1)
        log.info("{} is ready", p)
      }
    }
    case HowMuch => {
      saveSnapshot(counter)
      log.info("Saving snapshot {}", counter)
      val oweMe = YouOweMe(counter.number * euro)
      log.info("Making total.. {}", oweMe)
      sender ! oweMe
    }
  }

}
