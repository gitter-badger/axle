package org.pingel.bayes;

import org.pingel.util.DirectedGraph
import org.pingel.util.UndirectedGraph

object JoinTree {

  // TODO:
  def fromEliminationOrder(G: DirectedGraph, pi: List[RandomVariable]): JoinTree = {
    // returns a jointree for DAG G with width equal to width(pi, G)
    var T = new JoinTree()
    val Gm = G.moralGraph() // UndirectedGraph
    var clusterSequence: List[Set[RandomVariable]] = null; // Gm.induceClusterSequence(pi);
    return T;
  }

}

class JoinTree extends UndirectedGraph[JoinTreeNode, JoinTreeEdge]
{
	
  var node2cluster = Map[JoinTreeNode, Set[RandomVariable]]()
  
  def copyTo(other: UndirectedGraph[JoinTreeNode, JoinTreeEdge]): Unit = 
  {
    // asdf();
  }

  def setCluster(n: JoinTreeNode, cluster: Set[RandomVariable]): Unit = node2cluster += n -> cluster

  def addToCluster(n: JoinTreeNode, v: RandomVariable): Unit = {
    if( ! node2cluster.contains(n) ) {
      node2cluster += n -> Set[RandomVariable]()
    }
    node2cluster(n).add(v);
  }
	
  def constructEdge(n1: JoinTreeNode, n2: JoinTreeNode): JoinTreeEdge = new JoinTreeEdge(n1, n2)
  

  def separate(n1: JoinTreeNode, n2: JoinTreeNode): Set[RandomVariable] = {
    var result = Set[RandomVariable]()
    
    for( v <- node2cluster.get(n1)) {
      if( node2cluster(n2).contains(v) ) {
	result.add(v)
      }
    }
    result
  }


  def toEliminationOrder(r: JoinTreeNode): List[RandomVariable] =  {
    var result: List[RandomVariable] = Nil
		
    var T = new JoinTree()
    copyTo(T); // not yet implemented
		
    while( T.getVertices().size() > 1 ) {
      val i = T.firstLeafOtherThan(r)
      val j = null; // TODO theNeighbor(); a JoinTreeNode
      for(v <- node2cluster.get(i)) {
	if( ! node2cluster(j).contains(v) ) {
	  result.add(v);
	}
      }
    }

    for( v <- node2cluster(r) )  {
      result.add(v)
    }
		
    result
  }
	
  def embeds(eTree: EliminationTree, embedding: Map[JoinTreeNode, EliminationTreeNode]): Boolean = {
    for(jtn <- getVertices() ) {
      val cluster = node2cluster(jtn)
      val etn = embedding(jtn);
      for( v <- eTree.getFactor(etn).getVariables ) {
	if( ! cluster.contains(v) ) {
	  return false
	}
      }
    }
    
    true
  }
	
}