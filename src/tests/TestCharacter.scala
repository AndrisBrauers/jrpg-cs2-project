import org.scalatest._

class TestCharacter extends FunSuite {

  test("Test takeDamage") {
    val player1: Character = new Warrior()
    val player2: Character = new Warrior()
    val player3: Character = new Warrior()
    val player4: Character = new Warrior()

    player1.takeDamage(1)

    assert(player1.currentHP === 9)
    assert(player1.alive === true)

    player2.takeDamage(20)

    assert(player2.currentHP === 5)
    assert(player2.alive === true)

    player3.takeDamage(40)

    assert(player3.currentHP === 0)
    assert(player3.alive === false)

    player4.takeDamage(36)

    assert(player4.currentHP === 1)
    assert(player4.alive === true)

    //Checking if HP reduces when hit again

    player1.takeDamage(5)

    assert(player1.currentHP === 7)
    assert(player1.alive === true)
  }

  test("Basic attack"){
    val player1: Character = new Warrior()
    val player2: Character = new Warrior()

    player1.attackOpp(player2, player1.attack)

    assert(player2.currentHP === 9)
  }


  test("Magic attack"){
    val player1: Character = new Warrior()
    val player2: Character = new Warrior()

    player1.magicAttackOpp(player2, player1.magicAttack)

    assert(player2.currentHP === 9)
    assert(player1.currentMP === 3)

    player1.magicAttackOpp(player2, player1.magicAttack)

    assert(player2.currentHP === 8)
    assert(player1.currentMP === 0)

    player1.magicAttackOpp(player2, player1.magicAttack)

    assert(player2.currentHP === 8)
    assert(player1.currentMP === 0)
  }

  test("Gaining XP"){
    val player1: Character = new Warrior()
    val player2: Character = new Warrior()

    player1.gainXP(player2)
    assert(player1.gainXP(player2) === 8)

    player2.level = 3
    assert(player1.gainXP(player2)=== 24)

    player2.level = 5
    assert(player1.gainXP(player2) === 40)
  }

  test("Adding XP") {
    val player1: Character = new Warrior()
    val player2: Character = new Warrior()
    val player3: Character = new Warrior()

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
    //Check if stats increase (In new test file)
//    assert(player2.maxHP === 15)
//    assert(player2.attack === 4)
//    assert(player2.defense === 4)
//    assert(player2.magicAttack === 7)
//    assert(player2.magicDefense === 4)
//    assert(player2.maxMP === 15)
//    assert(player2.speed === 3)

    //Check when level up multiple levels

    player3.addXP(160)

    assert(player3.currentXP === 160)
    assert(player3.level === 4)
    assert(player3.neededXP === 300)
    //Check if stats increase (In new test file)
//    assert(player3.maxHP === 25)
//    assert(player3.attack === 8)
//    assert(player3.defense === 8)
//    assert(player3.magicAttack === 13)
//    assert(player3.magicDefense === 8)
//    assert(player3.maxMP === 25)
//    assert(player3.speed === 5)
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

    var char1: Character = new Warrior()
    char1.level = 1
    var char2: Character = new Warrior()
    char2.level = 2
    var char3: Character = new Warrior()
    char3.level = 3
    var char4: Character = new Warrior()
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

  test ("is Winner"){
    var party1: Party = new Party()
    var party2: Party = new Party()

    party1.isWinner(party2)
    //When party is not dead
    assert(party1.allCharacters(0).currentXP === 0)
    assert(party1.allCharacters(1).currentXP === 0)
    assert(party1.allCharacters(2).currentXP === 0)

    for(char <- party2.allCharacters){
      char.alive = false
    }

    party1.isWinner(party2)
    //When party is dead
    assert(party1.allCharacters(0).currentXP === 8)
    assert(party1.allCharacters(1).currentXP === 8)
    assert(party1.allCharacters(2).currentXP === 8)
  }
}
