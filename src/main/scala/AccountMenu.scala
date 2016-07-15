/**
  * Created by Administrator on 29/06/2016.
  */

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, VBox}
import Order.OrderDescription
import StockOrder.StockOrderDescription
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
import scalafx.scene.text.{Font, FontWeight}

case class AccountMenu() extends Scene {

  var accountsOrderTable = new TableView[OrderDescription](Order.accountsOrders) {
    columns ++= List(
      new TableColumn[OrderDescription, String] {
        text = "Order ID"
        cellValueFactory = {
          _.value.ordID
        }
        prefWidth = 125
      },
      new TableColumn[OrderDescription, String]() {
        text = "Date"
        cellValueFactory = {
          _.value.ordDate
        }
        prefWidth = 125
      },
      new TableColumn[OrderDescription, Double]() {
        text = "Price"
        cellValueFactory = {
          _.value.ordPrice
        }
        prefWidth = 125
      },
      new TableColumn[OrderDescription, String]() {
        text = "Customer ID"
        cellValueFactory = {
          _.value.custID
        }
        prefWidth = 125
      },
      new TableColumn[OrderDescription, String]() {
        text = "Customer Name"
        cellValueFactory = {
          _.value.custName
        }
        prefWidth = 125
      },
      new TableColumn[OrderDescription, String]() {
        text = "Customer Address"
        cellValueFactory = {
          _.value.custAddress
        }
        prefWidth = 250
      },
      new TableColumn[OrderDescription, String]() {
        text = "Order Status"
        cellValueFactory = {
          _.value.ordstatus
        }
        prefWidth = 125
      },
      new TableColumn[OrderDescription, String]() {
        text = "Staff Assigned"
        cellValueFactory = {
          _.value.staffassigned
        }
        prefWidth = 125
      }
    )
  }

  var stockOrdersTable = new TableView[StockOrderDescription](StockOrder.stockOrders) {
    columns ++= List(
      new TableColumn[StockOrderDescription, String] {
        text = "Stock Order ID"
        cellValueFactory = {
          _.value.stockOrdID
        }
        prefWidth = 125
      },
      new TableColumn[StockOrderDescription, String] {
        text = "Product ID"
        cellValueFactory = {
          _.value.prodID
        }
        prefWidth = 125
      },
      new TableColumn[StockOrderDescription, String] {
        text = "Item Name"
        cellValueFactory = {
          _.value.name
        }
        prefWidth = 125
      },
      new TableColumn[StockOrderDescription, Int] {
        text = "Quantity"
        cellValueFactory = {
          _.value.quant
        }
        prefWidth = 125
      },
      new TableColumn[StockOrderDescription, Double] {
        text = "Price"
        cellValueFactory = {
          _.value.itemPrice
        }
        prefWidth = 125
      },
      new TableColumn[StockOrderDescription, String] {
        text = "Date"
        cellValueFactory = {
          _.value.date
        }
        prefWidth = 125
      },
      new TableColumn[StockOrderDescription, String] {
        text = "Supplier"
        cellValueFactory = {
          _.value.stockSupplier
        }
        prefWidth = 125
      }
    )
  }

  val topMenu = new accountsMenuBar().menuBar

  val ordersLabel = new Label("Dispatched Orders") {
    font = Font.font(null, FontWeight.Bold, 12)
  }
  val stockLabel = new Label("Stock Orders") {
    font = Font.font(null, FontWeight.Bold, 12)
  }

  val splitTable = new SplitPane() {
    autosize()
    items.addAll(ordersLabel, accountsOrderTable, stockLabel, stockOrdersTable)
    dividerPositions = (2)
  }

  splitTable.setOrientation(Orientation.Vertical)

  val splitComponents = new SplitPane() {
    autosize()
    items.addAll(topMenu, splitTable)
    dividerPositions = (2)
  }

  accountsOrderTable.setMinHeight(250)
  accountsOrderTable.setMaxHeight(250)

  splitComponents.setOrientation(Orientation.Vertical)

  root = splitComponents
}
