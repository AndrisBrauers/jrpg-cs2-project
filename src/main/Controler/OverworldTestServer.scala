package Controler

import Model.{AddParty, BattleEnded, BattleStarted, MoveParty, OverworldMap, OverworldState, OverworldSystem, RemoveParty, StopParty, Update}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import play.api.libs.json.Json


class OverworldTestServer() extends Actor {
  var ID : Int = 2
  val overworldSystem: ActorRef = this.context.actorOf(Props(classOf[OverworldSystem], self))
  var socketToPartyId: Map[SocketIOClient, String] = Map()
  val config: Configuration = new Configuration {
    setHostname("localhost")
    setPort(8080)
  }

  overworldSystem ! AddParty("party_1")
  val server: SocketIOServer = new SocketIOServer(config)

  var overworldMapJSON: String = ""

  server.addConnectListener(
    (socket: SocketIOClient) => {
      val partyId = "party_" + ID.toString
      ID += 1
      socket.sendEvent("yourPartyId", partyId)
      socket.sendEvent("overworldMap", overworldMapJSON)
      socketToPartyId += socket -> partyId
      overworldSystem ! AddParty(partyId)
    }
  )

  server.addDisconnectListener(
    (socket: SocketIOClient) => {
      overworldSystem ! RemoveParty(socketToPartyId(socket))
      socketToPartyId -= socket
    }
  )


  server.addEventListener("overworldMovement", classOf[String],
    (socket: SocketIOClient, data: String, _: AckRequest) => {
      val partyId = socketToPartyId(socket)
      val dataParsed = Json.parse(data)
      val x = (dataParsed \ "x").as[Double]
      val y = (dataParsed \ "y").as[Double]
      if (Math.abs(x) < 0.001 && Math.abs(y) < 0.001) {
        overworldSystem ! StopParty(partyId)
      } else {
        overworldSystem ! MoveParty(partyId, x, y)
      }
    }
  )

  server.addEventListener("addPlayer", classOf[String],
    (socket: SocketIOClient, data: String, _: AckRequest) => {
        overworldSystem ! AddParty("party_" + ID.toString)
        ID += 1
    }
  )
  server.addEventListener("removePlayer", classOf[String],
    (socket: SocketIOClient, data: String, _: AckRequest) => {
      overworldSystem ! RemoveParty("party_" + (ID-1).toString)
      ID -= 1
    }
  )
  server.addEventListener("movePlayers", classOf[String],
    (socket: SocketIOClient, data: String, _: AckRequest) => {
      for(x <- 1 to ID){
        overworldSystem ! MoveParty("party_1", 1, 0)
      }
    }
  )
  server.addEventListener("stopPlayers", classOf[String],
    (socket: SocketIOClient, data: String, _: AckRequest) => {
        overworldSystem ! StopParty("party_1")
    }
  )

  server.start()

  override def postStop(): Unit = {
    println("stopping server")
    server.stop()
  }


  import context.dispatcher

  import scala.concurrent.duration._

  override def receive: Receive = {


    case update: Update =>
      overworldSystem ! Update


    /*** From Model.OverworldSystem ***/
    case overworldMap: OverworldMap => overworldMapJSON = overworldMap.mapJSON
    case overworldState: OverworldState =>
      server.getBroadcastOperations.sendEvent("overworldState", overworldState.overworldStateJSON, overworldState.overworldPlayers)
    case battleStarted: BattleStarted =>
      // Simulate the battle by randomly selecting a winner and sending the result back to the overworld in 5 seconds
      val battleEnd: BattleEnded = if (Math.random() < 0.5) {
        BattleEnded(battleStarted.partyId1, battleStarted.partyId2)
      } else {
        BattleEnded(battleStarted.partyId2, battleStarted.partyId1)
      }

      context.system.scheduler.scheduleOnce(5000.milliseconds, overworldSystem, battleEnd)
  }

}



object OverworldTestServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val server = actorSystem.actorOf(Props(classOf[OverworldTestServer]))

    actorSystem.scheduler.schedule(0.milliseconds, 33.milliseconds, server, Update(System.nanoTime()))
  }
}