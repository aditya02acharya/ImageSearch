package im.core.uct.functions;

import im.core.uct.data.Tree;
import im.core.uct.data.Tree.Node;
import im.utility.constants.GlobalConstants;

public class UCTSearchExplore {

	private Tree _t = null;
	
	public UCTSearchExplore(Tree tree){
		this._t = tree;
	}
	
	public Tree startExploration(){
		
		Node parent = _t.getRoot();
		int iter = 0;
		while(iter < GlobalConstants.Episodes){
			Node child = treePolicy(parent);
		}
		
		//TODO: add return type
		return null;
	}
	
	public Node treePolicy(Node parent){
		return null;
	}
	
}
