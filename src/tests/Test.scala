package tests

import java.sql.Date

import main.Order
import main.Order.OrderDescription
import org.scalatest.{FlatSpec, Matchers, OptionValues}
import org.scalatest.Assertions._


import scala.collection.mutable

/**
  * Created by Tevyn on 01/07/2016.
  */
class Test extends  FlatSpec with Matchers with OptionValues {

  "When an Order ID is requested to be found through Order it" should "print a queue of current orders" in {

   val ord1 = new OrderDescription("OD0001", new Date(2016,9,12), 8.5, "John Smith", "CO0007", "1, The Street, Somewhere, ZZ12 9AB", "Dispatched", "None")
   //val orderDesc = Order.findByID("OD0001", Order.orders)
    val ordMatch ="OD0001"

    assert(ordMatch.equals(ord1))
    }

  it should "throw a NullPointerException if there is no details in the database regarding OrderID" in {

    intercept[NoSuchElementException]{

}
  }

}
