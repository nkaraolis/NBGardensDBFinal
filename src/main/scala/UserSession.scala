/**
  * Created by Daniel on 07/07/2016.
  */
import scalafx.scene.Scene
import scalafx.stage.Stage

/**
  * this object will store login session
  */

object UserSession {
  private var currentUser = ""
  var stage = new Stage()

  def setUser(userID: String): Unit = currentUser = userID

  def getCurrentUser() = currentUser

  def setStage(stage: Stage): Unit = this.stage = stage

  def changeScene(next: Scene): Unit = {
    stage.scene = next
  }
}