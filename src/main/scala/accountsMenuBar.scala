/**
  * Created by Owner on 14/07/2016.
  */

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.HBox

class accountsMenuBar {
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

  val menuBar = new HBox(btnLogOut)
  menuBar.alignment = Pos.Center
  menuBar.setMaxHeight(50)

}
