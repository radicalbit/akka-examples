package io.radicalbit.akkarecipes

package object messages {

  case object IssueAnOrder
  case class MakePizza(number: Int)

}
