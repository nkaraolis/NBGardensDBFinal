/**
  * Created by Nick on 28/06/2016.
  */

object Main {
  def main(args: Array[String]): Unit = {
    Order.populateEntity(Order)
    OrderItem.populateEntity(OrderItem)
    Stock.populateEntity(Stock)
    StockOrder.populateEntity(StockOrder)
    Order.notifyAccounts
  }
}
