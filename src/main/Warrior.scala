class Warrior extends Character {

  override var attack: Int = 4
  override var defense: Int = 4
  override var magicAttack: Int = 2
  override var magicDefense: Int = 1
  override var maxMP: Int = 6
  override var currentMP: Int = maxMP
  override var speed: Int = 1
  override var battleOp: List[String] = List("Basic Attack", "Magic Attack", "Power Attack", "")

  override def increaseStats(): Unit ={
    this.maxHP = this.maxHP + 6
    this.attack = this.attack + 3
    this.defense = this.defense + 3
    this.magicAttack = this.magicAttack + 1
    this.magicDefense = this.magicDefense + 1
    this.maxMP = this.maxMP + 3
    this.speed = this.speed + 1

    if (this.level == 5){
      battleOp = List("Basic Attack", "Magic Attack", "Power Attack", "Slash Attack")
    }
  }

  override def battleOptions (): List[String] = {
    this.battleOp
  }

  override def takeAction(option: String, character: Character): Unit ={
    if(battleOp(0) == option){
      basicAttack(character)
    }
    else if(battleOp(1) == option){
      magicAttack(character)
    }
    else if(battleOp(2) == option){
      powerAttack(character)
    }
    else if(battleOp(3) == option){
      slashAttack(character)
    }
  }

  def powerAttack(opponent: Character): Unit ={
    attackOpp(opponent, this.attack * 2)
    this.currentHP = this.currentHP - this.maxHP/5
  }
  def slashAttack(opponent: Character): Unit ={
    if(opponent.speed < this.speed){
      for(x <- 1 to this.speed){
        attackOpp(opponent, this.attack/2)
      }
    }
  }

}
