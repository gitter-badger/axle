
package org.pingel.causality.examples

import org.pingel.causality.CausalModel
import org.pingel.forms.Basic.PFunction
import org.pingel.bayes.RandomVariable

object Model3dot8d extends CausalModel("3.8d") {

  val X = new RandomVariable("X")
  g += X
  
  val Y = new RandomVariable("Y")
  g += Y
  
  val Z = new RandomVariable("Z")
  g += Z
  
  val U = new RandomVariable("U", None, false)
  g += U

  addFunction(new PFunction(X, List(Z, U)))
  addFunction(new PFunction(Y, List(X, Z)))
  addFunction(new PFunction(Z, List(U)))

  def main(args: Array[String]) {
    g.draw
  }

}
