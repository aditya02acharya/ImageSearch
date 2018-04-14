package im.core.qlearn.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Belief state 
 */

public class BeliefState {

	private static BeliefState instance = new BeliefState();
	private List<Double> belief = new ArrayList<Double>(Collections.nCopies(37, (double)1/37));
	
	protected BeliefState(){};
	
	public static BeliefState getInstance(){
		return instance;
	}
	
	public void initBelief(){
		this.belief.clear();
		this.belief.addAll(Collections.nCopies(37, (double)1/37));
	}
	
	public void updateBelief(List<Double> likelihood){
		
		for(int i = 0; i < belief.size(); i++){
			this.belief.set(i, (this.belief.get(i) * (likelihood.get(i) + 0.001)));
		}
		
		double normalizationFactor = this.belief.stream().mapToDouble(Double::doubleValue).sum();
		
		for(int i = 0; i < belief.size(); i++){
			this.belief.set(i, (this.belief.get(i) / normalizationFactor));
		}
	}
	
	public void printBelief(){
		System.out.println(this.belief.toString());
	}
	
	public Integer maxBelief(){
		
		if(this.belief.equals(Collections.nCopies(37, (double)1/37))){
			return 0;
		}
		else{
			Double value = Collections.max(this.belief);
		
			Integer index = this.belief.indexOf(value);
		
			return index;
		}
	}
}
