package io.radicalbit.akkarecipes

import akka.actor.{ ActorSelection, Props, ActorSystem }
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import io.radicalbit.akkarecipes.actors.{ Customer, PizzaMaker }
import io.radicalbit.akkarecipes.messages.IssueAnOrder
import scala.concurrent.{ Await, ExecutionContextExecutor }
import scala.concurrent.duration._

object PizzaRestaurantHall {

  def main(args: Array[String]) {

    val config = ConfigFactory.load("PizzaRestaurantHall")

    val system = ActorSystem("SimplePizzaRestaurant", config)

    val pizzaMakerSelection: ActorSelection =
      system.actorSelection("akka.tcp://RemoteKitchen@127.0.0.2:4000/user/PizzaMaker")

    implicit val timeout = new Timeout(10 seconds)
    val eventualActorRef = pizzaMakerSelection.resolveOne()
    val pizzaMaker = Await.result(eventualActorRef, 10 seconds)

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
