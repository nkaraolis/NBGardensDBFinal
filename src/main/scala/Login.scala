/**
  * Created by Administrator on 29/06/2016.
  */

import java.sql.{Connection, DriverManager}

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.event.ActionEvent
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.VBox

case class Login() extends Scene {

  //Variables required for database connection
  val url = "jdbc:mysql://localhost:3306/nbgardensv0.2?autoReconnect=true&useSSL=false"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "password"
  var connection: Connection = _

  //Check the username is in the database
  def validateUsername(staffID: String): Boolean = {
    var userFound = false
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("select exists ( select * from staff where StaffID = '" + staffID + "') as 'True'")
      while (rs.next) {
        if (rs.getString("True") == "1") {
          userFound = true
        }
        else {
          userFound = false
        }
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    userFound
  }

  //Check the password is correct for the given staffID
  def validatePassword(staffID: String, staffPassword: String): Boolean = {
    var userFound = false
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("select exists ( select * from staff where StaffID = '" + staffID + "' AND Password = md5('" + staffPassword + "')) as 'True'")
      while (rs.next) {
        if (rs.getString("True") == "1") {
          userFound = true
        }
        else {
          userFound = false
        }
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    userFound
  }

  //Get the current user's role
  def getUserRole(staffID: String): String = {
    var userRole = ""
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("select Role from staff where StaffID = '" + staffID + "'")
      while (rs.next) {
        userRole = rs.getString("Role")
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    userRole
  }

  val ilabel = new Label("Employee ID:")
  val etextField = new TextField() {
    promptText = "Employee ID"
  }

  val plabel = new Label("Password:")

  val passwordField = new PasswordField() {
    promptText = "Enter Password"
  }

  etextField.margin = Insets(0, 100, 20, 100)
  passwordField.margin = Insets(0, 100, 20, 100)

  etextField.maxWidth = 250
  passwordField.maxWidth = 250

  this.onKeyPressed = (event: KeyEvent) => {
    if (event.code == KeyCode.Enter) {
      attemptLogin()
    }
  }

  val btnLogin = new Button("Login")
  btnLogin.prefWidth = 200
  btnLogin.prefHeight = 30

  btnLogin.onAction = (event: ActionEvent) => {
    attemptLogin()
  }

  def attemptLogin(): Unit = {
    if (etextField.getText.isEmpty) {

    } else if (validateUsername(etextField.getText)) {
      if (validatePassword(etextField.getText, passwordField.text.value)) {
        UserSession.setUser(etextField.getText.toUpperCase)
        if (getUserRole(etextField.getText) == "Accounts") {
          UserSession.changeScene(AccountMenu())
        } else {
          UserSession.changeScene(WarehouseMenu())
        }
      } else {
        new Alert(AlertType.Error) {
          title = "Incorrect Password!"
          headerText = "Incorrect Password!"
          contentText = "Please enter the correct password"
          etextField.text.value = ""
          passwordField.text.value = ""
        }.showAndWait()
      }
    } else {
      new Alert(AlertType.Error) {
        title = "Incorrect Username!"
        headerText = "Incorrect Username!"
        contentText = "Please enter a valid username"
        etextField.text.value = ""
        passwordField.text.value = ""
      }.showAndWait()
    }
  }

// Request focus on the username field by default.
Platform.runLater(etextField.requestFocus())

val loginUI = new VBox(7, ilabel, etextField, plabel, passwordField, btnLogin)
loginUI.alignment = Pos.Center
root = loginUI
}