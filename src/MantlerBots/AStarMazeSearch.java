package MantlerBots;


import java.util.PriorityQueue;
import MantlerBots.aStar.AStarSearch;
import MantlerBots.aStar.Node;
import robocode.BattlefieldMap;
import robocode.Rules;


/**
 * 
 * @author Johann
 *
 */
public class AStarMazeSearch extends AStarSearch{

	

	private final int directions = 4;	//in welche der Robot gehen kann
	
	
	//Vereinfacht die Koordinatenfindung. Wenn der Robot z.B. nach rechts gehen soll,
	//so sind die Koordinaten dafuer: moveX[1]moveY[1]
    private final double[] moveX = {0, Rules.MAZE_TILE_SIZE/2, 0, -Rules.MAZE_TILE_SIZE/2}; //oben, rechts, unten,links
    private final double[] moveY = {Rules.MAZE_TILE_SIZE/2, 0, -Rules.MAZE_TILE_SIZE/2, 0};
	
    
    private final BattlefieldMap map;
    private final double mapWidth;
    private final double mapHeight;
	
    
    
    
    /* Funktioniert nicht, da an jeder Ecke eine Wand ist.
    private final double[] moveX = {0,								//oben
    								Rules.MAZE_TILE_SIZE/2,			//oben rechts
    								Rules.MAZE_TILE_SIZE/2,			//rechts
    								Rules.MAZE_TILE_SIZE/2,			//unten rechts
    								0,								//unten
    								-Rules.MAZE_TILE_SIZE/2,		//unten links
    								-Rules.MAZE_TILE_SIZE/2,		//links
    								-Rules.MAZE_TILE_SIZE/2}; 		//links oben
    
    
    
    private final double[] moveY = {Rules.MAZE_TILE_SIZE/2,			//oben
									Rules.MAZE_TILE_SIZE/2,			//oben rechts
									0,								//rechts
									-Rules.MAZE_TILE_SIZE/2,		//unten rechts
									-Rules.MAZE_TILE_SIZE/2,		//unten
									-Rules.MAZE_TILE_SIZE/2,		//unten links
									0,								//links
									Rules.MAZE_TILE_SIZE/2}; 		//links oben
*/

    
	public AStarMazeSearch(BattlefieldMap map) {
		
		super();
		this.map = map;
		this.mapHeight = map.getHeight();
		this.mapWidth = map.getWidth();

	}
	

	
	@Override
	protected boolean isEndNode(Node node, Node endNode) {
		
		MazeNode n = (MazeNode)node;			//endNode wird nicht gebraucht
		return this.map.isEndZone(n.getPosX(), n.getPosY());
	}
 
	
	/**
	 * Ruft fuer jeden Nachbarknoten von currentNode " super.toOpenList(nextNode, currentNode) " auf.
	 * Nachbarknoten sind Knoten, die passierbar sind(keine Wand enthalten) und
	 * die sich im BattlefieldMap befinden.
	 * <p>
	 * Das Spielfeld ist in quadratisch groszen Kacheln von Rules.MAX_TILE_SIZE pixeln aufgeteilt. An jeder
	 * Kachelgrenze kann sich eine Wand befinden. Wenn der Robot genau in der Mitte der Kachel
	 * startet, so kann in alle Himmelsrichtungen im Abstand von Rules.MAX_TILE_SIZE / 2 sich eine
	 * Wand befinden.
	 * 
	 * </p>
	 * <p>
	 * <b>Laufzeit:</b>
	 * Die Laufzeit dieser Methode belaeuft sich auf O( a * O(toOpenList())  ) = O( O(toOpenList()) ) , wenn a
	 * die Anzahl der ausgehenden Kanten des Knotens sind. (a = directions). </br></br>
	 * Und die Laufzeit von toOpenList() ist liniar. </br>
	 * <i>Also hat diese Methode auch liniare Laufzeit </i>
	 * <p>
	 * <p>
	 * Alle Funktionen werden für jeden Nachfolgeknoten aufgerufen. Es wird angenommen,
	 * dass jeder Knoten nur maximal a ausgehende Kanten hat. Die Anzahl der Schleifendurchläufe
	 * innerhalb von expandNode ist somit konstant und kann bei der asymptotischen Laufzeitbetrachtung vernachlässigt werden.
	 * Diese Annahme gilt nicht für Graphen, in denen jeder Knoten mit fast jedem anderen Knoten verbunden ist.
	 * </p>
	 */
	@Override
	protected void expand(Node currentNode) {
		
		MazeNode current = (MazeNode)currentNode;
		MazeNode nextNode;
		double nextTileX;
		double nextTileY;
		
		for(int i = 0; i < this.directions; i++) {
			
			nextTileX = current.getPosX() + this.moveX[i]; //erstmal MaxTileSize/2 vorwaerts..
			nextTileY = current.getPosY() + this.moveY[i];
			
			
			if(nextTileX < this.mapWidth && nextTileY < this.mapHeight) { //Koordinaten noch in Map??
				
				if(this.map.isPassable(nextTileX, nextTileY)) { //pruefe dann auf Wand, und wenn keine Wand..
					
					nextTileX +=  this.moveX[i]; //gehe weitere MaxTileSize/2 Schritte und 
					nextTileY +=  this.moveY[i]; //setze hier den Knoten.
					
					nextNode = new MazeNode(nextTileX, nextTileY, current);
					nextNode.calcHeuristic();
					
					
				    super.toOpenList(nextNode, currentNode);
						
					
				}
			}
		}
	}
	
	

	public String toString(MazeNode head) {
		
		MazeNode tmpNode = head;
		if(null == tmpNode) return "Es gibt keinen Weg zum Ziel!";
		
		int counter = 1;	//zaehlt die Anzahl der Knoten im Loesungspfad
		
		do {
			counter++;
			tmpNode = (MazeNode)tmpNode.getParent();
		
		} while(tmpNode != null);
		
		String s = "Es gibt "+this.mapHeight/Rules.MAZE_TILE_SIZE * this.mapWidth/Rules.MAZE_TILE_SIZE+" Knoten in diesem Spielfeld.";
		return s+"\nEs wurden "+super.getClosedListSize()+" Knoten expandiert.\nInsgesamt gibt es "+counter+" Knoten im Loesungspfad.";
	}
	
	
	
	//nur zum Testen
	public PriorityQueue<Node> getOpenList() {
		return this.openList;
	}

}
