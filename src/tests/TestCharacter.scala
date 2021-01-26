import org.scalatest._

class TestCharacter extends FunSuite {
  test("Constructor for Character") {
    val Player1: Character = new Character()

    assert(Player1.level === 1)
    assert(Player1.currentXP === 0)
    assert(Player1.neededXP === 20)
    assert(Player1.maxHP === 10)
    assert(Player1.currentHP === 10)
    assert(Player1.basicAttack === 2)
    assert(Player1.defense === 2)
    assert(Player1.specialAttack === 5)
    assert(Player1.magicAttack === 4)
    assert(Player1.magicDefense === 2)
    assert(Player1.maxMP === 10)
    assert(Player1.currentMP === 10)
    assert(Player1.speed === 2)
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

  test("Gaining XP"){
    val player1: Character = new Character()
    val player2: Character = new Character()

    player1.gainXP(player2)
    assert(player1.gainXP(player2) === 8)

    player2.level = 3
    assert(player1.gainXP(player2)=== 24)

    player2.level = 5
    assert(player1.gainXP(player2) === 40)
  }

  test("Adding XP") {
    val player1: Character = new Character()
    val player2: Character = new Character()
    val player3: Character = new Character()

    //Check if level do not increase
    player1.addXP(8)

    assert(player1.currentXP === 8)
    assert(player1.level === 1)
    assert(player1.neededXP == 20)

    //Check if level do increase

    player2.addXP(24)

    assert(player2.currentXP === 24)
    assert(player2.level === 2)
    assert(player2.neededXP === 60)
    //Check if stats increase
    assert(player2.maxHP === 15)
    assert(player2.basicAttack === 4)
    assert(player2.specialAttack === 9)
    assert(player2.defense === 4)
    assert(player2.magicAttack === 7)
    assert(player2.magicDefense === 4)
    assert(player2.maxMP === 15)
    assert(player2.speed === 3)

    //Check when level up multiple levels

    player3.addXP(160)

    assert(player3.currentXP === 160)
    assert(player3.level === 4)
    assert(player3.neededXP === 300)
    //Check if stats increase
    assert(player3.maxHP === 25)
    assert(player3.basicAttack === 8)
    assert(player3.specialAttack === 17)
    assert(player3.defense === 8)
    assert(player3.magicAttack === 13)
    assert(player3.magicDefense === 8)
    assert(player3.maxMP === 25)
    assert(player3.speed === 5)
  }

  test ("Dividing XP to party") {
    //Basic situation

    var party1: Party = new Party()
    var party2: Party = new Party() // Party 2 is assume to be dead

    party1.divideXP(party2)

    assert(party1.allCharacters(0).currentXP === 8)
    assert(party1.allCharacters(1).currentXP === 8)
    assert(party1.allCharacters(2).currentXP === 8)

    //Situation where party2 has different number and leveled characters

    var char1: Character = new Character()
    char1.level = 1
    var char2: Character = new Character()
    char2.level = 2
    var char3: Character = new Character()
    char3.level = 3
    var char4: Character = new Character()
    char4.level = 4
    party2.allCharacters = List(char1, char2, char3, char4)

    for(x <- party1.allCharacters){
      x.currentXP = 0
    }

    party1.divideXP(party2)

    assert(party1.allCharacters(0).currentXP === 26)
    assert(party1.allCharacters(1).currentXP === 26)
    assert(party1.allCharacters(2).currentXP === 26)

    //Situation where party1 has one character dead

    char1.alive = false
    party1.allCharacters = List(char1, char2, char3)

    party1.divideXP(party2)

    assert(party1.allCharacters(0).currentXP === 0)
    assert(party1.allCharacters(1).currentXP === 40)
    assert(party1.allCharacters(2).currentXP === 40)
  }

}
