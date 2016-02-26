package io.radicalbit.akkarecipes

import akka.actor.{ Props, ActorSystem }
import com.typesafe.config.ConfigFactory
import io.radicalbit.akkarecipes.actors.{ Customer, PizzaMaker }
import io.radicalbit.akkarecipes.messages.IssueAnOrder
import scala.concurrent.duration._

import scala.concurrent.{ ExecutionContextExecutor, Await }

object SimplePizzaRestaurant {

  def main(args: Array[String]) {

    val config = ConfigFactory.load("SimplePizzaRestaurant")

    val system = ActorSystem("SimplePizzaRestaurant", config)

    val pizzaMaker = system.actorOf(Props[PizzaMaker], "PizzaMaker")

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
