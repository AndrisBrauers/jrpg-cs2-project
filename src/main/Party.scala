class Party {
  var allCharacters: List[Character] = List(new Character, new Character, new Character)

  def divideXP(oppParty: Party){
    var aliveChar: Int = 0
    var allXP: Int = 0
    for(char <- oppParty.allCharacters){
      allXP = allXP + this.allCharacters(0).gainXP(char)
    }
    for(char <- allCharacters){
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
}
