package tests

import org.scalatest.{FlatSpec, Matchers}
import main.StockOrder._
import main.StockOrder
import org.scalatest.{FlatSpec, Matchers, Tag}
import org.scalatest.Assertions._
import scala.collection.mutable


/**
  * Created by Tevyn on 12/07/2016.
  */
class StockOrderTests extends FlatSpec with Matchers{

"When these methods are called " should "add the stock to Stock Order table" in{
  addNewStock("PO00008", 100)
}
}
