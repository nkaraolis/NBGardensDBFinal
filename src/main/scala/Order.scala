/**
  * Created by Nick on 28/06/2016.
  */

import java.sql.{DriverManager}
import scalafx.collections.ObservableBuffer
import scala.collection.mutable.Queue
import scalafx.beans.property.{ObjectProperty, StringProperty}

//Represents customer orders in the system
object Order extends Entity {

  //Set the table name to use the orders table
  var dbTableName = "orders"

  //Column names for the orders table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("OrderID", "Date", "Price", "CustomerName", "CustomerID", "Address", "Status", "StaffAssigned")

  //Column types for the orders table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string", "double", "string", "string", "string", "string", "string")

  //Collection of orders
  var orders = new ObservableBuffer[OrderDescription]

  //Collection of orders for the accounts department
  var accountsOrders = new ObservableBuffer[OrderDescription]

  //Case class to represent one order
  class OrderDescription(orderID: String, date: String, price: Double, customerName: String, customerID: String, address: String, status: String, staff: String) extends EntityDescription {
    override var entityID: String = orderID
    val ordID = new StringProperty(this, "Order ID", orderID)
    val ordDate = new StringProperty(this, "Date", date)
    val ordPrice = new ObjectProperty(this, "Price", price)
    val custID = new StringProperty(this, "Customer ID", customerID)
    val custName = new StringProperty(this, "Customer Name", customerName)
    val custAddress = new StringProperty(this, "Customer Address", address)
    val ordstatus = new StringProperty(this, "Order Status", status)
    val staffassigned = new StringProperty(this, "Staff Assigned", staff)
  }

  //Create a new orderDescription (order row) and add it to the collection of orders
  override def createEntity(): Unit = {
    var addNewOrder = new OrderDescription(stringParams(0), stringParams(1), doubleParams(0), stringParams(2), stringParams(3), stringParams(4), stringParams(5), stringParams(6))
    orders += addNewOrder
  }

  //Assign a staff member to an order
  def assignStaff(ordID: String, staffMember: String): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      statement.executeUpdate("UPDATE " + dbTableName + " SET staffAssigned='" + staffMember + "' WHERE OrderID = '" + ordID + "'")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    orders.clear()
    this.populateEntity(this)
  }

  //Update order status
  def updateOrderStatus(ordID: String, newStatus: String): Unit = {
    orders.clear()
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      statement.executeUpdate("UPDATE orders SET Status='" + newStatus + "' WHERE OrderID = '" + ordID + "'")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    this.populateEntity(this)
  }

  //Select all the orders that have been dispatched
  def notifyAccounts(): Unit = {
    accountsOrders.clear()
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * FROM orders WHERE Status = 'Dispatched'")
      while (rs.next) {
        var newOrder = new OrderDescription(rs.getString("OrderID"), rs.getString("Date"), rs.getDouble("Price"), rs.getString("CustomerName"), rs.getString("CustomerID"), rs.getString("Address"), rs.getString("Status"), rs.getString("StaffAssigned"))
        accountsOrders += newOrder
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
  }

  //Find an order by a given order ID
  def findByID(entityDescID: String, ordersList: ObservableBuffer[OrderDescription]): ObservableBuffer[OrderDescription] = {
    var requiredOrder = new ObservableBuffer[OrderDescription]
    if (ordersList.isEmpty) {
      requiredOrder
    } else if (entityDescID == ordersList.head.ordID.value) {
      requiredOrder += ordersList.head
      requiredOrder
    } else {
      this.findByID(entityDescID, ordersList.tail)
    }
  }
}