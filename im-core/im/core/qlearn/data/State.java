package im.core.qlearn.data;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class State {
	
	private Integer fixationLocation = null;
	private Double fixatedValue = null;
	private Double neighbour_1 = null;
	private Double neighbour_2 = null;
	private Double neighbour_3 = null;
	private Double neighbour_4 = null;
	//private Integer beliefLoc = null;
	
	public State(Integer fixationLocation,Double fixatedValue, Double neighbour_1, Double neighbour_2, Double neighbour_3, Double neighbour_4){
		this.fixationLocation = fixationLocation;
		this.fixatedValue = fixatedValue;
		this.neighbour_1 = neighbour_1;
		this.neighbour_2 = neighbour_2;
		this.neighbour_3 = neighbour_3;
		this.neighbour_4 = neighbour_4;
		//this.beliefLoc = beliefLoc;
	}


	public Integer getFixationLocation() {
		return fixationLocation;
	}

	public void setFixationLocation(Integer fixationLocation) {
		this.fixationLocation = fixationLocation;
	}
	
	public Double getFixatedValue() {
		return fixatedValue;
	}


	public void setFixatedValue(Double fixatedValue) {
		this.fixatedValue = fixatedValue;
	}


	public Double getNeighbour_1() {
		return neighbour_1;
	}

	public void setNeighbour_1(Double neighbour_1) {
		this.neighbour_1 = neighbour_1;
	}

	public Double getNeighbour_2() {
		return neighbour_2;
	}

	public void setNeighbour_2(Double neighbour_2) {
		this.neighbour_2 = neighbour_2;
	}

	public Double getNeighbour_3() {
		return neighbour_3;
	}

	public void setNeighbour_3(Double neighbour_3) {
		this.neighbour_3 = neighbour_3;
	}

	public Double getNeighbour_4() {
		return neighbour_4;
	}

	public void setNeighbour_4(Double neighbour_4) {
		this.neighbour_4 = neighbour_4;
	}


/*	public Integer getBeliefValue() {
		return beliefLoc;
	}


	public void setBeliefValue(Integer beliefValue) {
		this.beliefLoc = beliefValue;
	}*/
	

	@Override
	public boolean equals(Object o){
		if (o == null) {
	        return false;
	    }
	    if (this.getClass() != o.getClass()) {
	        return false;
	    }
	    
	    final State other = (State) o;
	    
	    if((this.fixationLocation == null && other.fixationLocation == null) && /*(this.beliefLoc == null && other.beliefLoc == null) &&*/
	    		(this.fixatedValue == null && other.fixatedValue == null) && (this.neighbour_1 == null && other.neighbour_1 == null) &&
	    		(this.neighbour_2 == null && other.neighbour_2 == null) && (this.neighbour_3 == null && other.neighbour_3 == null) &&
	    		(this.neighbour_4 == null && other.neighbour_4 == null)){
	    	return true;
	    }
	    
	    if((this.fixationLocation != null && other.fixationLocation != null) && /*(this.beliefLoc == null && other.beliefLoc == null) &&*/
	    		(this.fixatedValue != null && other.fixatedValue != null) && (this.neighbour_1 != null && other.neighbour_1 != null) &&
	    		(this.neighbour_2 != null && other.neighbour_2 != null) && (this.neighbour_3 != null && other.neighbour_3 != null) &&
	    		(this.neighbour_4 != null && other.neighbour_4 != null)){
	    	
	    	if(this.fixationLocation.equals(other.fixationLocation) && /*this.beliefLoc.equals(other.beliefLoc) &&*/ 
		    		this.fixatedValue.equals(other.fixatedValue) && this.neighbour_1.equals(other.neighbour_1) && 
		    		this.neighbour_2.equals(other.neighbour_2) && this.neighbour_3.equals(other.neighbour_3) && 
		    		this.neighbour_4.equals(other.neighbour_4)){
	    		return true;
	    	}
	    	
	    }
	    
	    return false;
	}
	
	@Override
	public int hashCode() {
		
		HashCodeBuilder hash = new HashCodeBuilder(17, 37);
		hash.append(this.fixationLocation);
		hash.append(this.fixatedValue);
		hash.append(this.neighbour_1);
		hash.append(this.neighbour_2);
		hash.append(this.neighbour_3);
		hash.append(this.neighbour_4);
	    return hash.hashCode();
	}
	
	@Override
	public String toString(){
		return "["+this.fixationLocation+","+/*this.beliefLoc+*/"]{"+this.fixatedValue+","+this.neighbour_1+","+this.neighbour_2+","+
				this.neighbour_3+","+this.neighbour_4+"}";
	}
}
