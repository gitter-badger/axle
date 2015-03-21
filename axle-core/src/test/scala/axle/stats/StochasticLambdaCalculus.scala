package axle.stats

import org.specs2.mutable.Specification

import axle.game.Dice.die
import spire.optional.unicode.Σ
import spire.implicits.IntAlgebra
import spire.math.Rational
import spire.syntax.literals._

object StochasticLambdaCalculus extends Specification {

  "iffy (stochastic if)" should {
    "map fair boolean to d6 + (d6+d6)" in {

      val distribution =
        iffy(
          binaryDecision(Rational(1, 3)),
          die(6),
          for { a <- die(6); b <- die(6) } yield a + b)

      distribution.probabilityOf(1) must be equalTo Rational(1, 18)

      distribution.probabilityOf(12) must be equalTo Rational(1, 54)

      Σ(distribution.values map distribution.probabilityOf) must be equalTo Rational(1)
    }
  }

  "Monty Hall contestant" should {

    "always pick the other door" in {

      val numDoors = 3

      val prizeDoor = uniformDistribution(1 to numDoors, "prize")

      val chosenDoor = uniformDistribution(1 to numDoors, "chosen")

      def reveal(p: Int, c: Int) =
        uniformDistribution((1 to numDoors).filter(d => d == p || d == c), "reveal")

      def switch(probabilityOfSwitching: Rational, c: Int, r: Int) =
        iffy(
          binaryDecision(probabilityOfSwitching),
          uniformDistribution((1 to numDoors).filter(d => d == r || d == c), "switch"), // switch
          uniformDistribution(Seq(c), "switch") // stay
          )

      // TODO: The relationship between probabilityOfSwitching and outcome can be performed more efficiently and directly.
      val outcome = (probabilityOfSwitching: Rational) => for {
        p <- prizeDoor
        c <- chosenDoor
        r <- reveal(p, c)
        c2 <- switch(probabilityOfSwitching, c, r)
      } yield c2 == p

      // val pos = uniformRealDistribution(Range(r"0", r"1"))
      // pos.probabilityOf(Range(r"3/10", r"4/10")) // should be r"1/10"
      // pos.range
      // val chanceOfWinning = pos map { outcome }
      // cow should also now have a value at pos.min and pos.max

      val chanceOfWinning = (probabilityOfSwitching: Rational) => outcome(probabilityOfSwitching).probabilityOf(true)

      chanceOfWinning(Rational(1)) must be equalTo Rational(1, 2)

      chanceOfWinning(Rational(0)) must be equalTo Rational(1, 3)

      // TODO: p1 > p2 <=> chanceOfWinning(p1) > chanceOfWinning(p2)
      //        aka "is monotonically increasing"
    }
  }

  "bowling" should {
    "work" in {

      case class Bowler(firstRoll: Distribution0[Int, Rational], spare: Distribution0[Boolean, Rational])

      val randomBowler =
        Bowler(
          firstRoll = uniformDistribution(0 to 10, "uniform first roll"),
          spare = binaryDecision(Rational(1, 2)))

      // bad bowler.  50% gutter-ball, even (5%) distribution of 1-10
      val badBowler =
        Bowler(
          firstRoll = ConditionalProbabilityTable0(Map(
            0 -> Rational(5, 10),
            1 -> Rational(1, 20),
            2 -> Rational(1, 20),
            3 -> Rational(1, 20),
            4 -> Rational(1, 20),
            5 -> Rational(1, 20),
            6 -> Rational(1, 20),
            7 -> Rational(1, 20),
            8 -> Rational(1, 20),
            9 -> Rational(1, 20),
            10 -> Rational(1, 20)), "bad first roll"),
          spare = binaryDecision(Rational(1, 10)))

      // decent bowler.  5%  over 0-5, 10% 6, 15% over 7-10
      val decentBowler =
        Bowler(
          firstRoll = ConditionalProbabilityTable0(Map(
            0 -> Rational(1, 20),
            1 -> Rational(1, 20),
            2 -> Rational(1, 20),
            3 -> Rational(1, 20),
            4 -> Rational(1, 20),
            5 -> Rational(1, 20),
            6 -> Rational(1, 10),
            7 -> Rational(3, 20),
            8 -> Rational(3, 20),
            9 -> Rational(3, 20),
            10 -> Rational(3, 20))),
          spare = binaryDecision(Rational(1, 10)))

      // 4%  over 0-6, 12% 7, 20% 8, 30% 9, 30% 10
      val goodBowler = Bowler(
        firstRoll = ConditionalProbabilityTable0(Map(
          0 -> Rational(1, 25),
          1 -> Rational(1, 25),
          2 -> Rational(1, 25),
          3 -> Rational(1, 25),
          4 -> Rational(1, 25),
          5 -> Rational(1, 25),
          6 -> Rational(1, 25),
          7 -> Rational(3, 25),
          8 -> Rational(1, 5),
          9 -> Rational(3, 10),
          10 -> Rational(3, 10))),
        spare = binaryDecision(Rational(8, 10)))

      // TODO handle strikes
      def scoreFrame(first: Int, spare: Boolean, nextFirst: Int): Int =
        first + (if (first < 10 && spare) (10 - first + nextFirst) else 0)

      def scoreDistribution(bowler: Bowler): Distribution0[Int, Rational] = {
        for {
          f1 <- bowler.firstRoll;
          s1 <- bowler.spare;
          f2 <- bowler.firstRoll;
          s2 <- bowler.spare;
          f3 <- bowler.firstRoll;
          s3 <- bowler.spare;
          f4 <- bowler.firstRoll
        } yield scoreFrame(f1, s1, f2) +
          scoreFrame(f2, s2, f3) +
          scoreFrame(f3, s3, f4)
      }

      def scoreDistribution2(bowler: Bowler): Distribution0[Int, Rational] = {

        import bowler._

        val zero: Distribution0[Int, Rational] = ConditionalProbabilityTable0(Map(0 -> Rational(1)))

        (1 to 10).foldLeft(zero)({
          case (incoming, _) => for {
            i <- incoming;
            f1 <- firstRoll;
            s <- spare;
            f2 <- firstRoll // TODO pass this on to next frame
          } yield (i + scoreFrame(f1, s, f2))
        })
      }

      scoreDistribution2(goodBowler) // TODO the probabilities are summing to > 1

      1 must be equalTo 1
    }
  }

}