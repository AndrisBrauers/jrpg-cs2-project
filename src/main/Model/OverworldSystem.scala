package Model

import akka.actor.{Actor, ActorRef}



class OverworldSystem(server: ActorRef) extends Actor {

  val functions : Functions = new Functions(server)

  override def receive: Receive = {
    case received: AddParty => functions.addParty(received.partyId)
      server ! OverworldMap(functions.overworldMapJSON)
      server ! OverworldState(functions.givePlayerJSON(), functions.playerString)
    case received: RemoveParty => functions.removeParty(received.partyId)
    case received: StopParty => functions.stopParty(received.partyId)
    case received: MoveParty => functions.movePlayer(received.partyId, received.x, received.y)
    case Update => functions.update()
      server ! OverworldState(functions.givePlayerJSON(), functions.playerString)
    case receive: BattleEnded => functions.battleEnded(receive.winningPartyId, receive.losingPartyId)
  }

}
