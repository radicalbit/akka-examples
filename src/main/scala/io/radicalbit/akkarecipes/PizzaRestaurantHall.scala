package io.radicalbit.akkarecipes

import akka.actor.{ActorSelection, Props, ActorSystem}
import com.typesafe.config.ConfigFactory
import io.radicalbit.akkarecipes.actors.{Customer, PizzaMaker}
import scala.concurrent.duration._

import scala.concurrent.Await

object PizzaRestaurantHall {

  def main(args: Array[String]) {

    val hall: ActorSystem =
      ActorSystem("PizzaRestaurantHall", ConfigFactory.load("PizzaRestaurantHall"))

    val pizzaMaker: ActorSelection =
      hall.actorSelection("akka.tcp://RemoteKitchen@127.0.0.2:4000/user/PizzaMaker")

    val customer = hall.actorOf(Props[Customer](new Customer(pizzaMaker)), "Customer")

    Thread.sleep(60000)

    Await.ready(hall.terminate(), 10 seconds)
    println("The restaurant is closed!")
  }
}
