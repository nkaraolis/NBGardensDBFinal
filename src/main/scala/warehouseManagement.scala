/**
  * Created by Daniel on 16/06/2016.
  */

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object warehouseManagement extends JFXApp {
  stage = new PrimaryStage {
    title = "Warehouse Management System"
    height = 600
    width = 550
    scene = Login()
  }

  Order.populateEntity(Order)
  OrderItem.populateEntity(OrderItem)
  Stock.populateEntity(Stock)
  StockOrder.populateEntity(StockOrder)
  Order.notifyAccounts()

  UserSession.setStage(stage)

}



