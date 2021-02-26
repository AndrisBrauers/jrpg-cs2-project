package Controler

import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyEvent}
import view.PlayerCoordinates

class KeyEventHandler(player: PlayerCoordinates) extends EventHandler[KeyEvent]{
  val playerSpeed: Double = 0.1
  override def handle(event: KeyEvent): Unit = {
    keyPressed(event.getCode)
  }
  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => player.y -= playerSpeed
      case "A" => player.x -= playerSpeed
      case "S" => player.y += playerSpeed
      case "D" => player.x += playerSpeed
      case _ => println(keyCode.getName + " pressed with no action")
    }
  }
}
