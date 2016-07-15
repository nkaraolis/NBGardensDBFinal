/**
  * Created by Owner on 10/07/2016.
  */

import Order.OrderDescription
import OrderItem.OrderItemDescription
import warehouseManagement._

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.event.ActionEvent
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.control.{Alert, ChoiceDialog}
import scalafx.scene.control.Alert.AlertType
import scalafx.geometry.Insets
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.control.TableColumn._
import scalafx.scene.input.MouseEvent
import scalafx.geometry.Orientation
import scalafx.scene.text.{Font, FontWeight}

case class IndividualOrder(selectedOrder: ObservableBuffer[OrderDescription]) extends Scene {

  val ordertable = new TableView[OrderDescription](selectedOrder) {
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

  var requiredOrder = selectedOrder.head
  var requiredItems: ObservableBuffer[OrderItemDescription] = OrderItem.findOrderItems(requiredOrder.ordID.value, OrderItem.orderItems)

  val orderItemsTable = new TableView[OrderItemDescription](requiredItems) {
    columns ++= List(
      new TableColumn[OrderItemDescription, String]() {
        text = "Order ID"
        cellValueFactory = {
          _.value.ordID
        }
        prefWidth = 100
      },
      new TableColumn[OrderItemDescription, String]() {
        text = "Product ID"
        cellValueFactory = {
          _.value.prodID
        }
        prefWidth = 100
      },
      new TableColumn[OrderItemDescription, Int]() {
        text = "Quantity"
        cellValueFactory = {
          _.value.quant
        }
        prefWidth = 100
      },
      new TableColumn[OrderItemDescription, String]() {
        text = "Porous Required"
        cellValueFactory = {
          _.value.porous
        }
        prefWidth = 125
      },
      new TableColumn[OrderItemDescription, String]() {
        text = "Warehouse Location"
        cellValueFactory = {
          _.value.loc
        }
        prefWidth = 150
      }
    )
  }

  val orderLabel = new Label("Order Details") {
    font = Font.font(null, FontWeight.Bold, 18)
  }

  val orderItemsLabel = new Label("Order Items") {
    font = Font.font(null, FontWeight.Bold, 18)
  }

  val topMenu = new warehouseMenuBar().menuBar

  val updateStatus = new Button("Update Status")
  updateStatus.onAction = (e: ActionEvent) => {
    val newStatus = toggle.selectedToggle.apply().getUserData.toString
    try {
      if (!(requiredOrder.staffassigned.value == "None")) {
        Order.updateOrderStatus(requiredOrder.ordID.value, newStatus)
        UserSession.changeScene(IndividualOrder(Order.findByID(requiredOrder.ordID.value, Order.orders)))
        if (newStatus == "Dispatched") {
          Order.notifyAccounts
          Order.updateOrderStatus(requiredOrder.ordID.value, newStatus)
          UserSession.changeScene(IndividualOrder(Order.findByID(requiredOrder.ordID.value, Order.orders)))
        }
      } else {
        new Alert(AlertType.Error) {
          initOwner(stage)
          title = "No Staff Assigned!"
          headerText = "Assign Staff"
          contentText = "Please assign a staff member before choosing a new status"
        }.showAndWait()
      }
    }
    catch {
      case e: Exception =>
    }
  }

  val assignStaff = new Button("Assign Staff")
  assignStaff.onAction = (e: ActionEvent) => {
    Order.assignStaff(requiredOrder.ordID.value, UserSession.getCurrentUser)
    Stock.decrementStock(requiredOrder.ordID.value)
    Order.updateOrderStatus(requiredOrder.ordID.value, "Processing")
    UserSession.changeScene(IndividualOrder(Order.findByID(requiredOrder.ordID.value, Order.orders)))
  }

  val packaged = new RadioButton("Packaged")
  packaged.setUserData("Packaged")
  val dispatched = new RadioButton("Dispatched")
  dispatched.setUserData("Dispatched")

  val menuBar = new HBox(50, assignStaff, packaged, dispatched, updateStatus)
  menuBar.alignment = Pos.Center
  menuBar.setMaxHeight(50)

  val toggle = new ToggleGroup()
  toggle.toggles = List(packaged, dispatched)
  toggle.selectToggle(packaged)

  val splitComponents = new SplitPane() {
    autosize()
    items.addAll(topMenu, menuBar, orderLabel, ordertable, orderItemsLabel, orderItemsTable)
    dividerPositions = (2)
  }

  splitComponents.setOrientation(Orientation.Vertical)

  ordertable.setMinHeight(50)
  ordertable.setMaxHeight(50)
  ordertable.setMinWidth(800)

  orderItemsTable.setMaxHeight((requiredItems.size * 25) + 25)

  root = splitComponents

}