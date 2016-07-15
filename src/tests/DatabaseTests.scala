package tests

import main.{Order, OrderItem, Stock, StockOrder}
import org.scalatest.{FlatSpec, Matchers, Tag}
import org.scalatest.Assertions._

import scala.collection.mutable

/**
  * Created by Tevyn on 10/07/2016.
  */
class DatabaseTests extends FlatSpec with Matchers {

  it should "get a queue of orders from order table" taggedAs OrderDataSuccess in{
    Order.populateEntity(Order) != null
  }
  it should "get a queue of orders and their associated products from order items table" taggedAs OrderItemDataSuccess in{
   OrderItem.populateEntity(OrderItem) != null
  }
  it should "get a queue of stock from the stock table in the database" taggedAs StockDataSuccess in{
    Stock.populateEntity(Stock) != null
  }
  it should "get a queue of stock orders from the stock order table in the database" taggedAs StockOrderDataSuccess in{
    StockOrder.populateEntity(StockOrder) != null
  }

  object OrderDataSuccess extends Tag("OrderDataSuccess")
  object OrderItemDataSuccess extends Tag("OrderItemDataSuccess")
  object StockDataSuccess extends Tag("StockDataSuccess")
  object StockOrderDataSuccess extends Tag("StockOrderDataSuccess")

}
