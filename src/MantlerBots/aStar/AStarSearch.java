package MantlerBots.aStar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;


/**
 * Enthaelt alle ueblichen Bestandteile die man braucht um den A*-Algorithmus zu implementieren.
 * Unterklassen dieser Klasse muessen nur noch die primitiven Methoden geeignet implementieren.
 * <p>
 * Der A*-Algorithmus ist
 * <ol>
 * <li><i>vollständig:</i> Falls eine Lösung existiert, wird sie gefunden.
 * <li><i>optimal:</li> Es wird immer die optimale Lösung gefunden. Existieren mehrere optimale Lösungen, wird eine davon gefunden (abhängig von Implementierungsdetails).
 * <li><i>optimal effizient:</li> Es gibt keinen anderen Algorithmus, der die Lösung unter Verwendung der gleichen Heuristik schneller findet. (genauer: A* expandiert eine minimale Anzahl an Knoten.)
 * </o>
 * </p>
 * <p>
 * Die Laufzeit haengt von 2 Faktoren ab:
 * <ol>
 * <li>Genaugikeit der verwendeten Heuristik
 * <li>Implementierung der Open und ClosedList
 * </oL>
 * 
 * </p>
 * @author Johann
 *
 */
public abstract class AStarSearch {

	
	/**
	 * Bekannte Knoten. Aus dieser Queue werden immer die vielverprechendsten
	 * Knoten ausgewaehlt und untersucht. Hier werden alle Knoten solange gespeichert,
	 * bis ihr endgueltiger kuerzester Pfad bis zum Startknoten bekannt ist. Wenn es soweit
	 * ist, wird der Knoten von der openList entfernt und zur closedList hinzugefuegt.
	 */
	protected PriorityQueue<Node> openList; //The head of this queue is the least element
	
	/**
	 * Untersuchte Knoten. Zu diesen Knoten ist der kuerzeste Weg bekannt.
	 * HashSet wegen der Duplikatvermeidung und dem effizienten Suchen.
	 * <p>
	 * <b>NODE!</b> equals() und hashset() muessen im Node ueberschrieben werden, weil
	 * HasSet anhand dieser Methoden die Duplikat-Erkennung durchfuehrt.
	 * </p>
	 */
	protected HashSet<Node> closedList;
	
	
	/**
	 * Nach abschlieszender Suche wird in headOfPath der Knoten gespeichert, mit dem man den Loesungspfad
	 * ueber die Vaterknoten zurueckverfolgen kann.
	 */
	protected Node headOfPath;
	
	
	
	/**
	 * Standard-Konstruktor
	 * <ul>
	 * <li>initialisiere den Grenzbereich(openList) mit dem Ausgangszustand (=leer)
	   <li>initialisiere die untersuchte Menge(closedList) mit der leeren Menge
	   <li>initialisiere den Kopf des Loesungspfades mit null
	 * </ul>
	 */
	public AStarSearch() {
		
		this.openList = new PriorityQueue<Node>();
		this.closedList = new HashSet<Node>();
		this.headOfPath = null;
	}
	
	

	
	
	
	/**
	 * Liefert den kueresten Weg vom Startknoten zum Endknoten.
	 * <ol>
	 * <li> Setze den Startknoten in die Warteschlange
	 * <li> Solange Warteschlange nicht leer & Zielknoten nicht gefunden
	 * 	<ol>
	 * 		<li>Hole ein Knoten aus der Warteschlange(PriorityQueue)
	 * 		<li>Fuege ihn zum Grenzbereich hinzu
	 * 		<li>Wenn er kein Zielknoten ist, expandiere ihn.
	 * 	</ol>
	 * <ol>
	 * 
	 * <p>
	 * <b>Laufzeit im worstcase:</b></br>
	 * O( n * (log(n) + O( expand() ) ) ) </br>
	 * Laufzeit von expand() = O(n) </br>
	 * Gesamtlaufzeit:  O( n * (log(n) + n) ) = <b>O( n * n) )</b>
	 * </p>
	 * @param startNode Startknoten
	 * @param endNode	Endknoten=Ziel
	 */
	public final Node searchPath(Node startNode, Node endNode) { //final als Ueberschreibungsschutz
		
		this.openList.add(startNode);	
		boolean foundPath = false; 
		
		do {
			
			Node currentNode = this.openList.poll(); 	// O(log(n))	n = Anzahl aller Knoten
			this.closedList.add(currentNode); 			// O(1)

			if( this.isEndNode(currentNode, endNode) ) {
				
				foundPath = true;
				this.headOfPath = currentNode;			
			}
			else {
				this.expand(currentNode);			//Laufzeit von expand() = O(n)
			}

			
		} while( !foundPath && !this.openList.isEmpty() ); // O(n)

		
		if(!foundPath) {
			this.headOfPath = this.openList.peek();
		}
		
		
		return this.headOfPath;
	}
	
	
	/**
	 * Ueberprueft ob der node ein Endknoten, also das Ziel der Suche ist.
	 * Diese Methode muss geeignet ueberschrieben werden, da sie in der searchPath()
	 * verwendet wird.
	 * <p>Entwurfsmuster Schablonenmethode: isEndNode() ist die primitive Methode</p>
	 * @param node der Knoten der ueberprueft werden soll.
	 * @param endNode Informationen ueber den Endknoten
	 * @return true wenn node ein Endknoten ist, sonst false.
	 */
	protected abstract boolean isEndNode(Node node, Node endNode);
	
	
	/**
	 * Expandiert, d.h. untersucht den aktuellen Knoten.
	 * Genauer, ruft fuer jeden Nachbarknoten von node, die Methode toOpenList auf.
	 * Diese Methode muss geeignet ueberschrieben werden, da sie in der searchPath()
	 * verwendet wird.
	 * <p>Entwurfsmuster Schablonenmethode: expand() ist die primitive Methode</p>
	 * @param node Knoden, welcher expandiert werden soll.
	 */
	protected abstract void expand(Node node);
	
	
	
