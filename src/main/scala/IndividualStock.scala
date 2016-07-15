/**
  * Created by Owner on 10/07/2016.
  */

import java.text.SimpleDateFormat
import java.util.Calendar

import OrderItem.OrderItemDescription
import Stock.StockItemDescription
import StockOrder.StockOrderDescription
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
import scalafx.event.ActionEvent

case class IndividualStock(selectedStock: ObservableBuffer[StockItemDescription]) extends Scene {

  val stocktable = new TableView[StockItemDescription](selectedStock) {
    columns ++= List(
      new TableColumn[StockItemDescription, String] {
        text = "Order ID"
        cellValueFactory = {
          _.value.prodID
        }
        prefWidth = 100
      },
      new TableColumn[StockItemDescription, String]() {
        text = "Product ID"
        cellValueFactory = {
          _.value.name
        }
        prefWidth = 100
      },
      new TableColumn[StockItemDescription, Int]() {
        text = "Quantity"
        cellValueFactory = {
          _.value.quant
        }
        prefWidth = 100
      },
      new TableColumn[StockItemDescription, Double]() {
        text = "Customer ID"
        cellValueFactory = {
          _.value.itemPrice
        }
        prefWidth = 100
      },
      new TableColumn[StockItemDescription, String]() {
        text = "Customer Name"
        cellValueFactory = {
          _.value.itemLocation
        }
        prefWidth = 100
      },
      new TableColumn[StockItemDescription, String]() {
        text = "Customer Address"
        cellValueFactory = {
          _.value.porous
        }
        prefWidth = 300
      }
    )
  }

  var requiredStock = selectedStock.head

  var newStockOrdID = ""

  val nextOrdIDIndex = StockOrder.stockOrders.size + 1

  if(nextOrdIDIndex < 10) {
    newStockOrdID = "SO000" + nextOrdIDIndex.toString
  } else if(nextOrdIDIndex < 100) {
    newStockOrdID = "SO0" + nextOrdIDIndex.toString
  } else {
    newStockOrdID = "SO" + nextOrdIDIndex.toString
  }

  val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
  val newStockOrderDate = dateFormat.format(Calendar.getInstance.getTime)

  val updateStockLabel = new Label("Enter stock order here") {
    font = Font.font(null, FontWeight.Bold, 18)
  }

  val stockLabel = new Label("Stock Details") {
    font = Font.font(null, FontWeight.Bold, 18)
  }

  val topMenu = new warehouseMenuBar().menuBar

  val quantityLabel = new Label("Quantity") {
    font = Font.font(null, FontWeight.Bold, 12)
  }
  val priceLabel = new Label("Order Price") {
    font = Font.font(null, FontWeight.Bold, 12)
  }
  val supplierLabel = new Label("Supplier") {
    font = Font.font(null, FontWeight.Bold, 12)
  }

  quantityLabel.setMaxHeight(50)
  priceLabel.setMaxHeight(50)
  supplierLabel.setMaxHeight(50)

  var stockQuantity = new TextArea("")
  stockQuantity.setMaxHeight(2)
  stockQuantity.setMaxWidth(150)

  var stockPrice = new TextArea("")
  stockPrice.setMaxHeight(2)
  stockPrice.setMaxWidth(150)

  var stockSupplier = new TextArea("")
  stockSupplier.setMaxHeight(2)
  stockSupplier.setMaxWidth(150)

  val enterStockOrderbtn = new Button("Order Stock")
  enterStockOrderbtn.onAction = (e: ActionEvent) => {
    try {
      if (stockQuantity.length.value == 0 || stockPrice.length.value == 0 || stockSupplier.length.value == 0) {
        new Alert(AlertType.Error) {
          initOwner(stage)
          title = "Missing Data!"
          headerText = "Missing data"
          contentText = "Please fill in all fields before updating stock"
        }.showAndWait()
      } else {
        StockOrder.addNewStock(requiredStock.prodID.value, stockQuantity.getText.toInt)
        val newOrder = new StockOrderDescription(newStockOrdID, requiredStock.prodID.value, requiredStock.name.value, stockQuantity.getText.toInt, stockPrice.getText.toDouble, newStockOrderDate, stockSupplier.getText)
        StockOrder.addStockOrder(newOrder)
        UserSession.changeScene(IndividualStock(Stock.findByID(requiredStock.prodID.value, Stock.stockItems)))
      }
    } catch {
      case e: Exception =>
    }
  }

  val enterStockOrder = new HBox(15, quantityLabel, stockQuantity, priceLabel, stockPrice, supplierLabel, stockSupplier, enterStockOrderbtn)
  enterStockOrder.setMaxHeight(15)
  enterStockOrder.alignment = Pos.Center

  val splitComponents = new SplitPane() {
    autosize()
    items.addAll(topMenu, updateStockLabel, enterStockOrder, stockLabel, stocktable)
    dividerPositions = (2)
  }

  splitComponents.setOrientation(Orientation.Vertical)

  stocktable.setMinHeight(50)
  stocktable.setMaxHeight(50)
  stocktable.setMinWidth(800)

  root = splitComponents

}