
package axle.pgm.docalculus

import spire.algebra.Eq
import spire.algebra.Field

object InsertAction extends Rule {

  def apply[T: Eq, N: Field, DG[_, _]](q: CausalityProbability[T, N], m: CausalModel[T, N, DG], namer: VariableNamer[T, N]): List[Form] = {

    val Y = q.question
    val X = q.actions
    val W = q.given

    val XW = X ++ W

    // TODO Question: are all actions necessarily in q? Is
    // is possible to have relevant actions that are not in q?
    // I assume not.

//    (m.randomVariables().toSet -- Y -- X -- W).flatMap(zRandomVariable => {
//      if (m.observes(zRandomVariable)) {
//        val zAction = namer.nextVariable(zRandomVariable)
//        val Z = Set(zAction)
//
//        val subModel = m.duplicate()
//        subModel.removeInputs(subModel.nodesFor(X))
//        val ancestorsOfW = subModel.collectAncestors(subModel.nodesFor(W))
//        if (!ancestorsOfW.contains(zRandomVariable)) {
//          subModel.removeInputs(subModel.nodesFor(Z))
//        }
//
//        if (subModel.blocks(Y, Z, XW)) {
//          Some(CausalityProbability(Y, W, X + zAction))
//        } else {
//          None
//        }
//      } else {
//        None
//      }
//    }).toList

    Nil // TODO
  }

}
