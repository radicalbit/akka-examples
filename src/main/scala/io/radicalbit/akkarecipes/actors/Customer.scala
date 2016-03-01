package io.radicalbit.akkarecipes.actors

import akka.actor.Status.Failure
import akka.actor.{ Actor, ActorLogging, ActorRef }
import akka.pattern.{ ask, pipe }
import akka.util.Timeout
import io.radicalbit.akkarecipes.messages.{ Pizza, IssueAnOrder, MakePizza }

import scala.concurrent.Future
import scala.concurrent.duration._

class Customer(pizzaMaker: ActorRef, customerName: String) extends Actor with ActorLogging {

  var orderNumber = 0
  val name = customerName

  implicit val timeout: akka.util.Timeout = Timeout(5 seconds)
  implicit val executionContext = scala.concurrent.ExecutionContext.global

  override def receive: Receive = {
    case IssueAnOrder => {
      orderNumber += 1
      log.info("{} is sending orderNumber #{}", customerName, orderNumber)
      val result: Future[Any] = pizzaMaker ? MakePizza(orderNumber)
      result pipeTo self
    }
    case p @ Pizza(n) => {
      log.info("Eating {}", p)
    }
    case Failure(e) => {
      log.error("{}", e)
    }

  }
}
