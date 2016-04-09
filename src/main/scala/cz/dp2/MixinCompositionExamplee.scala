package cz.dp2

trait CardPlayer {
  def run: Unit
}

trait BlackJackPlayer extends CardPlayer {
  override def run = println("I am blackjack player")
}

trait PokerPlayer extends CardPlayer {
  override def run = println("I am poker player")
}

class Player extends BlackJackPlayer with PokerPlayer

object MixinCompositionExamplee {
  def main(args: Array[String]): Unit = {
    new Player run
  }
}

