package MantlerBots;

import MantlerBots.aStar.Node;
import robocode.Rules;

/**
 * <p>
 * <b>Zielbasierter Agent:</b> Wie der modelbasierter Reflex-Agent, 
 * nur dass dieser seine Zielvorgabe kennt. Es werden immer die Aktionen ausgeführt, 
 * die dem Ziel dienlich erscheinen. Da das Ziel nicht immer ein einem Schritt erreicht werden kann,
 * ist der Agent in der Lage zu planen. 
 * </p>
   Ein zielorientierter Agent plant einen Weg, der mit minimalen Kosten vom Ausgangszustand 
   zu einem Zielzustand führt. Ein solcher Weg wird auch als Pfad durch den Zustandsraum bezeichnet
    und die damit verbundenen Kosten als Pfadkosten

 * @author Johann
 *
 */
public class MazeNode extends Node  {

	private static int counter = -1;	//Jede Node bekommt ihre eigene Nummer
	private final int nr;
	
	private double posX;	// (x|y)-Koordinate
	private double posY;
	
	
	// h(A) <= c(K,A) + h(K)
	public MazeNode(double x, double y, MazeNode parent) {
		
		super();
		MazeNode.counter++;
		this.nr = MazeNode.counter;
		
		this.posX = x;
		this.posY = y;
		this.parent = parent;
		this.pathCost = parent == null ? 0 : parent.getPathCost() + 35;//(int)Rules.MAZE_TILE_SIZE; //100 geht nicht
	}
	

	/**
	 * Berechnet und setzt die Heuristk des Knotens.
	 * <p>
	 * <ol>
	 * <li>Eine Heuristik ist zulässig, wenn die Kosten nie überschätzt werden.
	 * <ol>
	 * </p>
	 * <p>
	 * Die Heuristik ist die Luftlinie vom Knoten bis zum Zielknoten und wird mit Hilfe des
	 * Satz des Pythagoras berechnet. Luftlinie = wurzel(x²+y²) , wobei x und y die Koordinaten
	 * des Knotes sind und der Zielknoten unten links im Punkt (0|0) ist.
	 * </p>
	 */
	public void calcHeuristic() {
		
		double heuristic = Math.sqrt(Math.pow(this.getPosX(), 2) + Math.pow(this.getPosX(), 2)); //actNode.getPosX() + actNode.getPosY();//
		this.setHeuristic(heuristic);
	}
	
	
	
	/**
	 * Zwei Nodes sind dann gleich, wenn ihre Koordinaten gleich sind.
	 */
	@Override
	public boolean equals(Object obj) { //fuer Hashset und Queue (contains)
		
		boolean result = false;
		
		try {
			
			MazeNode tmp = (MazeNode)obj;
			result = this.posX == tmp.getPosX() && this.posY == tmp.getPosY();
	
		} catch(Exception e) {
			
			//result bleibt false
		}
		
		return result;
	}
	
	
	@Override
	public int hashCode() {
		return (int)this.posX + (int)this.posY; //ist moeglichst eindeutig!
	}




	/**
	 * Liefert die natuerliche Ordnung von Node-Objekten.
	 * Anhand dieser Methode trifft die PriorityQueue, die Entscheidung, welcher Node
	 * zu bevorzugen ist. Ein Node ist dann hoeher priorisiert, wenn sein F-Wert kleiner ist.
	 * Vergleicht zwei Node-Objekte und ..
	 * <ul>
	 * <li>liefert -1 , wenn der F-Wert kleiner als der F-Wert von otherNode ist
	 * <li>liefert  1 , wenn der F-Wert von otherNode groeszer ist
	 * <li>liefert  0 , wenn di F-Werte gleich sind
	 * </ul>
	 */
	@Override
	public int compareTo(Node otherNode) {

		int result = 0;
		
		try {
			
			if(this.getF() < otherNode.getF()) {
				result = -1;
			}
			else if(this.getF() > otherNode.getF()) {
				result = 1;
			}
			
		} catch (Exception e){
			throw new Error("Cast- or NullpointerException in compareTo!!");
		}
		
		return result;
	}
	
	
	
	
	public final int getNr() {
		return this.nr;
	}
	

	public final double getPosX() {
		return posX;
	}

	public final double getPosY() {
		return posY;
	}
	
	
	
	public String toString() {
		return this.nr+" Node: ("+this.posX+"|"+this.posY+") with f(): "+this.getF();
	}

}
