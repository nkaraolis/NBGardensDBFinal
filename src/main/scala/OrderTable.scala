/**
  * Created by Administrator on 30/06/2016.
  */

import scalafx.beans.property.{ObjectProperty, StringProperty}

class OrderTable(OrderID : String, Date :String, Price :Double, CustomerID: String, CustomerName: String, CustomerAddress: String, OrderStatus :String, StaffAssigned :String) {
  val ordID = new StringProperty(this, "Order ID", OrderID)
  val date = new StringProperty(this, "Date", Date)
  val price = new ObjectProperty(this, "Price", Price)
  val custID = new StringProperty(this, "Customer ID", CustomerID)
  val custName = new StringProperty(this, "Customer Name", CustomerName)
  val custAddress = new StringProperty(this, "Customer Address", CustomerAddress)
  val ordstatus = new StringProperty(this, "Order Status", OrderStatus)
  val staffassigned = new StringProperty(this, "Staff Assigned", StaffAssigned)
}
