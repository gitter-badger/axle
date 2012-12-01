
import axle.stats._

object Statistics {

  val fairCoin = coin()                           //> fairCoin  : axle.stats.RandomVariable0[Symbol] = RandomVariable0(coin,Some(Ve
                                                  //| ctor('HEAD, 'TAIL)),Some(axle.stats.ConditionalProbabilityTable0@19e3118a))
  val biasedCoin = coin(0.9)                      //> biasedCoin  : axle.stats.RandomVariable0[Symbol] = RandomVariable0(coin,Some(
                                                  //| Vector('HEAD, 'TAIL)),Some(axle.stats.ConditionalProbabilityTable0@4ac9131c))
                                                  //| 
  fairCoin.observe                                //> res0: Symbol = 'TAIL

  import collection._
  import axle.visualize._

  val d6a = die(6)                                //> d6a  : axle.stats.RandomVariable0[Int] = RandomVariable0(d6,Some(Range(1, 2,
                                                  //|  3, 4, 5, 6)),Some(axle.stats.ConditionalProbabilityTable0@6fc5f743))
  val d6b = die(6)                                //> d6b  : axle.stats.RandomVariable0[Int] = RandomVariable0(d6,Some(Range(1, 2,
                                                  //|  3, 4, 5, 6)),Some(axle.stats.ConditionalProbabilityTable0@2dec8909))

  val hist = new immutable.TreeMap[Int, Int]() ++ (0 until 10000).map(i => d6a.observe + d6b.observe).map(v => (v, 1)).groupBy(_._1).map({ case (k, v) => (k, v.map(_._2).sum) })
                                                  //> hist  : scala.collection.immutable.TreeMap[Int,Int] = Map(2 -> 291, 3 -> 578
                                                  //| , 4 -> 799, 5 -> 1100, 6 -> 1393, 7 -> 1706, 8 -> 1407, 9 -> 1130, 10 -> 814
                                                  //| , 11 -> 522, 12 -> 260)

  val plot = Plot(List(("count", hist)), connect = true, drawKey = false, xAxis = 0, yAxis = 0, title = Some("d6 + d6"))
                                                  //> plot  : axle.visualize.Plot[Int,Nothing,Int,Nothing] = Plot(List((count,Map(
                                                  //| 2 -> 291, 3 -> 578, 4 -> 799, 5 -> 1100, 6 -> 1393, 7 -> 1706, 8 -> 1407, 9 
                                                  //| -> 1130, 10 -> 814, 11 -> 522, 12 -> 260))),true,false,700,600,50,4,Some(d6 
                                                  //| + d6),0,None,0,None)

  // show(plot)

  utfD6().observe                                 //> res1: Symbol = '?

  // Probability

  val fairFlip1 = coin()                          //> fairFlip1  : axle.stats.RandomVariable0[Symbol] = RandomVariable0(coin,Some(
                                                  //| Vector('HEAD, 'TAIL)),Some(axle.stats.ConditionalProbabilityTable0@52f6438d)
                                                  //| )
  val fairFlip2 = coin()                          //> fairFlip2  : axle.stats.RandomVariable0[Symbol] = RandomVariable0(coin,Some(
                                                  //| Vector('HEAD, 'TAIL)),Some(axle.stats.ConditionalProbabilityTable0@25cd0888)
                                                  //| )

