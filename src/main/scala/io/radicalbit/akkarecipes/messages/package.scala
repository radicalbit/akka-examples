package io.radicalbit.akkarecipes

import java.util.Date

package object messages {

  case object Order
  case class MakePizza(number: Int, timeStamp : Date)

}
