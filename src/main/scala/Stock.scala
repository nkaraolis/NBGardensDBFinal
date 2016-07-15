/**
  * Created by Nick on 28/06/2016.
  */
import java.sql.DriverManager

import OrderItem.OrderItemDescription

import scala.collection.mutable.Queue
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer

object Stock extends Entity {

  //Set the table name to use the stock table
  var dbTableName = "stock"

  //Column names for the stock table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("ProductID", "ItemName", "Price", "Quantity", "Location", "HasPorous")

  //Column types for the stock table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string", "double", "int", "string", "string")

  //Collection of stock
  var stockItems = new ObservableBuffer[StockItemDescription]

  //Case class to represent one stock item
  class StockItemDescription(productID: String, itemName: String, price: Double, quantity: Int, location: String, hasPorous: String) extends EntityDescription {
    override var entityID: String = productID
    val prodID = new StringProperty(this, "Product ID", productID)
    val name = new StringProperty(this, "Item Name", itemName)
    val quant = new ObjectProperty(this, "Quantity", quantity)
    val itemPrice = new ObjectProperty(this, "Price", price)
    val itemLocation = new StringProperty(this, "Location", location)
    val porous = new StringProperty(this, "Has Porous", hasPorous)
  }

  //Create a new stockItemDescription (stock row) and add it to the collection of orders
  override def createEntity(): Unit = {
    var addNewStock = new StockItemDescription(stringParams(0), stringParams(1), doubleParams(0), intParams(0), stringParams(2), stringParams(3))
    stockItems += addNewStock
  }

  //Update the stock levels based on an order
  def decrementStock(ordID: String): Unit = {

    var reqItems = Queue[String]()
    var reqQuantities = Queue[Int]()

    //Find the items and their quantities for the required order
    def findItems(ordID: String, items: ObservableBuffer[OrderItemDescription]): Unit = {
      if (items.isEmpty) {

      } else if (items.head.ordID.value == ordID) {
        reqItems += items.head.prodID.value
        reqQuantities += items.head.quant.value
        findItems(ordID, items.tail)
      } else {
        findItems(ordID, items.tail)
      }
    }
    //Call the nested function to find the required items and quantities
    findItems(ordID, OrderItem.orderItems)

    //Update the stock values
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.prepareStatement("UPDATE stock SET Quantity = (Quantity - ?) WHERE ProductID = ?")

      var counter = 0
      for (item <- reqItems) {
        statement.setInt(1, reqQuantities(counter))
        statement.setString(2, item)
        statement.addBatch()
        counter += 1
        println(statement)
      }
      statement.executeBatch()
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    stockItems.clear
    this.populateEntity(this)
    reqItems.clear
    reqQuantities.clear
  }

  def findByID(entityDescID: String, stockList: ObservableBuffer[StockItemDescription]): ObservableBuffer[StockItemDescription] = {
    var requiredOrder = new ObservableBuffer[StockItemDescription]
    if (stockList.isEmpty) {
      requiredOrder
    } else if (entityDescID == stockList.head.prodID.value) {
      requiredOrder += stockList.head
      requiredOrder
    } else {
      this.findByID(entityDescID, stockList.tail)
    }
  }
}
