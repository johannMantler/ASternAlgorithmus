package MantlerBots.aStar;



/**
 * 
 * @author Johann
 *
 */
public abstract class Node implements Comparable<Node>{

	/**
	 * Heuristik dieses Knotens als geschaetzte Kosten von diesem Knoten bis zum Ziel.
	 */
	protected double heuristic;
	
	/**
	 * Pfadkosten vom Startknoten bis zu diesem Knoten.
	 */
	protected int pathCost;
	
	/**
	 * Referenz auf den Vaterknoten. Wird benoetigt um den Loesungspfad als einfach
	 * verkettete Liste von Nodes aufzubauen.
	 */
	protected Node parent;
	

	public Node() { 
		
	}
	
	
	/**
	 * Muss fuer Vergleiche in Sets zwischen Nodes uberschrieben werden. Ansonsten wird
	 * e1.equals(e2) von Object aufgerufen und dieser vergleicht einfach die Referenzen  von e1 und e2.
	 */
	@Override
	public abstract boolean equals(Object obj);
	
	
	/**
	 * Muss fuer Vergleiche in Sets zwischen Nodes uberschrieben werden. Es muss ein int-Wert zurueck geliefert
	 * werden, der den Node moeglichst eindeutig identifiziert. Anhand des HashCodes werden
	 * die Objekte im HashSet kategorisiert. In jeder Kategorie sind (laut equals) unterschiedliche
	 * Objekte gespeichert.
	 */
	@Override
	public abstract int hashCode();
	
	
	
	
	
	////////////////    getter /////////////////
	
	
	public final double getF() {
		return this.heuristic + this.pathCost;
	}
	
	public double getHeuristic() {
		return heuristic;
	}

	public int getPathCost() {
		return pathCost;
	}

	public Node getParent() {
		return parent;
	}
	
	
	//////////////// setter ///////////////
	
	public void setPathCost(int cost) {
		this.pathCost = cost;
	}

	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}
	
	public void setParent(Node node) {
		this.parent = node;
	}

}
