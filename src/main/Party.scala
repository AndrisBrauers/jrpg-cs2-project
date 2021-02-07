class Party {
  var allCharacters: List[Character] = List(new Warrior, new Mage, new Warrior)

  def divideXP(oppParty: Party){
    var aliveChar: Int = 0
    var allXP: Int = 0
    for(char <- oppParty.allCharacters){
      allXP = allXP + this.allCharacters(0).gainXP(char)
    }
    for(char <- this.allCharacters){
      if (char.alive == true){
        aliveChar = aliveChar + 1
      }
    }
    for(char <- allCharacters){
      if (char.alive == true){
        char.addXP(allXP/aliveChar)
      }
    }
  }

  def isWinner(oppParty: Party){
    var dead: Int = 0
    for (char <- oppParty.allCharacters){
      if(char.alive == false){
        dead = dead + 1
      }
    }
    if (dead == oppParty.allCharacters.length){
      divideXP(oppParty)
    }
  }
}