	/**
	 * Hilfsmethode fuer die Methode void expand(Node node) , die einen Knoten in die
	 * OpenList einfuegt.
	 * <ul>
	 * 	<li>Wenn nextNode noch nicht in der ClosedList ist..(also noch kein optimaler Pfad bekannt ist) und ..
	 * 	<ul>
	 * 		<li>nextNode noch nicht in der OpenList ist(noch unbekannt ist), dann fuege ihn in die OpenList ein.
	 * 		<li>Falls er schon in der Openlist ist und ein besserer Pfad vorhanden ist, aktualisiere den Knoten in der Openlist.
	 * 	</ul>
	 * </ul>
	 * 
	 * <p>
	 * <b>Laufzeit:</b> O( log(n) + n )  = O( n )
	 * </p>
	 * @param nextNode Der Knoten der in die openList aufgenommen werden soll.
	 * @param actNode Der neue Elternknoten von nextNode, falls nextNode aktualisiert werden sollte.
	 */
	protected final void toOpenList(Node nextNode, Node actNode) {
		
		if(!this.closedList.contains(nextNode)) { 			//O(1) 
		
			if(!this.openList.contains(nextNode)) {			//O(n) kann auch in konstanter Zeit erfolgen, falls man zu jeden Knoten ein openList-Flag speichert!
				this.openList.add(nextNode);				//O( log(n) )
				

			}
			else {											//O(n)
			
				
				Iterator<Node> iterator = this.openList.iterator();		
				Node tmp;
				do {
					tmp = iterator.next();
				} while(!tmp.equals(nextNode));
				
				if(nextNode.getPathCost() < tmp.getPathCost()) {
					
					tmp.setPathCost(nextNode.getPathCost());
					tmp.setParent(actNode);
				}
			}
		}
	}
	
	
	
	/**
	 * Dreht die Verkettungsrichtung des Loesungspfades in die andere Richtung.
	 * Arbeitet mit O(n) wobei n die Anzahl der Knoten im Loesungspfad ist.
	 * @param node Anfangs- oder endpunkt des Pfades.
	 * @return wenn node der Anfagsknoten war, gibt es den Endknoten zurueck und umgekehrt.
	 */
    public Node turnPathAround(Node node) {
    	
    	LinkedList<Node> buffer = new LinkedList<Node>();
    	
    	Node tmp = node;
    	buffer.add(tmp);
    	
    	tmp = tmp.getParent();
    	buffer.add(tmp);
    	int i = 1;
    	
    	while( (tmp = tmp.getParent()) != null) {
    		
    		buffer.add(tmp);
        	i++;
        	buffer.get(i-1).setParent(buffer.get(i-2));
    	}
    	
    	buffer.get(i).setParent(buffer.get(i-1));
    	buffer.get(0).setParent(null);
    	
    	return buffer.get(i);
	
    }






	public final int getOpenListSize() {
		return openList.size();
	}



	public final int getClosedListSize() {
		return closedList.size();
	}
	
	
/*	protected final Node getHeadOfPath() {
		return this.headOfPath;
	}*/
}
