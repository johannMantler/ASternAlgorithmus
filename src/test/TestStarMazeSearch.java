package test;



//import static org.junit.Assert.*;

//import java.util.Iterator;
//import java.util.PriorityQueue;

import junit.framework.Assert;

import org.junit.Test;

import robocode.BattlefieldMap;

import MantlerBots.AStarMazeSearch;
//import MantlerBots.MazeNode;


public class TestStarMazeSearch {

	@Test
	public final void testAStarMazeSearch() {	//einfacher Konstruktortest
		
		BattlefieldMap map = new BattlefieldMap(500, 500);
		
		AStarMazeSearch aStar = new AStarMazeSearch(map);
		
		Assert.assertNotNull(aStar);
	}

	

//				------------------------------- testToOpenList() muss fuers Testen public gemacht werden-------------------
//	@Test
//	public final void testToOpenList() {
//		
//		//SET UP
//		MazeNode startMazeNode = new MazeNode(450, 450, null);
//		BattlefieldMap map = new BattlefieldMap(500, 500);
//		AStarMazeSearch aStar = new AStarMazeSearch(startMazeNode, map);
//		PriorityQueue<MazeNode> openList = aStar.getOpenList();
//		
//		//EXERCISE
//		
//		MazeNode n1 = new MazeNode(400, 450, startMazeNode);		//ein paar testknoten
//		MazeNode n2 = new MazeNode(350, 450, n1);
//		MazeNode n3 = new MazeNode(350, 400, n2);
//		MazeNode n4 = new MazeNode(400, 400, n1);
//		
//		aStar.calcHeuristic(n1);
//		aStar.calcHeuristic(n2);
//		aStar.calcHeuristic(n3);
//		aStar.calcHeuristic(n4);
//		
//		aStar.toOpenList(n1, startMazeNode);			//Setze in die OpenList und pruefe das nach
//		aStar.toOpenList(n2, n1);
//		aStar.toOpenList(n3, n2);
//		aStar.toOpenList(n4, n1);
//		
//		Assert.assertTrue(openList.contains(n1));
//		Assert.assertTrue(openList.contains(n2));
//		Assert.assertTrue(openList.contains(n3));
//		Assert.assertTrue(openList.contains(n4));
//		
//		
//		
//		
//		MazeNode betterMazeNode = new MazeNode(400, 400, startMazeNode); //= neuer Weg zu n4 mit geringeren Pfadkosten
//		aStar.calcHeuristic(betterMazeNode);
//		
//		Assert.assertTrue(n4.getPathCost() > betterMazeNode.getPathCost()); //betterMazeNode hat bessere(geringere) Pfadkosten
//		
//		aStar.toOpenList(betterMazeNode, startMazeNode); //hier muss n4 um betterMazeNode aktualisiert werden
//		
//		
//		
//		Iterator<MazeNode> iterator = openList.iterator();
//		MazeNode tmp;
//		do {
//			tmp = iterator.next();
//		} while(!tmp.equals(n4));				//iteriere bis zu n4 in der Queue
//		
//		Assert.assertTrue(n4.getPathCost() == betterMazeNode.getPathCost()); //pruefe ob aktualisiert wurde
//		Assert.assertTrue(n4.getParent() == betterMazeNode.getParent());	
//		
//		
//		
//		Assert.assertTrue(openList.contains(n1));	//Sind die MazeNodes immer noch in der Queue??
//		Assert.assertTrue(openList.contains(n2));
//		Assert.assertTrue(openList.contains(n3));
//		Assert.assertTrue(openList.contains(n4));
//		Assert.assertTrue(openList.contains(betterMazeNode)); 	//betterMazeNode = n4
//		
//	}

}
