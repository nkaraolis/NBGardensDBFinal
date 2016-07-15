/**
  * Created by Administrator on 29/06/2016.
  */

import Order.OrderDescription
import warehouseManagement._

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.control.{Alert, ChoiceDialog}
import scalafx.scene.control.Alert.AlertType
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.control.TableColumn._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.BorderPane

case class ViewOrders() extends Scene {

  var requiredOrder: ObservableBuffer[OrderDescription] = new ObservableBuffer[OrderDescription]()

  var ordertable = new TableView[OrderDescription](Order.orders) {
    columns ++= List(
      new TableColumn[OrderDescription, String] {
        text = "Order ID"
        cellValueFactory = {
          _.value.ordID
        }
        prefWidth = 100
      },
      new TableColumn[OrderDescription, String]() {
        text = "Date"
        cellValueFactory = {
          _.value.ordDate
        }
        prefWidth = 100
      },
      new TableColumn[OrderDescription, Double]() {
        text = "Price"
        cellValueFactory = {
          _.value.ordPrice
        }
        prefWidth = 100
      },
      new TableColumn[OrderDescription, String]() {
        text = "Customer ID"
        cellValueFactory = {
          _.value.custID
        }
        prefWidth = 100
      },
      new TableColumn[OrderDescription, String]() {
        text = "Customer Name"
        cellValueFactory = {
          _.value.custName
        }
        prefWidth = 100
      },
      new TableColumn[OrderDescription, String]() {
        text = "Customer Address"
        cellValueFactory = {
          _.value.custAddress
        }
        prefWidth = 300
      },
      new TableColumn[OrderDescription, String]() {
        text = "Order Status"
        cellValueFactory = {
          _.value.ordstatus
        }
        prefWidth = 100
      },
      new TableColumn[OrderDescription, String]() {
        text = "Staff Assigned"
        cellValueFactory = {
          _.value.staffassigned
        }
        prefWidth = 100
      }
    )
  }

  ordertable.onMouseClicked = (event: MouseEvent) => {
    requiredOrder.clear()
    requiredOrder += ordertable.getSelectionModel.getSelectedItem
    UserSession.changeScene(IndividualOrder(requiredOrder))
  }

  val topMenu = new warehouseMenuBar().menuBar

  val splitComponents = new SplitPane() {
    autosize()
    items.addAll(topMenu, ordertable)
    dividerPositions = (2)
  }

  splitComponents.setOrientation(Orientation.Vertical)

  ordertable.setMinHeight(50)
  ordertable.setMinWidth(800)

  root = splitComponents
}
