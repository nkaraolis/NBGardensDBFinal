/**
  * Created by Owner on 12/07/2016.
  */
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.HBox

class warehouseMenuBar {
  val viewStock = new Button("View Stock")
  viewStock.onAction = (e : ActionEvent) => {
    UserSession.changeScene(ViewStock())
  }

  val viewOrders = new Button("View Orders")
  viewOrders.onAction= (e : ActionEvent) => {
    UserSession.changeScene(ViewOrders())
  }

  val btnLogOut = {
    new Button("Log Out")
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
      case _                   => UserSession.changeScene(WarehouseMenu())
    }
  }

  val menuBar = new HBox(50, viewStock, viewOrders, btnLogOut)
  menuBar.alignment = Pos.Center
  menuBar.setMaxHeight(50)
}
