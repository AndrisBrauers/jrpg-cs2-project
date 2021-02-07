import org.scalatest._

class TestCharacterTypes extends FunSuite{
  test("Initial stats: Warrior"){
    val Player1: Character = new Warrior()
    assert(Player1.level === 1)
    assert(Player1.currentXP === 0)
    assert(Player1.neededXP === 20)
    assert(Player1.maxHP === 10)
    assert(Player1.currentHP === 10)
    assert(Player1.attack === 4)
    assert(Player1.defense === 4)
    assert(Player1.magicAttack === 2)
    assert(Player1.magicDefense === 1)
    assert(Player1.maxMP === 6)
    assert(Player1.currentMP === 6)
    assert(Player1.speed === 1)
    assert(Player1.alive === true)
  }

  test("Initial stats: Mage"){
    val Player1: Character = new Mage()
    assert(Player1.level === 1)
    assert(Player1.currentXP === 0)
    assert(Player1.neededXP === 20)
    assert(Player1.maxHP === 10)
    assert(Player1.currentHP === 10)
    assert(Player1.attack === 2)
    assert(Player1.defense === 2)
    assert(Player1.magicAttack === 5)
    assert(Player1.magicDefense === 4)
    assert(Player1.maxMP === 10)
    assert(Player1.currentMP === 10)
    assert(Player1.speed === 1)
    assert(Player1.alive === true)
  }

  test("Initial battle options: Warrior"){
    val Player1: Character = new Warrior()
    assert(Player1.battleOptions() == List("Basic Attack", "Magic Attack", "Power Attack", ""))
  }

  test("Initial battle options: Mage"){
    val Player1: Character = new Mage()
    assert(Player1.battleOptions() == List("Basic Attack", "Magic Attack", "Reduce Defense", ""))
  }

  test("Stats after leveling up: Warrior"){
    val Player1: Character = new Warrior()
    Player1.addXP(24)

    assert(Player1.level === 2)
    assert(Player1.currentXP === 24)
    assert(Player1.neededXP === 60)
    assert(Player1.maxHP === 16)
    assert(Player1.currentHP === 10)
    assert(Player1.attack === 7)
    assert(Player1.defense === 7)
    assert(Player1.magicAttack === 3)
    assert(Player1.magicDefense === 2)
    assert(Player1.maxMP === 9)
    assert(Player1.currentMP === 6)
    assert(Player1.speed === 2)
    assert(Player1.alive === true)
  }

  test("Stats after leveling up: Mage"){
    val Player1: Character = new Mage()
    Player1.addXP(24)

    assert(Player1.level === 2)
    assert(Player1.currentXP === 24)
    assert(Player1.neededXP === 60)
    assert(Player1.maxHP === 15)
    assert(Player1.currentHP === 10)
    assert(Player1.attack === 3)
    assert(Player1.defense === 3)
    assert(Player1.magicAttack === 9)
    assert(Player1.magicDefense === 7)
    assert(Player1.maxMP === 16)
    assert(Player1.currentMP === 10)
    assert(Player1.speed === 2)
    assert(Player1.alive === true)
  }

  test("Battle options after leveling up: Warrior"){
    val Player1: Character = new Warrior()
    Player1.addXP(1000)
    assert(Player1.battleOptions() == List("Basic Attack", "Magic Attack", "Power Attack", "Slash Attack"))
  }

  test("Battle options after leveling up: Mage"){
    val Player1: Character = new Mage()
    Player1.addXP(1000)
    assert(Player1.battleOptions() == List("Basic Attack", "Magic Attack", "Reduce Defense", "Heal"))
  }

  test("Battle options: Warrior"){
    val Player1: Character = new Warrior()
    val Player2: Character = new Warrior()

    Player1.takeAction("Basic Attack", Player2)
    assert(Player2.currentHP == 9)

    Player1.takeAction("Magic Attack", Player2)
    assert(Player2.currentHP == 8)

    Player1.takeAction("Power Attack", Player2)
    assert(Player2.currentHP == 6)
    assert(Player1.currentHP == 8)

    Player1.takeAction("Slash Attack", Player2)
    assert(Player2.currentHP == 6)

    Player1.addXP(1000)
    assert(Player1.level == 6)
    assert(Player1.attack == 19)

    Player2.currentHP = 100
    Player1.takeAction("Slash Attack", Player2)
    println(Player1.speed)
    println(Player1.attack/2)
    assert(Player2.currentHP == 82)
  }

  test("Battle options: Mage"){
    val Player1: Character = new Mage()
    val Player2: Character = new Warrior()

    Player1.takeAction("Basic Attack", Player2)
    assert(Player2.currentHP == 9)

    Player1.takeAction("Magic Attack", Player2)
    assert(Player2.currentHP == 5)

    Player1.takeAction("Reduce Defense", Player2)
    assert(Player2.defense == 2)

    Player1.takeAction("Heal", Player2)
    assert(Player2.currentHP == 5)

    Player1.addXP(1000)
    assert(Player1.level == 6)
    assert(Player1.magicAttack == 25)

    Player1.currentHP = 10
    Player1.takeAction("Heal", Player1)
    assert(Player1.currentHP == 22)
  }
}



