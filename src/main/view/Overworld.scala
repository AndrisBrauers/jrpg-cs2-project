package view

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.scene.input.{KeyEvent, MouseEvent}
import play.api.libs.json.JsPath.\
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.scene.control.TextField
import scalafx.scene.layout.{BorderPane, GridPane, Pane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import play.api.libs.json.{JsValue, Json}
import scalafx.animation.AnimationTimer
import scala.math.BigDecimal.double2bigDecimal


class SetID() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    val message : String = objects.apply(0).toString
    Overworld.playerID = message
  }
}

class OverworldMap() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    val message : String = objects.apply(0).toString
    Overworld.fromMapJSON(message)
  }
}

class OverworldState() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    val JSON : String = objects.apply(0).toString
    val Players : String = objects.apply(1).toString
    Overworld.playerJSON = JSON
    Overworld.playerString = Players
  }
}

object Overworld extends JFXApp {

  val socket: Socket = IO.socket("http://localhost:8080/")
  socket.on("overworldState", new OverworldState())
  socket.on("overworldMap", new OverworldMap())
  socket.on("yourPartyId", new SetID())
  socket.connect()

  var playerID : String = ""

  var sceneGraphics: Group = new Group {}

  var playerJSON  : String = ""
  var playerString : String = ""

  //This string is placeholder when opponent moving will be implemented
  //var oppInfo : JsValue = Json.parse("""[{"location":{"x":2, "y": 2}, "level":25,"inBattle": false}, {"location":{"x":2.43, "y": 0.45}, "level":10,"inBattle": false}, {"location":{"x":12.49, "y": 9.32}, "level":1,"inBattle": false}, {"location":{"x":6.30, "y": 1.43}, "level":5,"inBattle": true}]""")

  // These values is for demonstrating larger map. That is what program gets from JSON string

  var mapWidth : Int = 0
  var mapHeight : Int = 0
  var mapTiles : Array[Boolean] = Array()


  val windowWidth : Int = 200
  val windowHeight : Int = 200
  val tileSize : Int = 50
  val playerR : Int = 10
  val playerCoo : PlayerCoordinates = new PlayerCoordinates()
  var playerBattleState : Boolean = false
  var newXZero : Int = 0
  var newYZero : Int = 0

  def fromMapJSON(jsonString: String): Unit = {
    val parsed: JsValue = Json.parse(jsonString)
    val size = (parsed \ "mapSize").as[Map[String,Int]]
    val tiles = (parsed \ "tiles").as[Array[Array[Map[String, JsValue]]]]
    mapWidth = size("width")
    mapHeight = size("height")

    val placeholder = new Array[Boolean](tiles.length * tiles(0).length)
    for (j <- 0 to tiles.length - 1){
      for(i <- 0 to tiles(j).length - 1){
        val tile = (tiles(j)(i)("passable")).as[Boolean]
        placeholder(mapWidth * j + i) =  tile
      }
    }
    mapTiles = placeholder
  }

  def fromPlayerJSON(jsonString: String, playerString: String): Unit = {

    var str = playerString.replaceFirst(",", "")
    var allPlayerID : Array[String] = str.split(",")
    allPlayerID = allPlayerID.filter(!_.contains(playerID))
    println(jsonString)

    val parsed: JsValue = Json.parse(jsonString)
    val player = (parsed \ playerID).as[Map[String, JsValue]]
    val locationPlayer : JsValue = player("location")
    playerBattleState = player("inBattle").as[Boolean]
    playerCoo.x = (locationPlayer \ "x").as[Double]
    playerCoo.y = (locationPlayer \ "y").as[Double]
    newXZero = (playerCoo.x - windowWidth / (2 * tileSize)).toInt
    newYZero = (playerCoo.y - windowHeight / (2 * tileSize)).toInt
    renderMap()
    renderPlayer()

    for (oppParty <- allPlayerID) {
      if(oppParty != playerID){
        val party = (parsed \ oppParty).as[Map[String, JsValue]]
        val locationOpp : JsValue = party("location")
        val oppX = (locationOpp \ "x").as[Double]
        val oppY = (locationOpp \ "y").as[Double]
        val level = party("level").as[Int]
        val inBattle = party("inBattle").as[Boolean]
        renderOppParty(oppX, oppY, level, inBattle)
      }
    }
//    val otherParties = (parsed \ "otherParties").as[Array[JsValue]]
//
//    for (party <- otherParties){
//      location = party("location")
//      val oppX = (location \ "x").as[Double]
//      val oppY = (location \ "y").as[Double]
//      val level = (party \ "level").as[Int]
//      val inBattle = (party \ "inBattle").as[Boolean]
//
//      renderOppParty(oppX, oppY, level, inBattle)
//    }

  }

  def renderOppParty(x: Double, y: Double, level : Int, inBattle : Boolean): Unit = {
    val deltaX : Double = (x - (playerCoo.x - windowWidth / (2 * tileSize))).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    val deltaY : Double = (y - (playerCoo.y - windowWidth / (2 * tileSize))).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    if (inBattle) {
      val rectangle: Rectangle = new Rectangle {
        width = playerR * 2
        height = playerR * 2
        translateX = deltaX * tileSize
        translateY = deltaY * tileSize
        fill = Color.Brown
      }
      sceneGraphics.children.add(rectangle)
    }
    else if (level < 5){
      val circle: Circle = new Circle {
        radius = playerR
        centerX = deltaX * tileSize
        centerY = deltaY * tileSize
        fill = Color.White
      }
      sceneGraphics.children.add(circle)
    }
    else if (level < 10){
      val circle: Circle = new Circle {
        radius = playerR
        centerX = deltaX * tileSize
        centerY = deltaY * tileSize
        fill = Color.Yellow
      }
      sceneGraphics.children.add(circle)
    }
    else if (level < 20){
      val circle: Circle = new Circle {
        radius = playerR
        centerX = deltaX * tileSize
        centerY = deltaY * tileSize
        fill = Color.Orange
      }
      sceneGraphics.children.add(circle)
    }
    else {
      val circle: Circle = new Circle {
        radius = playerR
        centerX = deltaX * tileSize
        centerY = deltaY * tileSize
        fill = Color.Red
      }
      sceneGraphics.children.add(circle)
    }
  }

