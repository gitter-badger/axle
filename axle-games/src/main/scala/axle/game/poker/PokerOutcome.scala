package axle.game.poker

import axle.Show
import axle.game.Outcome
import axle.string

object PokerOutcome {

  implicit def showPokerOutcome: Show[PokerOutcome] = new Show[PokerOutcome] {
    def text(po: PokerOutcome): String = {
      import po._
      "Winner: " + winner.get.description + "\n" +
        "Hand  : " + hand.map(h => string(h) + " " + h.description).getOrElse("not shown") + "\n"
    }
  }
}

case class PokerOutcome(winner: Option[PokerPlayer], hand: Option[PokerHand])(implicit ev: Poker)
  extends Outcome[Poker] {

  implicit def game = ev
}
