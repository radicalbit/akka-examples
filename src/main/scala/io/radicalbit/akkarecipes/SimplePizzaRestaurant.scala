package io.radicalbit.akkarecipes

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory
import io.radicalbit.akkarecipes.actors.{Customer, PizzaMaker}
import scala.concurrent.duration._

import scala.concurrent.Await

object SimplePizzaRestaurant {

  def main(args: Array[String]) {

    val system = ActorSystem("SimplePizzaRestaurant", ConfigFactory.load("SimplePizzaRestaurant"))
    val pizzaMaker = system.actorOf(Props[PizzaMaker], "PizzaMaker")
    val customer = system.actorOf(Props[Customer](new Customer(pizzaMaker)), "Customer")

    Thread.sleep(30000)

    Await.ready(system.terminate(), 10 seconds)
    println("Terminated")
  }
}
