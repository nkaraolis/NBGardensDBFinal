package tests
import main.Order
import main.Order._
import org.scalatest.{FlatSpec, Matchers, Tag}
import org.scalatest.Assertions._
import scala.collection.mutable

/**
  * Created by Tevyn on 10/07/2016.
  * Testing Order use cases
  */
class TestOrders extends FlatSpec with Matchers {

 // Find orders by orderID

  "When an user wants to see the orders" should "get the list of orders from the database" taggedAs OrderDataSuccess in {

      Order.populateEntity(Order) != null shouldEqual true
    }

  //test if items empty

  it should "find an order using OrderID" taggedAs OrderIDSuccess in{
  Order.findByID("OD0001", Order.orders).isEmpty shouldEqual false
  }
//test  if correct collection
  it should "have the correct order list " taggedAs OrderIDListSuccess in{
    Order.findByID("OD0001", Order.orders).head.orderID shouldEqual "OD0001"
  }

  // Test if staff can be allocated to orders
 it should "update the order table in the database with the correct staff ID when staff want to be assigned to order " in {
    assignStaff("OD0001", "ST0001")
  }

  //test the update of order status
  it should "update the order table with the order status of the order" in {
    updateOrderStatus("OD0001", "Dispatched")
  }

  //notify accounts
  it should "select all of the orders from the database where the status is dispatched " in{
   notifyAccounts()
    Order.accountsOrders.isEmpty shouldEqual false
    }

  it should "have a list of orders that have a status dispatched and only dispatched " in{
    Order.accountsOrders.head.orderID.isEmpty shouldEqual false
    for(n<- accountsOrders){
      if(accountsOrders.head.status == "Dispatched"){
        true
      }
    }
  }

  object OrderDataSuccess extends Tag("OrderDataSuccess")
  object OrderIDSuccess extends Tag("OrderIDSuccess")
  object OrderIDListSuccess extends Tag("OrderIDListSuccess")


}
