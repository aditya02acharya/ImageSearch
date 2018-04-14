package im.core.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VisualGrid {

	private List<Integer> _grid = new ArrayList<Integer>();
	
	/*
	 * Constructor to Initialise the grid space with features.
	 */
	public VisualGrid(){
		
		//Add 5-feature to the list
		this._grid.add(5);
		
		//Add 4-feature to the list
		this._grid.add(4);
		
		//Add 3-feature to the list
		this._grid.add(3);
		this._grid.add(3);
		
		//Add 2-feature to the list
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		
		//Add 1-feature to the list
		for(int i = 0; i < 26; i++){
			this._grid.add(1);
		}
		
	}
	
	public void init(){

		//Add 5-feature to the list
		this._grid.add(5);
		
		//Add 4-feature to the list
		this._grid.add(4);
		
		//Add 3-feature to the list
		this._grid.add(3);
		this._grid.add(3);
		
		//Add 2-feature to the list
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		this._grid.add(2);
		
		//Add 1-feature to the list
		for(int i = 0; i < 26; i++){
			this._grid.add(1);
		}
		
	}
	
	public void printGrid(){
		System.out.println(this._grid.toString());		
	}
	
	public void shuffleArray() {
		Collections.shuffle(_grid);
	}
	
	public List<Integer> getGrid(){
		return this._grid;
	}

}
