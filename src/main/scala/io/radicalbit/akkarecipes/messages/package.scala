package io.radicalbit.akkarecipes

package object messages {

  case object IssueAnOrder

  case class MakePizza(number: Int)
  case class Pizza(number: Int)

  case class Pizza(number: Int)

}
