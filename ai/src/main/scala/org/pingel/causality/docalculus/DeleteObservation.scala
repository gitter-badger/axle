package org.pingel.causality.docalculus

import org.pingel.causality.CausalModel
import org.pingel.bayes.Probability
import org.pingel.bayes.VariableNamer
import org.pingel.forms.Variable
import org.pingel.gestalt.core.Form
import org.pingel.gestalt.core.Unifier

class DeleteObservation extends Rule {

    apply(q: Probability, m: CausalModel, namer: VariableNamer)  = {

    	var results = ArrayList[Form]()
        
        val Y = q.getQuestion()
        val X = q.getActions()
        val subModel = m.duplicate()
        subModel.getGraph().removeInputs(randomVariablesOf(X))
        
        for( zObservation <- q.getGiven() ) {
          
            var Z = Set[Variable]()
            Z += zObservation
            
            val W = Set[Variable]()
            W ++= q.getGiven()
            W.remove(zObservation)

            var WX = Set[Variable]()
            WX ++= W
            WX ++= X

            
            if( subModel.blocks(randomVariablesOf(q.getGiven()), randomVariablesOf(Z), randomVariablesOf(WX)) ) {

            	var Ycopy = Set[Variable]()
                Ycopy ++= Y
                var Xcopy = Set[Variable]()
                Xcopy ++= X

                val probFactory = new Probability()
                val unifier = new Unifier()
                unifier.put(probFactory.question, Ycopy)
                unifier.put(probFactory.given, W)
                unifier.put(probFactory.actions, Xcopy)
                results.add(probFactory.createForm(unifier))
            }
        }
        
        results
    }

}