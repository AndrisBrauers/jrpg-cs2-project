import org.scalatest._

class TestCharacter extends FunSuite {
  test("Constructor for Character") {

    val Player1: Character = new Character()

    assert(Player1.maxHP === 10)
    assert(Player1.currentHP === 10)
    assert(Player1.basicAttack === 2)
    assert(Player1.defense === 2)
    assert(Player1.specialAttack === 5)
    assert(Player1.magicAttack === 4)
    assert(Player1.magicDefense === 2)
    assert(Player1.maxMP === 10)
    assert(Player1.currentMP === 10)
    assert(Player1.speed === 3)
    assert(Player1.alive === true)
  }

  test("Test takeDamage") {
    val player1: Character = new Character()
    val player2: Character = new Character()
    val player3: Character = new Character()
    val player4: Character = new Character()

    player1.takeDamage(1)

    assert(player1.currentHP === 9)
    assert(player1.alive === true)

    player2.takeDamage(20)

    assert(player2.currentHP === 0)
    assert(player2.alive === false)

    player3.takeDamage(30)

    assert(player3.currentHP === 0)
    assert(player3.alive === false)

    player4.takeDamage(18)

    assert(player4.currentHP === 1)
    assert(player4.alive === true)

    //Checking if HP reduces when hit again

    player1.takeDamage(5)

    assert(player1.currentHP === 6)
    assert(player1.alive === true)
  }

  test("Basic attack"){
    val player1: Character = new Character()
    val player2: Character = new Character()

    player1.basicAttackOpp(player2)

    assert(player2.currentHP === 9)
  }

  test("Special attack"){
    val player1: Character = new Character()
    val player2: Character = new Character()

    player1.specialAttackOpp(player2)

    assert(player2.currentHP === 7)
  }

  test("Magic attack"){
    val player1: Character = new Character()
    val player2: Character = new Character()

    player1.magicAttackOpp(player2)

    assert(player2.currentHP === 8)
    assert(player1.currentMP === 7)

    player1.magicAttackOpp(player2)

    assert(player2.currentHP === 6)
    assert(player1.currentMP === 4)

    player1.magicAttackOpp(player2)

    assert(player2.currentHP === 4)
    assert(player1.currentMP === 1)

    player1.magicAttackOpp(player2)

    assert(player2.currentHP === 4)
    assert(player1.currentMP === 1)
  }
}
