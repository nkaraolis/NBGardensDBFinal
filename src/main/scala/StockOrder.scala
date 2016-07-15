/**
  * Created by Nick on 28/06/2016.
  */
import java.sql.{Date, DriverManager}
import scalafx.collections.ObservableBuffer
import scala.collection.mutable.Queue
import scalafx.beans.property.{ObjectProperty, StringProperty}

object StockOrder extends Entity {

  //Set the table name to use the orders table
  var dbTableName = "stockorder"

  //Column names for the stock orders table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("StockOrderID","ProductID", "ItemName", "Quantity", "Price", "Date", "Supplier")

  //Column types for the stock orders table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string",  "string", "int", "double", "string", "string")

  //Collection of stock orders
  var stockOrders = new ObservableBuffer[StockOrderDescription]

  //Case class to represent one stock order
  class StockOrderDescription(stockOrderID: String, productID: String, itemName: String, quantity: Int, price: Double, ordDate: String, supplier: String) extends EntityDescription {
    override var entityID: String = stockOrderID
    val stockOrdID = new StringProperty(this, "Stock Order ID", stockOrderID)
    val prodID = new StringProperty(this, "Product ID", productID)
    val name = new StringProperty(this, "Quantity", itemName)
    val quant = new ObjectProperty(this, "Quantity", quantity)
    val itemPrice = new ObjectProperty(this, "Price", price)
    val date = new StringProperty(this, "Location", ordDate)
    val stockSupplier = new StringProperty(this, "Has Porous", supplier)
  }

  //Create a new stockOrderDescription (stock order row) and add it to the collection of stock orders
  override def createEntity(): Unit = {
    var addNewStockOrder = new StockOrderDescription(stringParams(0), stringParams(1), stringParams(2), intParams(0), doubleParams(0), stringParams(3), stringParams(4))
    stockOrders += addNewStockOrder
  }

  //Add a new stock order to the database and update the stock orders collection afterwards
  def addNewStock(prodID: String, quantity: Int): Unit = {
    //Update the stock values
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      statement.executeUpdate("UPDATE stock SET Quantity = (Quantity + " +  quantity + ") WHERE ProductID = '" + prodID + "'")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    Stock.stockItems.clear
    Stock.populateEntity(Stock)
  }

  def addStockOrder(newOrder : StockOrderDescription): Unit = {
    //Update the stock orders
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      statement.executeUpdate("INSERT INTO stockOrder VALUES ('" + newOrder.stockOrdID.value + "', '" + newOrder.prodID.value + "', '" + newOrder.name.value +
      "', '" + newOrder.quant.value + "', '" + newOrder.itemPrice.value + "', '" + newOrder.date.value + "', '" + newOrder.stockSupplier.value + "')")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    stockOrders.clear
    this.populateEntity(this)
  }
}
