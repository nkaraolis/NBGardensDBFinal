/**
  * Created by Administrator on 29/06/2016.
  */

import Order.OrderDescription
import Stock.StockItemDescription

import scalafx.scene.control._
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.BorderPane

case class ViewStock() extends Scene {

  var requiredStock: ObservableBuffer[StockItemDescription] = new ObservableBuffer[StockItemDescription]()

  val stocktable = new TableView[StockItemDescription](Stock.stockItems) {
    columns ++= List(
      new TableColumn[StockItemDescription, String] {
        text = "Product ID"
        cellValueFactory = {
          _.value.prodID
        }
        prefWidth = 100
      },
      new TableColumn[StockItemDescription, String]() {
        text = "Item Name"
        cellValueFactory = {
          _.value.name
        }
        prefWidth = 200
      },
      new TableColumn[StockItemDescription, Double]() {
        text = "Price"
        cellValueFactory = {
          _.value.itemPrice
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
      new TableColumn[StockItemDescription, String]() {
        text = "Location"
        cellValueFactory = {
          _.value.itemLocation
        }
        prefWidth = 100
      },
      new TableColumn[StockItemDescription, String]() {
        text = "Has Porous"
        cellValueFactory = {
          _.value.porous
        }
        prefWidth = 100
      }
    )
  }
  stocktable.onMouseClicked = (event: MouseEvent) => {
    requiredStock.clear()
    requiredStock += stocktable.getSelectionModel.getSelectedItem
    UserSession.changeScene(IndividualStock(requiredStock))
  }

  val topMenu = new warehouseMenuBar().menuBar

  val splitComponents = new SplitPane() {
    autosize()
    items.addAll(topMenu, stocktable)
    dividerPositions=(2)
  }

  splitComponents.setOrientation(Orientation.Vertical)

  stocktable.setMinHeight(50)
  stocktable.setMinWidth(800)

  root = splitComponents

}

