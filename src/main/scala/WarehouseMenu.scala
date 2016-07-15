/**
  * Created by Administrator on 29/06/2016.
  */

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.ButtonType
import scalafx.scene.layout.VBox
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert._

case class WarehouseMenu() extends Scene{

  val btnViewOrders = {
    new Button("View Orders")
  }
  btnViewOrders.prefWidth = 100
  btnViewOrders.prefHeight = 50

  val btnViewStock = {
    new Button("View Stock")
  }
  btnViewStock.prefWidth = 100
  btnViewStock.prefHeight = 50

  val btnLogOut = {
    new Button("Log Out")
  }

  btnLogOut.prefWidth = 100
  btnLogOut.prefHeight = 50

  btnViewOrders.margin = Insets(0, 0, 20, 0)
  btnViewStock.margin = Insets(0, 0, 20, 0)

  btnLogOut.margin = Insets(0, 0, 20, 0)

  btnViewOrders.onAction = (event :ActionEvent) => {
    UserSession.changeScene(ViewOrders())
  }

  btnViewStock.onAction = (event :ActionEvent) => {
    UserSession.changeScene(ViewStock())
  }

  btnLogOut.onAction = (event :ActionEvent) => {
    val alert = new Alert(AlertType.Confirmation) {
      title = "Exit Confirmation"
      headerText = "Exit Confirmation"
      contentText = "Are you sure you want to logout?"
    }
    val result = alert.showAndWait()
    // React to user's selectioon
    result match {
      case Some(ButtonType.OK) => UserSession.changeScene(Login())
      case _                   => UserSession.changeScene(UserSession.stage.getScene)
    }
  }

  val wareMenu = new VBox(7, btnViewOrders, btnViewStock, btnLogOut)
  wareMenu.alignment = Pos.Center
  root = wareMenu

}
