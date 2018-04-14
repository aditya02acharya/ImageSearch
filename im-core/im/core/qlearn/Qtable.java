package im.core.qlearn;

import im.core.qlearn.data.State;
import im.utility.constants.GlobalConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Qtable {

	private static Qtable instance = null;
	
	private Map<State, ArrayList<Double>> qtable = new HashMap<State, ArrayList<Double>>();
	
	protected Qtable(){};
	
	public static Qtable getInstance(){
		
		if(instance == null){
			instance = new Qtable();
		}
		
		return instance;
	}
	
	public Map<State, ArrayList<Double>> getQtable(){
		return qtable;
	}
	
	public ArrayList<Double> getActionforState(State state){
		
		if(this.qtable.containsKey(state)){
			return this.qtable.get(state);
		}
		else{
			//if state does not exist then add the new state and return a list of actions
			ArrayList<Double> actionList = new ArrayList<Double>(Collections.nCopies(37, 0.0));
			qtable.put(state, actionList);
			
			return actionList;
		}
		
	}
	
	public Integer addEntrytoQtable(State state, ArrayList<Double> actions){
		
		if(!this.qtable.containsKey(state)){
			this.qtable.put(state, actions);
			
			return GlobalConstants.SUCCESS;
		}
		
		return GlobalConstants.EXIST;
	}
	
	public Integer addEntrytoQtable(State state){
		
		if(!this.qtable.containsKey(state)){
			
			ArrayList<Double> actionList = new ArrayList<Double>(Collections.nCopies(37, 0.0));
			this.qtable.put(state, actionList);
			
			return GlobalConstants.SUCCESS;
		}
		
		return GlobalConstants.EXIST;
	}
	
	public Integer updateActionReward(State state, Integer action, State stateOld, Double reward){
		
		if(this.qtable.containsKey(stateOld)){
			Double qOld = this.qtable.get(stateOld).get(action);
			ArrayList<Double> actions = getActionforState(state);
			
			if(action != GlobalConstants.CLICK_ACTION){
			
			this.qtable.get(stateOld).set(action, (qOld + GlobalConstants.alpha*(reward + GlobalConstants.gamma * Collections.max(actions)
					- qOld)));
			}else{
				this.qtable.get(stateOld).set(action, (qOld + GlobalConstants.alpha*(reward - qOld)));	
			}
			
			return GlobalConstants.SUCCESS;
		}
		
		return GlobalConstants.FAILURE;
	}
	
	public Double getQvalue(String state, Integer action){
		
		if(this.qtable.containsKey(state)){
			return this.qtable.get(state).get(action);
		}
		
		return 0.0;
	}
	
}
