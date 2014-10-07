package test;

import java.util.HashSet;
import java.util.PriorityQueue;

import junit.framework.Assert;

import org.junit.Test;

import MantlerBots.MazeNode;



public class Test_MazeNode {

	@Test //Konstruktor test
	public final void testMazeNode() {
		MazeNode n = new MazeNode(100, 100, null);
		n.setHeuristic(200);
		Assert.assertTrue(n.getPathCost() == 0); //Elternknoten muss 0 pfadkosten haben
		
		Assert.assertTrue(n.getF() == 200 + 0);
		
	}

	@Test//Test auf Gleichheit:
	public final void testEqualsObject() {
		MazeNode n1 = new MazeNode(100, 100, null);
		MazeNode n2 = new MazeNode(100, 100, null);

		
		Assert.assertTrue(n1.equals(n2));
		
	}
	
	@Test//Test auf Ungleichheit:
	public final void testEqualsObject_1() {
		MazeNode n1 = new MazeNode(100, 0, null);		
		
		MazeNode n2 = new MazeNode(100, 100, n1);

		
		
		Assert.assertFalse(n1.equals(n2));
		
	}
	
	@Test// Negativtest
	public final void testEqualsObject_2() {
		MazeNode n1 = new MazeNode(100, 0, null);
		n1.setHeuristic(200);
		
		Assert.assertFalse(n1.equals(null));
		
	}
	
	@Test// erkennt HashSet richtig Duplikate??
	public final void testEqualsObject_3() {
		
		MazeNode n1 = new MazeNode(950.0, 950.0, null);
		n1.setHeuristic(200);

		MazeNode n2 = new MazeNode(950.0, 950.0, n1); //2 Knoten mit selben xy = Duplikat
		n2.setHeuristic(7);
		
		
		HashSet<MazeNode> set = new HashSet<MazeNode>();
		
		Assert.assertTrue(set.add(n1));
		
		Assert.assertFalse(set.add(n1));
		Assert.assertFalse(set.add(n2));
		
		Assert.assertTrue(set.contains(n1));
		Assert.assertTrue(set.contains(n2)); //n2 ist auch im set da n1.equals(n2)!
	}

	@Test// MazeNodes richtig in PriorityQueue?
	public final void testCompareTo() {
		MazeNode n1 = new MazeNode(100, 100, null);
		n1.setHeuristic(200);
		
		MazeNode n2 = new MazeNode(100, 99, n1);
		n2.setHeuristic(199);
		
		MazeNode n3 = new MazeNode(100, 20, n2);
		n3.setHeuristic(120);
		
		Assert.assertTrue(n3.compareTo(n2) <= 0);
		
		PriorityQueue<MazeNode> queue = new PriorityQueue<MazeNode>();
		queue.add(n1);
		queue.add(n3);
		queue.add(n2);
		
		Assert.assertTrue(n1 == queue.poll());	//war n3
		Assert.assertTrue( n3.equals( queue.poll() ));
	}

}