  P(fairFlip1 eq 'HEAD)()                         //> res2: Double = 0.5

  P((fairFlip1 eq 'HEAD) and (fairFlip2 eq 'HEAD))() // ∧
                                                  //> res3: Double = 0.25

  P((fairFlip1 eq 'HEAD) or (fairFlip2 eq 'HEAD))() // ∨
                                                  //> res4: Double = 0.75

  P((fairFlip1 eq 'HEAD) | (fairFlip2 eq 'TAIL))()//> res5: Double = 0.5

  val utfD6a = utfD6()                            //> utfD6a  : axle.stats.RandomVariable0[Symbol] = RandomVariable0(UTF d6,Some(V
                                                  //| ector('?, '?, '?, '?, '?, '?)),Some(axle.stats.ConditionalProbabilityTable0@
                                                  //| 42552c))
  val utfD6b = utfD6()                            //> utfD6b  : axle.stats.RandomVariable0[Symbol] = RandomVariable0(UTF d6,Some(V
                                                  //| ector('?, '?, '?, '?, '?, '?)),Some(axle.stats.ConditionalProbabilityTable0@
                                                  //| 2e5bbd6))

  // P((utfD6a ne '⚃))()

  // P((utfD6a eq '⚃) and (utfD6b eq '⚃))()

  // Entropy

  entropy(biasedCoin)                             //> res6: axle.quanta.Information.Q = 0.46899559358928117 b

  H(d6a)                                          //> res7: axle.quanta.Information.Q = 2.584962500721156 b

  H(fairCoin)                                     //> res8: axle.quanta.Information.Q = 1.0 b

  // Visualize the relationship of a coin's bias to its entropy with this code snippet.

  import collection._
  import axle.stats._
  import axle.quanta.Information._
  import axle.visualize._
  import Plottable._

  val hm = new immutable.TreeMap[Double, Q]() ++ (0 to 100).map(i => (i / 100.0, entropy(coin(i / 100.0)))).toMap
                                                  //> hm  : scala.collection.immutable.TreeMap[Double,axle.quanta.Information.Q] 
                                                  //| = Map(0.0 -> 0.0 b, 0.01 -> 0.08079313589591118 b, 0.02 -> 0.14144054254182
                                                  //| 067 b, 0.03 -> 0.19439185783157623 b, 0.04 -> 0.24229218908241482 b, 0.05 -
                                                  //| > 0.28639695711595625 b, 0.06 -> 0.32744491915447627 b, 0.07 -> 0.365923650
                                                  //| 90022333 b, 0.08 -> 0.4021791902022729 b, 0.09 -> 0.4364698170641029 b, 0.1
                                                  //|  -> 0.4689955935892812 b, 0.11 -> 0.499915958164528 b, 0.12 -> 0.5293608652
                                                  //| 873644 b, 0.13 -> 0.5574381850279891 b, 0.14 -> 0.584238811642856 b, 0.15 -
                                                  //| > 0.6098403047164004 b, 0.16 -> 0.6343095546405662 b, 0.17 -> 0.65770477874
                                                  //| 42195 b, 0.18 -> 0.6800770457282798 b, 0.19 -> 0.7014714598838974 b, 0.2 ->
                                                  //|  0.7219280948873623 b, 0.21 -> 0.7414827399312737 b, 0.22 -> 0.760167502961
                                                  //| 9657 b, 0.23 -> 0.7780113035465377 b, 0.24 -> 0.7950402793845223 b, 0.25 ->
                                                  //|  0.8112781244591328 b, 0.26 -> 0.8267463724926178 b, 0.27 -> 0.841464636208
                                                  //| 1757 b, 0.28 -> 0.8554508105601307 b, 0.29 -> 0.8687212463394045 b, 0.3 -> 
                                                  //| 0.8812908992306927 b, 0.31 -> 0.8931734583778568 b, 0.32 -> 0.9043814577244
                                                  //| 941 b, 0.33 -> 0.9149263727797275 b, 0.34 -> 0.9248187049730301 b, 0.35 -> 
                                                  //| 0.934068055375491 b, 0.36 -> 0.9426831892554922 b, 0.37 -> 0.95067209268706
                                                  //| 59 b, 0.38 -> 0.9580420222262995 b, 0.39 -> 0.9647995485050872 b, 0.4 -> 0.
                                                  //| 9709505944546686 b, 0.41 -> 0.976500468757824 b, 0.42 -> 0.9814538950336535
                                                  //|  b, 0.43 -> 0.9858150371789198 b, 0.44 -> 0.9895875212220557 b, 0.45 -> 0.9
                                                  //| 927744539878084 b, 0.46 -> 0.9953784388202258 b, 0.47 -> 0.9974015885677396
                                                  //|  b, 0.48 -> 0.9988455359952018 b, 0.49 -> 0.9997114417528099 b, 0.5 -> 1.0 
                                                  //| b, 0.51 -> 0.9997114417528099 b, 0.52 -> 0.9988455359952018 b, 0.53 -> 0.99
                                                  //| 74015885677396 b, 0.54 -> 0.9953784388202258 b, 0.55 -> 0.9927744539878084 
                                                  //| b, 0.56 -> 0.9895875212220557 b, 0.57 -> 0.9858150371789198 b, 0.58 -> 0.98
                                                  //| 14538950336537 b, 0.59 -> 0.9765004687578241 b, 0.6 -> 0.9709505944546686 b
                                                  //| , 0.61 -> 0.9647995485050872 b, 0.62 -> 0.9580420222262995 b, 0.63 -> 0.950
                                                  //| 6720926870659 b, 0.64 -> 0.9426831892554922 b, 0.65 -> 0.934068055375491 b,
                                                  //|  0.66 -> 0.92481870497303 b, 0.67 -> 0.9149263727797275 b, 0.68 -> 0.904381
                                                  //| 457724494 b, 0.69 -> 0.8931734583778568 b, 0.7 -> 0.8812908992306927 b, 0.7
                                                  //| 1 -> 0.8687212463394046 b, 0.72 -> 0.8554508105601307 b, 0.73 -> 0.84146463
                                                  //| 62081757 b, 0.74 -> 0.8267463724926178 b, 0.75 -> 0.8112781244591328 b, 0.7
                                                  //| 6 -> 0.7950402793845223 b, 0.77 -> 0.7780113035465376 b, 0.78 -> 0.76016750
                                                  //| 29619657 b, 0.79 -> 0.7414827399312736 b, 0.8 -> 0.7219280948873623 b, 0.81
                                                  //|  -> 0.7014714598838974 b, 0.82 -> 0.68007704572828 b, 0.83 -> 0.65770477874
                                                  //| 42195 b, 0.84 -> 0.6343095546405662 b, 0.85 -> 0.6098403047164005 b, 0.86 -
                                                  //| > 0.584238811642856 b, 0.87 -> 0.5574381850279891 b, 0.88 -> 0.529360865287
                                                  //| 3644 b, 0.89 -> 0.499915958164528 b, 0.9 -> 0.46899559358928117 b, 0.91 -> 
                                                  //| 0.4364698170641028 b, 0.92 -> 0.4021791902022728 b, 0.93 -> 0.3659236509002
                                                  //| 2305 b, 0.94 -> 0.32744491915447643 b, 0.95 -> 0.2863969571159563 b, 0.96 -
                                                  //| > 0.24229218908241493 b, 0.97 -> 0.1943918578315763 b, 0.98 -> 0.1414405425
                                                  //| 4182076 b, 0.99 -> 0.08079313589591124 b, 1.0 -> 0.0 b)

  val entropyPlot = Plot(List(("h", hm)),
    connect = true, drawKey = false,
    xAxis = 0.0 *: bit, xAxisLabel = Some("p(x='HEAD)"),
    yAxis = 0.0, yAxisLabel = Some("H"),
    title = Some("Entropy"))(DoublePlottable, InfoPlottable(bit))
                                                  //> entropyPlot  : axle.visualize.Plot[Double,Nothing,axle.quanta.Information.Q
                                                  //| ,Nothing] = Plot(List((h,Map(0.0 -> 0.0 b, 0.01 -> 0.08079313589591118 b, 0
                                                  //| .02 -> 0.14144054254182067 b, 0.03 -> 0.19439185783157623 b, 0.04 -> 0.2422
                                                  //| 9218908241482 b, 0.05 -> 0.28639695711595625 b, 0.06 -> 0.32744491915447627
                                                  //|  b, 0.07 -> 0.36592365090022333 b, 0.08 -> 0.4021791902022729 b, 0.09 -> 0.
                                                  //| 4364698170641029 b, 0.1 -> 0.4689955935892812 b, 0.11 -> 0.499915958164528 
                                                  //| b, 0.12 -> 0.5293608652873644 b, 0.13 -> 0.5574381850279891 b, 0.14 -> 0.58
                                                  //| 4238811642856 b, 0.15 -> 0.6098403047164004 b, 0.16 -> 0.6343095546405662 b
                                                  //| , 0.17 -> 0.6577047787442195 b, 0.18 -> 0.6800770457282798 b, 0.19 -> 0.701
                                                  //| 4714598838974 b, 0.2 -> 0.7219280948873623 b, 0.21 -> 0.7414827399312737 b,
                                                  //|  0.22 -> 0.7601675029619657 b, 0.23 -> 0.7780113035465377 b, 0.24 -> 0.7950
                                                  //| 402793845223 b, 0.25 -> 0.8112781244591328 b, 0.26 -> 0.8267463724926178 b,
                                                  //|  0.27 -> 0.8414646362081757 b, 0.28 -> 0.8554508105601307 b, 0.29 -> 0.8687
                                                  //| 212463394045 b, 0.3 -> 0.8812908992306927 b, 0.31 -> 0.8931734583778568 b, 
                                                  //| 0.32 -> 0.9043814577244941 b, 0.33 -> 0.9149263727797275 b, 0.34 -> 0.92481
                                                  //| 87049730301 b, 0.35 -> 0.934068055375491 b, 0.36 -> 0.9426831892554922 b, 0
                                                  //| .37 -> 0.9506720926870659 b, 0.38 -> 0.9580420222262995 b, 0.39 -> 0.964799
                                                  //| 5485050872 b, 0.4 -> 0.9709505944546686 b, 0.41 -> 0.976500468757824 b, 0.4
                                                  //| 2 -> 0.9814538950336535 b, 0.43 -> 0.9858150371789198 b, 0.44 -> 0.98958752
                                                  //| 12220557 b, 0.45 -> 0.9927744539878084 b, 0.46 -> 0.9953784388202258 b, 0.4
                                                  //| 7 -> 0.9974015885677396 b, 0.48 -> 0.9988455359952018 b, 0.49 -> 0.99971144
                                                  //| 17528099 b, 0.5 -> 1.0 b, 0.51 -> 0.9997114417528099 b, 0.52 -> 0.998845535
                                                  //| 9952018 b, 0.53 -> 0.9974015885677396 b, 0.54 -> 0.9953784388202258 b, 0.55
                                                  //|  -> 0.9927744539878084 b, 0.56 -> 0.9895875212220557 b, 0.57 -> 0.985815037
                                                  //| 1789198 b, 0.58 -> 0.9814538950336537 b, 0.59 -> 0.9765004687578241 b, 0.6 
                                                  //| -> 0.9709505944546686 b, 0.61 -> 0.9647995485050872 b, 0.62 -> 0.9580420222
                                                  //| 262995 b, 0.63 -> 0.9506720926870659 b, 0.64 -> 0.9426831892554922 b, 0.65 
                                                  //| -> 0.934068055375491 b, 0.66 -> 0.92481870497303 b, 0.67 -> 0.9149263727797
                                                  //| 275 b, 0.68 -> 0.904381457724494 b, 0.69 -> 0.8931734583778568 b, 0.7 -> 0.
                                                  //| 8812908992306927 b, 0.71 -> 0.8687212463394046 b, 0.72 -> 0.855450810560130
                                                  //| 7 b, 0.73 -> 0.8414646362081757 b, 0.74 -> 0.8267463724926178 b, 0.75 -> 0.
                                                  //| 8112781244591328 b, 0.76 -> 0.7950402793845223 b, 0.77 -> 0.778011303546537
                                                  //| 6 b, 0.78 -> 0.7601675029619657 b, 0.79 -> 0.7414827399312736 b, 0.8 -> 0.7
                                                  //| 219280948873623 b, 0.81 -> 0.7014714598838974 b, 0.82 -> 0.68007704572828 b
                                                  //| , 0.83 -> 0.6577047787442195 b, 0.84 -> 0.6343095546405662 b, 0.85 -> 0.609
                                                  //| 8403047164005 b, 0.86 -> 0.584238811642856 b, 0.87 -> 0.5574381850279891 b,
                                                  //|  0.88 -> 0.5293608652873644 b, 0.89 -> 0.499915958164528 b, 0.9 -> 0.468995
                                                  //| 59358928117 b, 0.91 -> 0.4364698170641028 b, 0.92 -> 0.4021791902022728 b, 
                                                  //| 0.93 -> 0.36592365090022305 b, 0.94 -> 0.32744491915447643 b, 0.95 -> 0.286
                                                  //| 3969571159563 b, 0.96 -> 0.24229218908241493 b, 0.97 -> 0.1943918578315763 
                                                  //| b, 0.98 -> 0.14144054254182076 b, 0.99 -> 0.08079313589591124 b, 1.0 -> 0.0
                                                  //|  b))),true,false,700,600,50,4,Some(Entropy),0.0 b,Some(p(x='HEAD)),0.0,Some
                                                  //| (H))

  // png(entropyPlot, "entropy.png")
  // show(entropyPlot)

}