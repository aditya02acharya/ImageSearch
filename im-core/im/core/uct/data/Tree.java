package im.core.uct.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Tree {

	private Node root;

	public Tree(T rootData){
		root = new Node();
		root.data = rootData;
		root.children = new TreeMap<Integer, Node>();
	}
	
	public Node getRoot(){
		return this.root;
	}
	
	public void addChild(T childData,Node parent, ArrayList<Node> list){
		
		Node child = new Node();
		child.data = childData;
		child.parent = parent;
		child.children = new TreeMap<Integer, Node>();
		parent.children.put(childData.getAction(), child);
		
	}
	
	@SuppressWarnings("unused")
	public static class Node{
		private T data;
		private Node parent;
		private Map<Integer, Node> children;
	}
	
}
