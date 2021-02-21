abstract class Character {
  var currentXP: Int = 0
  var level: Int = 1
  var neededXP: Int = (10 * math.pow(2, this.level)).toInt
  var maxHP: Int = 10
  var currentHP: Int = maxHP

  var attack: Int
  var defense: Int
  var magicAttack: Int
  var magicDefense: Int
  var maxMP: Int
  var currentMP: Int
  var speed: Int  //Is used to understand who goes first
  var battleOp : List[String]

  var alive: Boolean = true

  def increaseStats(): Unit ={
  }

  def gainXP(opponent: Character):Int = {
    opponent.level * 8
  }

  def addXP(XP: Int): Unit ={
    val newXP: Int = XP + this.currentXP
    while(newXP >= this.neededXP) {
      this.level = this.level + 1
      increaseStats()
      this.neededXP = this.neededXP + (10 * math.pow(2, this.level)).toInt
    }
    this.currentXP = newXP
  }

  def takeDamage(hit: Int): Unit = {
    this.currentHP = this.currentHP - (hit.toFloat/this.defense).ceil.toInt
    if (this.currentHP <= 0) {
      this.currentHP = 0
      this.alive = false
    }
  }
  def attackOpp(opponent: Character, attackStrength: Int): Unit = {
    opponent.takeDamage(attackStrength)
    if (opponent.alive == false) {
      gainXP(opponent)
    }
  }
  def magicAttackOpp(opponent: Character, magAttackStrength: Int): Unit ={
    if(this.currentMP >= 3){
      if(magAttackStrength > opponent.magicDefense){
        opponent.takeDamage((magAttackStrength - opponent.magicDefense) * opponent.defense)
      }
      currentMP = currentMP - 3
      if (opponent.alive == false) {
        gainXP(opponent)
      }
    }
  }
  def battleOptions (): List[String] {
  }

  def takeAction(option: String, character: Character): Unit ={
  }

  def basicAttack(opponent: Character): Unit ={
    attackOpp(opponent, this.attack)
  }

  def magicAttack(opponent: Character): Unit ={
    magicAttackOpp(opponent, this.magicAttack)
  }
}