  def renderMap() : Unit = {
    println("Render map")
    for (a <- -1 to windowHeight / tileSize) {
      var j = a + newYZero
      if (playerCoo.y < 2 && playerCoo.y != 0 && playerCoo.y != 1) {
        j = a + newYZero
      }
      else {
        j = a + newYZero + 1
      }
      for (b <- -1 to windowWidth / tileSize) {
        var i = b + newXZero
        if (playerCoo.x < 2 && playerCoo.x != 0 && playerCoo.x != 1) {
          i = b + newXZero
        }
        else {
          i = b + newXZero + 1
        }
        if ( -1 < j * mapWidth + i && j * mapWidth + i < mapTiles.length && i > -1 && j > -1 && i < mapWidth) {
          if (mapTiles(j * mapWidth + i)) {
            val rectangle: Rectangle = new Rectangle {
              width = tileSize
              height = tileSize
              translateX = tileSize * b + (1 - (playerCoo.x - playerCoo.x.toInt).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble) * tileSize
              translateY = tileSize * a + (1 - (playerCoo.y - playerCoo.y.toInt).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble) * tileSize
              fill = Color.Green
            }
            sceneGraphics.children.add(rectangle)
          }
          else {
            val rectangle: Rectangle = new Rectangle {
              width = tileSize
              height = tileSize
              translateX = tileSize * b + (1 - (playerCoo.x - playerCoo.x.toInt).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble) * tileSize
              translateY = tileSize * a + (1 - (playerCoo.y - playerCoo.y.toInt).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble) * tileSize
              fill = Color.Blue
            }
            sceneGraphics.children.add(rectangle)
          }
        }
        else {
          val rectangle: Rectangle = new Rectangle {
            width = tileSize
            height = tileSize
            translateX = tileSize * b + (1 - (playerCoo.x - playerCoo.x.toInt).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble) * tileSize
            translateY = tileSize * a + (1 - (playerCoo.y - playerCoo.y.toInt).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble) * tileSize
            fill = Color.Black
          }
          sceneGraphics.children.add(rectangle)
        }

      }

    }
  }

  def renderPlayer() : Unit = {
    if(playerBattleState){
      val rectangle: Rectangle = new Rectangle {
        width = playerR * 2
        height = playerR * 2
        translateX = windowWidth / 2
        translateY = windowWidth / 2
        fill = Color.Purple
      }
      sceneGraphics.children.add(rectangle)
    } else {
      val circle: Circle = new Circle {
        radius = playerR
        centerX = windowWidth / 2
        centerY = windowWidth / 2
        fill = Color.Purple
      }
      sceneGraphics.children.add(circle)
    }
  }

//  def convertingPlayerJSON(): String = {
//    val locationMap : Map[String, Double] = Map("x" -> playerCoo.x, "y" -> playerCoo.y)
//    val locationMapJSON : JsValue = Json.toJson(locationMap)
//    val levelJSON : JsValue = Json.toJson(5)  // This 5 is just a place holder
//    val inBattleJSON : JsValue = Json.toJson(false)  // This is just a place holder
//    val playerPartyMap: Map[String, JsValue] = Map(
//      ("location", locationMapJSON),
//      ("level", levelJSON),
//      ("inBattle", inBattleJSON)
//    )
//    val playerPartyJSON : JsValue = Json.toJson(playerPartyMap)
//    val sendMapJSON : Map[String, JsValue] = Map("playerParty" -> playerPartyJSON, "otherParties" -> oppInfo)
//
//    Json.stringify(Json.toJson(sendMapJSON))
//  }

//  def sendPlayerJSON() : Unit = {
//    newPlayerJSON = convertingPlayerJSON()
//
//    fromPlayerJSON(newPlayerJSON)
//  }

  //fromMapJSON("""{"mapSize":{"width":3, "height":4}, "tiles":[[{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],[{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],[{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true}]]}""")
  //fromPlayerJSON("""{"playerParty": {"location":{"x":2, "y": 1}, "level":25, "inBattle": false}, "otherParties":[{"location":{"x":2, "y": 2}, "level":15,"inBattle": true}, {"location":{"x":2.43, "y": 0.45}, "level":10,"inBattle": false}, {"location":{"x":12.49, "y": 9.32}, "level":1,"inBattle": false}, {"location":{"x":6.30, "y": 1.43}, "level":5,"inBattle": true}]}""")


  stage = new PrimaryStage{
    width = windowWidth + 10
    height = windowHeight + 35
    title = "THE BEST JRPG YOU HAVE EVER SEEN"
    scene = new Scene() {
      content = List(sceneGraphics)
      addEventHandler(KeyEvent.KEY_PRESSED, new KeyEventHandler(playerCoo))
    }
  }

  val update: Long => Unit = (time: Long) => {
    fromPlayerJSON(playerJSON, playerString)
  }
  // Start Animations. Calls update 60 times per second (takes update as an argument)
  AnimationTimer(update).start()
}
