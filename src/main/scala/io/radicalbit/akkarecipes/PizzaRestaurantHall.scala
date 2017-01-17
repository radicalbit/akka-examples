package io.radicalbit.akkarecipes

import akka.actor.{ActorRef, ActorSelection, ActorSystem, Props}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import io.radicalbit.akkarecipes.actors.Customer
import io.radicalbit.akkarecipes.messages.IssueAnOrder

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

object PizzaRestaurantHall {

  def main(args: Array[String]) {

    val config = ConfigFactory.load("PizzaRestaurantHall")

    val system = ActorSystem("SimplePizzaRestaurant", config)

    val pizzaMakerSelection: ActorSelection =
      system.actorSelection("akka.tcp://RemoteKitchen@127.0.0.2:4000/user/PizzaMaker")

    implicit val timeout = new Timeout(10 seconds)
    val eventualActorRef: Future[ActorRef] = pizzaMakerSelection.resolveOne()
    val pizzaMaker: ActorRef = Await.result(eventualActorRef, 10 seconds)

    val customerName = config.getString("pizzarestaurant.people.customerName")
    val customer = system.actorOf(Props[Customer](new Customer(pizzaMaker, customerName)), "Customer")

    implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

    val tick = system.scheduler.schedule(500 millis, 2000 millis, customer, IssueAnOrder)

    Thread.sleep(30000)

    tick.cancel()
    Await.ready(system.terminate(), 10 seconds)
    println("The restaurant is closed!")
  }
}
