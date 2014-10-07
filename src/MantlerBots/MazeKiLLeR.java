package MantlerBots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;



import robocode.BattlefieldMap;

import robocode.*;
 
public class MazeKiLLeR extends Robot {
	
	private static Point2D CurrentPosition = new Point2D.Double();
	private static Point2D NextPosition = new Point2D.Double();
	private  AStarMazeSearch aStar;
	
	private  BattlefieldMap map;
    private  double mapWidth;
    private  double mapHeight;
    private MazeNode startNode;
    
    public void run() {
			
    	//initialize
		this.map = this.getBattlefieldMap();
		this.mapWidth = map.getWidth();
		this.mapHeight = map.getHeight();
		this.aStar = new AStarMazeSearch(map);
		
		
		//Get Ready!
		this.setStartPosition();
		MazeNode startNode = new MazeNode(getX(), getY(), null);
		startNode.calcHeuristic();
		System.out.println("Startposition: ("+getX()+"|"+getY()+") MAZE_TILE_SIZE ist: "+Rules.MAZE_TILE_SIZE);
		
		
		//Navigate
		MazeNode endNode = (MazeNode)this.aStar.searchPath(startNode, null); //2.Paramenter ist null, da sich der endpunkt mitHilfe von map.isEndZone() ergibt.
		this.startNode = (MazeNode)this.aStar.turnPathAround(endNode);
	
		
		//Go!!
		System.out.println(this.aStar.toString(this.startNode));
		this.driveToEndzone();

    	 
    }
    
    

    
    //setze Agent genau in die Mitte der Startzone
    private void setStartPosition() {
    	
		MazeKiLLeR.CurrentPosition.setLocation(getX(), getY());  
		
		MazeKiLLeR.NextPosition.setLocation(this.mapWidth - Rules.MAZE_TILE_SIZE/2,
											this.mapHeight - Rules.MAZE_TILE_SIZE/2);	 
		this.goTo(NextPosition);

    }
    
    
    /**
     * Laesst den Agenten zum Zielknoten fahren in O(n) wobei n = Anzahl der Knoten im Pfad ist.
     */
    private void driveToEndzone() {
    	
    	
    	MazeNode tmpNode = this.startNode;
    	
    	do {
    		MazeKiLLeR.CurrentPosition.setLocation(getX(), getY());
    		MazeKiLLeR.NextPosition.setLocation(tmpNode.getPosX(), tmpNode.getPosY());
    		this.goTo(NextPosition);
    		
    		tmpNode = (MazeNode)tmpNode.getParent();
    		
    	} while(tmpNode != null);
    }

    
    
    /**
     * Malt den gefunden kuerzesten Pfad auf das Spielfeld in O(n) wobei n = Anzahl der Knoten im Pfad ist.
     */
    @Override
    public void onPaint(Graphics2D g) {
        
    	try {
    	
    		MazeNode tmp = this.startNode;       	
        	MazeNode parent = (MazeNode)tmp.getParent();
        	g.setColor(Color.green);
            
        	
        	do {	
            g.drawLine((int)tmp.getPosX(), (int)tmp.getPosY(),
            		(int)parent.getPosX(), (int)parent.getPosY());
            		
            	tmp = parent;
            	parent = (MazeNode)tmp.getParent();
        	} while(parent != null);
    		  		
    	}
    	catch(Exception e) {
    		
    		return;
    	}
    	
    }
    
    
    
    private void goTo(Point2D point) {
    	double distance = CurrentPosition.distance(point); //Hole Distanz bis zum Punkt
        double angle = normalRelativeAngle(absoluteBearing(CurrentPosition, point) - getHeading());
        if (Math.abs(angle) > 90) {
            distance *= -1;
            if (angle > 0) {
                angle -= 180;
            }
            else {
                angle += 180;
            }
        }
        turnRight(angle);
        ahead(distance);
    }
    
    
    private double absoluteBearing(Point2D source, Point2D target) {
        return Math.toDegrees(Math.atan2(target.getX() - source.getX(), target.getY() - source.getY()));
    }

    
    private double normalRelativeAngle(double angle) {
        angle = Math.toRadians(angle);
        return Math.toDegrees(Math.atan2(Math.sin(angle), Math.cos(angle))); 
    }
    

    }
 

