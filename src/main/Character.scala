class Character {
  var currentXP: Int = 0
  var level: Int = 1
  var neededXP: Int = (10 * math.pow(2, this.level)).toInt
  var maxHP: Int = 10
  var currentHP: Int = maxHP
  var basicAttack: Int = 2
  var specialAttack: Int = 5
  var defense: Int = 2
  var magicAttack: Int = 4
  var magicDefense: Int = 2
  var maxMP: Int = 10
  var currentMP: Int = maxMP
  var speed: Int = 2  //Is used to understand who goes first
  var alive: Boolean = true

  def increaseStats() {
    this.maxHP = this.maxHP + 5
    this.basicAttack = this.basicAttack + 2
    this.specialAttack = this.specialAttack + 4
    this.defense = this.defense + 2
    this.magicAttack = this.magicAttack + 3
    this.magicDefense = this.magicDefense + 2
    this.maxMP = this.maxMP + 5
    this.speed = this.speed + 1
  }

  def gainXP(opponent: Character):Int = {
    opponent.level * 8
  }

  def addXP(XP: Int) {
    var newXP: Int = XP + this.currentXP
    while(newXP >= this.neededXP) {
      this.level = this.level + 1
      increaseStats()
      this.neededXP = this.neededXP + (10 * math.pow(2, this.level)).toInt
    }
    this.currentXP = newXP
  }

  def takeDamage(hit: Int) {
    this.currentHP = this.currentHP - (hit.toFloat/this.defense).ceil.toInt
    if (this.currentHP <= 0) {
      this.currentHP = 0
      this.alive = false
    }
  }
  def basicAttackOpp(opponent: Character) {
    opponent.takeDamage(this.basicAttack)
    if (opponent.alive == false) {
      gainXP(opponent)
    }
  }
  def specialAttackOpp(opponent: Character) {
    opponent.takeDamage(this.specialAttack)
    if (opponent.alive == false) {
      gainXP(opponent)
    }
  }
  def magicAttackOpp(opponent: Character) {
    if(this.currentMP >= 3){
      if(this.magicAttack > opponent.magicDefense){
        opponent.takeDamage((this.magicAttack - opponent.magicDefense) * opponent.defense)
      }
      currentMP = currentMP - 3
      if (opponent.alive == false) {
        gainXP(opponent)
      }
    }
  }
}
