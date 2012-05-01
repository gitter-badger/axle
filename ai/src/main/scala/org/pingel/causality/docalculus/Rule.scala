
package org.pingel.causality.docalculus

import org.pingel.causality.CausalModel
import org.pingel.bayes.Probability
import org.pingel.bayes.RandomVariable
import org.pingel.bayes.VariableNamer
import org.pingel.forms.Variable
import org.pingel.gestalt.core.Form

abstract class Rule {

  def apply(q: Probability, m: CausalModel, namer: VariableNamer): List[Form]
  
}
