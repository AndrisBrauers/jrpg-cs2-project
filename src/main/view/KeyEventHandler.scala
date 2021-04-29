package view

import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyEvent}
import play.api.libs.json.{JsValue, Json}

class KeyEventHandler(player: PlayerCoordinates) extends EventHandler[KeyEvent]{
  val playerSpeed: Double = 1.5
  override def handle(event: KeyEvent): Unit = {
    keyPressed(event.getCode)
  }

  def makeJson(x: Double, y: Double): String ={
    val map : Map[String, Double] = Map("x" -> x, "y" -> y)
    val jsonMap : JsValue = Json.toJson(map)
    val string = Json.stringify(jsonMap)
    string
  }
  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => Overworld.socket.emit("overworldMovement",makeJson(0, -playerSpeed))
      case "A" => Overworld.socket.emit("overworldMovement",makeJson(-playerSpeed, 0))
      case "S" => Overworld.socket.emit("overworldMovement",makeJson(0, playerSpeed))
      case "D" => Overworld.socket.emit("overworldMovement",makeJson(playerSpeed, 0))
      case "X" => Overworld.socket.emit("addPlayer", "")
      case "Z" => Overworld.socket.emit("removePlayer", "")
      case "M" => Overworld.socket.emit("movePlayers", "")
      case "N" => Overworld.socket.emit("stopPlayers", "")
      case _ => println(keyCode.getName + " pressed with no action")

//      case "W" => player.y -= playerSpeed
//      case "A" => player.x -= playerSpeed
//      case "S" => player.y += playerSpeed
//      case "D" => player.x += playerSpeed
//      case _ => println(keyCode.getName + " pressed with no action")
    }
  }
}
