class Mage extends Character{
  override var attack: Int = 2
  override var defense: Int = 2
  override var magicAttack: Int = 5
  override var magicDefense: Int = 4
  override var maxMP: Int = 10
  override var currentMP: Int = maxMP
  override var speed: Int = 1
  override var battleOp: List[String] = List("Basic Attack", "Magic Attack", "Reduce Defense", "")

  override def increaseStats(): Unit ={
    this.maxHP = this.maxHP + 5
    this.attack = this.attack + 1
    this.defense = this.defense + 1
    this.magicAttack = this.magicAttack + 4
    this.magicDefense = this.magicDefense + 3
    this.maxMP = this.maxMP + 6
    this.speed = this.speed + 1

    if (this.level == 5){
      battleOp = List("Basic Attack", "Magic Attack", "Reduce Defense", "Heal")
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
      reduceDefense(character)
    }
    else if(battleOp(3) == option){
      heal(character)
    }
  }

  def reduceDefense(opponent: Character): Unit ={
    opponent.defense = opponent.defense - this.magicAttack/2
  }
  def heal(ally: Character): Unit ={
    ally.currentHP = ally.currentHP + this.magicAttack/2
  }
}
