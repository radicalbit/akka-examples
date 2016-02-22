package io.radicalbit.akkarecipes.actors

import akka.actor.Actor.Receive
import akka.actor._
import io.radicalbit.akkarecipes.messages.MakePizza

class PizzaMaker extends Actor {
  override def receive: Receive = {
    case MakePizza(number, timestamp) => {
      println(s"Received an order of $number pizza at $timestamp")
    }
  }
}
