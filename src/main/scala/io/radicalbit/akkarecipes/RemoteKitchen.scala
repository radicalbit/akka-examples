package io.radicalbit.akkarecipes

import akka.actor.{ Props, ActorSystem }
import com.typesafe.config.ConfigFactory
import io.radicalbit.akkarecipes.actors.PizzaMaker

object RemoteKitchen {

  def main(args: Array[String]) {
    val kitchen = ActorSystem("RemoteKitchen", ConfigFactory.load("RemoteKitchen"))

    val pizzaMaker = kitchen.actorOf(Props[PizzaMaker], "PizzaMaker")
  }

}
