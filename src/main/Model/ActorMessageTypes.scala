package Model

import play.api.libs.json.JsValue

// These are recommended message types, though you are not required to use these exactly.
// You may use any message types/formats as long as you complete the required functionality

case class AddParty(partyId: String)
case class RemoveParty(partyId: String)
case class BattleStarted(partyId1: String, partyId2: String)
case class BattleState(partyId1:String, partyJSON1: JsValue, partyId2:String, partyJSON2: JsValue)
case class Update(timestamp: Long)

// Over world
case class BattleEnded(winningPartyId: String, losingPartyId: String)
case class MoveParty(partyId: String, x: Double, y: Double)
case class StopParty(partyId: String)
case class OverworldMap(mapJSON: String)
case class OverworldState(overworldStateJSON: String, overworldPlayers : String)



// Authentication
case class Register(username: String, password: String)
case class RegistrationResult(username: String, registered: Boolean, message: String)
case class Login(username: String, password: String)
case class SaveGame(username: String, charactersJSON: String)
case class FailedLogin(username: String, message: String)
case class Authenticated(username: String, charactersJSON: String)