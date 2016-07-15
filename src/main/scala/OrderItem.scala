/**
  * Created by Nick on 28/06/2016.
  */
import scala.collection.mutable.Queue
import scalafx.beans.property.{DoubleProperty, IntegerProperty, ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer

object OrderItem extends Entity {

  //Set the table name to use the order items table
  var dbTableName = "orderitems"

  //Column names for the order items table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("OrderID", "ProductID", "Quantity", "PorousRequired", "Location")

  //Column types for the order items table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string", "int", "string", "string")

  //Collection of order items
  var orderItems = new ObservableBuffer[OrderItemDescription]

  //Case class to represent one item for an order
  class OrderItemDescription(orderID: String, productID: String, quantity: Int, porousReq: String, location: String) extends EntityDescription {
    override var entityID: String = orderID
    val ordID = new StringProperty(this, "Order ID", orderID)
    val prodID = new StringProperty(this, "Date", productID)
    val quant = new ObjectProperty(this, "Quantity", quantity)
    val porous = new StringProperty(this, "Customer Address", porousReq)
    val loc = new StringProperty(this, "Order Status", location)
  }

  //Create a new orderItemDescription (order item row) and add it to the collection of order items
  override def createEntity(): Unit = {
    var addNewItem = new OrderItemDescription(stringParams(0), stringParams(1), intParams(0), stringParams(2), stringParams(3))
    orderItems += addNewItem
  }

  def findOrderItems(ordID : String, allItems : ObservableBuffer[OrderItemDescription]): ObservableBuffer[OrderItemDescription] = {
    val requiredItems: ObservableBuffer[OrderItemDescription] = new ObservableBuffer[OrderItemDescription]()
    innerFindOrderItems(ordID, allItems)
    def innerFindOrderItems(ordID: String, allItems: ObservableBuffer[OrderItemDescription]) {
      if (allItems.isEmpty) {

      } else if (allItems.head.ordID.value == ordID) {
        requiredItems.append(allItems.head)
        innerFindOrderItems(ordID, allItems.tail)
      } else {
        innerFindOrderItems(ordID, allItems.tail)
      }
    }
    requiredItems
  }

}
