package tests

import main.Stock
import org.scalatest.{FlatSpec, Matchers}
import main.Stock._
import org.scalatest.Assertions._

import scala.collection.mutable
/**
  * Created by Tevyn on 12/07/2016.
  */
class StockTests extends FlatSpec with Matchers{


  "Running the method to" should "get the list of stock from  the database" in {

    Stock.populateEntity(Stock) == null shouldEqual false
  }

  it should "decrement the stock from the database by specifying the order ID" in {
    decrementStock("OD0001")
  }
}
