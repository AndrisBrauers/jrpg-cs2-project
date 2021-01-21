class Character {
  var maxHP: Int = 10
  var currentHP: Int = maxHP
  var basicAttack: Int = 2
  var specialAttack: Int = 5
  var defense: Int = 2
  var magicAttack: Int = 4
  var magicDefense: Int = 2
  var maxMP: Int = 10
  var currentMP: Int = maxMP
  var speed: Int = 3  //Is used to understand who goes first
  var alive: Boolean = true

  def takeDamage(hit: Int) {
    this.currentHP = this.currentHP - (hit.toFloat/this.defense).ceil.toInt
    if (this.currentHP <= 0) {
      this.currentHP = 0
      this.alive = false
    }
  }

  def basicAttackOpp(opponent: Character) {
    opponent.takeDamage(this.basicAttack)
  }
  def specialAttackOpp(opponent: Character) {
    opponent.takeDamage(this.specialAttack)
  }
  def magicAttackOpp(opponent: Character) {
    if(this.currentMP >= 3){
      if(this.magicAttack > opponent.magicDefense){
        opponent.takeDamage((this.magicAttack - opponent.magicDefense) * opponent.defense)
      }
      currentMP = currentMP - 3
    }
  }
}
