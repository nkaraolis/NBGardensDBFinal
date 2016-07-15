/**
  * Created by Administrator on 30/06/2016.
  */
import scalafx.beans.property.{ObjectProperty, StringProperty}

class StockTable(ProductID :String, ItemName :String, Price :Double, Quantity :Int, Location :String, HasPorous :String) {
  val prodID = new StringProperty(this, "Product ID", ProductID)
  val itName = new StringProperty(this, "Item Name", ItemName)
  val price = new ObjectProperty(this, "Price", Price)
  val quant = new ObjectProperty(this, "Quantity", Quantity)
  val location = new StringProperty(this, "Location", Location)
  val porous = new StringProperty(this, "Has Porous", HasPorous)
}
