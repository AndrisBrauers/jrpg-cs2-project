package Model

import akka.actor.ActorRef
import play.api.libs.json.{JsValue, Json}

class Functions(server : ActorRef) {
  var mapWidth : Int = 0
  var mapHeight : Int = 0
  var mapTiles : Array[Boolean] = Array()
  var overworldMapJSON: String = """{"mapSize":{"width":16, "height":11}, "tiles":[
[{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": false},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}],
[{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": false},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true},{"type":"grass", "passable": true}]]}"""

  var partyVelocityMap : Map[String, Array[Double]] = Map()
  var playerString : String = ","
  var playerLocations : Map[String, Array[Double]] = Map()
  var lastUpdateTime: Long = System.nanoTime()
  var currentTime : Long = System.nanoTime()
  var playersLevels : Map[String, Int] = Map()
  var playersFighting : Map[String, Boolean] = Map()
  fromMapJSON(overworldMapJSON)

  def givePlayerJSON(): String ={
    var partyMap : Map[String, JsValue] = Map()

    val str = playerString.replaceFirst(",", "")
    val allPlayerID : Array[String] = str.split(",")

    for(partyId <- allPlayerID){
      val locationMap : Map[String, Double] = Map("x" -> playerLocations(partyId)(0), "y" -> playerLocations(partyId)(1))
      val locationMapJSON : JsValue = Json.toJson(locationMap)
      val levelJSON : JsValue = Json.toJson(playersLevels(partyId))  // This 5 is just a place holder
      val inBattleJSON : JsValue = Json.toJson(playersFighting(partyId))
      val playerPartyMap: Map[String, JsValue] = Map(
        ("location", locationMapJSON),
        ("level", levelJSON),
        ("inBattle", inBattleJSON)
      )
      val playerPartyJSON : JsValue = Json.toJson(playerPartyMap)
      //  val fullPlayerPartyMap : Map[String, JsValue] = Map(ID -> playerPartyJSON)
      partyMap += (partyId -> playerPartyJSON)
    }
    println(Json.stringify(Json.toJson(partyMap)))
    Json.stringify(Json.toJson(partyMap))
  }

  def addParty(ID : String): Unit = {
    partyVelocityMap += (ID -> Array(0,0))
    println("Added: ", ID)
    playerString = playerString + ID + ","
    val random = new scala.util.Random
    var x = random.nextInt().abs % mapWidth
    var y = random.nextInt().abs % mapHeight

    while(!mapTiles(y * mapWidth + x)){
      x = random.nextInt().abs % mapWidth
      y = random.nextInt().abs % mapHeight
    }
    playerLocations += (ID -> Array(x + 0.5, y + 0.5))
    playersLevels += (ID -> 5)
    playersFighting += (ID -> false)
  }

  def removeParty(ID: String): Unit = {
    playersFighting = playersFighting.-(ID)
    playersLevels = playersLevels.-(ID)
    partyVelocityMap = partyVelocityMap.-(ID)
    playerString = playerString.replace("," + ID + ",", ",")
    println(playerString)
  }

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

  def movePlayer(partyID : String, x : Double, y : Double): Unit ={
    partyVelocityMap(partyID)(0) = x
    partyVelocityMap(partyID)(1) = y
  }

  def stopParty(partyID : String): Unit ={
    partyVelocityMap(partyID)(0) = 0
    partyVelocityMap(partyID)(1) = 0
  }

  def update(): Unit = {
    var str = playerString.replaceFirst(",", "")
    var allPlayerID : Array[String] = str.split(",")
    currentTime = System.nanoTime()
    val secondsPassed : Float = (currentTime - lastUpdateTime)
    for(partyID <- allPlayerID){
      var newX : Double = playerLocations(partyID)(0) + secondsPassed/1000000000 * partyVelocityMap(partyID)(0)
      var newY : Double = playerLocations(partyID)(1) + secondsPassed/1000000000 * partyVelocityMap(partyID)(1)
      if(newX > 0.2 && newX < mapWidth - 0.2 && newY > 0.2 && newY < mapHeight - 0.2){
        if(mapTiles((newX + 0.2).toInt + mapWidth * (newY + 0.2).toInt) && mapTiles((newX - 0.2).toInt + mapWidth * (newY + 0.2).toInt) &&
          mapTiles((newX + 0.2).toInt + mapWidth * (newY - 0.2).toInt) && mapTiles((newX - 0.2).toInt + mapWidth * (newY - 0.2).toInt)){
          playerLocations(partyID)(0) = newX
          playerLocations(partyID)(1) = newY
        }
      }
      for(i <- 1 to allPlayerID.length){
        for(j <- 1 to allPlayerID.length){
          val res : Boolean = checkForCollision(allPlayerID(i - 1), allPlayerID(j - 1))
          playersFighting -= allPlayerID(i - 1)
          playersFighting += (allPlayerID(i - 1) -> res)
          playersFighting -= allPlayerID(j - 1)
          playersFighting += (allPlayerID(j - 1) -> res)
        }
      }

    }
    lastUpdateTime = currentTime
  }

  def checkForCollision(party1 : String, party2: String): Boolean ={
    val distance : Double = Math.sqrt(Math.pow(playerLocations(party2)(0) - playerLocations(party1)(0), 2) + Math.pow(playerLocations(party2)(1) - playerLocations(party1)(1), 2))
    if (distance <= 0.4 && party1 != party2){
      stopParty(party1)
      stopParty(party2)
      if(!playersFighting(party1) && !playersFighting(party2)){
        server ! BattleStarted(party1, party2)
      }
      true
    }
    else {
      false
    }
  }

  def battleEnded(winners : String, losers: String): Unit ={
    playersFighting -= winners
    playersFighting += (winners -> false)
    playersFighting -= losers
    playersFighting += (losers -> false)

    val random = new scala.util.Random
    var x = random.nextInt().abs % mapWidth
    var y = random.nextInt().abs % mapHeight

    while(!mapTiles(y * mapWidth + x)){
      x = random.nextInt().abs % mapWidth
      y = random.nextInt().abs % mapHeight
    }
    playerLocations(losers)(0) = x + 0.5
    playerLocations(losers)(1) = y + 0.5
    println("NEW LOCATIONS")
  }
}
