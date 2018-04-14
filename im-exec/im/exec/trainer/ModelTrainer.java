package im.exec.trainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import im.core.env.VisualGrid;
import im.core.functions.Likelihood;
import im.core.percept.PerceptGenerator;
import im.core.qlearn.Qtable;
import im.core.qlearn.data.BeliefState;
import im.core.qlearn.data.State;
import im.utility.constants.GlobalConstants;
import im.utility.functions.UtilityFunctions;

public class ModelTrainer {
	
	public Double ExploreLearning(VisualGrid env, Integer ntrail){
		
		//Initialise Belief state
		BeliefState.getInstance().initBelief();
		Integer fixated = null;//GlobalConstants._randomGenerator.nextInt(GlobalConstants.maxStates);
		Integer prevFixated = -1;
		State currState = new State(null, null, null, null, null, null);
		State prevState = currState;
		Qtable.getInstance().addEntrytoQtable(prevState);
		Integer nFixations = 0;
		Double totalTime = new Double(0.0);
		Double totalReward = 0.0, immediate_reward = 0.0;
		Boolean transition = true;
		List<Double> lookup = null;
		List<Double> noisyEnv = null;
		
		while(nFixations < GlobalConstants.maxFixations && prevFixated != GlobalConstants.CLICK_ACTION){
			
			prevState = currState;
			System.out.println("Previous State :: " + prevState.toString());
			if(transition){
				//get next state based on belief
				currState = getNewState(fixated, noisyEnv);
				Qtable.getInstance().addEntrytoQtable(currState);
			}
			else{
				//for a random initial fixation get the noisy percept.
				noisyEnv = PerceptGenerator.getIntance().generateNoisyPercept(env.getGrid(), fixated);
				System.out.println(noisyEnv.toString());
				currState = getCurrentState(fixated, noisyEnv);
				Qtable.getInstance().addEntrytoQtable(currState);
			}
			System.out.println("Current State :: " + currState.toString());
			fixated = getGreedyAction(currState, transition);
			
			if(fixated < GlobalConstants.maxStates){
				
				if(!transition){
					lookup = Likelihood.getInstance().likelihoodLookup(fixated, noisyEnv, ntrail);
					//update belief with the new likelihood
					BeliefState.getInstance().updateBelief(lookup);
					
					totalTime += 350.0;//ms
					nFixations++;
				}
				else{
					if(nFixations == 0){
						totalTime += 100.0;//ms
					}
					else{
						totalTime += 37.0 + 2.7 * 
								UtilityFunctions.getInstance().getEccentricity(fixated, prevFixated);//ms
					}
				}
				
				immediate_reward =  (totalTime/1000)* -1;
				
				//Update Q-table
				Qtable.getInstance().updateActionReward(currState, fixated, prevState, immediate_reward);
			}else{
				//A click action is initiated
				if(nFixations == 0 && fixated == GlobalConstants.CLICK_ACTION){
					Qtable.getInstance().updateActionReward(currState, fixated, prevState, -1000.0);
				}else{
				
					totalTime += 350.0;//milisec
					
					immediate_reward = GlobalConstants.POWER_FEATURE_BAG[env.getGrid().get(prevFixated)]/(totalTime/1000);
					
					//Update Q-table
					Qtable.getInstance().updateActionReward(currState, fixated, prevState, immediate_reward);
					
				}
			}
			
			
			prevFixated = fixated;
			totalReward += immediate_reward;
			
			transition = !transition;
			System.out.println();
		}
		
		return totalReward;
	}
	
	public List<Double> ExploitLearning(VisualGrid env, Integer ntrail){
		
		List<Double> list = new ArrayList<Double>(Collections.nCopies(3, 0.0));
		
		//Initialise Belief state
		BeliefState.getInstance().initBelief();
		Integer fixated = null;
		Integer prevFixated = -1;
		State currState = new State(null, null, null, null, null, null);
		Integer nFixations = 0;
		Double totalTime = new Double(0.0);	
		Boolean transition = true;
		List<Double> lookup = null;
		List<Double> noisyEnv = null;
		
		while(nFixations < GlobalConstants.maxFixations && prevFixated != GlobalConstants.CLICK_ACTION){
			
			if(transition){
				//get next state based on belief
				currState = getNewState(fixated, noisyEnv);
			}
			else{
				//for a random initial fixation get the noisy observation.
				noisyEnv = PerceptGenerator.getIntance().generateNoisyPercept(env.getGrid(), fixated);
				currState = getCurrentState(fixated, noisyEnv);
			}
			
			fixated = getExploitedAction(currState);
			
			if(fixated < GlobalConstants.maxStates){
			
				if(!transition){
					lookup = Likelihood.getInstance().likelihoodLookup(fixated, noisyEnv, ntrail);
					//update belief with the new likelihood
					BeliefState.getInstance().updateBelief(lookup);
					
					totalTime += 350.0;//ms
					nFixations++;
				}
				else{
					if(nFixations == 0){
						totalTime += 100.0;//ms
					}
					else{
						totalTime += 37.0 + 2.7 * 
								UtilityFunctions.getInstance().getEccentricity(fixated, prevFixated);//ms
					}
				}
			}else{
				//A click action is initiated
				totalTime += 350.0;//milisec
				Double utility = GlobalConstants.POWER_FEATURE_BAG[env.getGrid().get(prevFixated)]/(totalTime/1000);
				list.set(0, utility);
			}
			
			prevFixated = fixated;
			transition = !transition;
			
		}
		
		list.set(1, totalTime);
		list.set(2, Double.valueOf(nFixations));
		
		return list;
	}

	
	private Integer getExploitedAction(State s){
		List<Double> actions = Qtable.getInstance().getActionforState(s);
		Double value = Collections.max(actions);
		Integer index = actions.indexOf(value);
		
		return index;
	}
	
	private Integer getGreedyAction(State s, Boolean transition){
		List<Double> actions = Qtable.getInstance().getActionforState(s);
		if(transition && actions.size() >= GlobalConstants.maxActions){
			actions.set(GlobalConstants.CLICK_ACTION, -1000.0);
		}
		Double value = Collections.max(actions);
		Integer index = actions.indexOf(value);
		
		
		if(Math.random() > GlobalConstants.EPSILON){
			return index;
		}
		
		return GlobalConstants._randomGenerator.nextInt(GlobalConstants.maxActions);
	}
	
/*	private Integer getSAAction(String state, Integer ntrail, Double[][] noisyEnv, Double temperature){
		
		Integer rand_action = Math.abs((rand.nextInt(GlobalConstants.maxActions)));
		
		Integer policy_action = 0;
		Double maxReward = -99999999999.0;
		
		for(int i = 0; i < GlobalConstants.maxActions; i++){
			
			if(Qtable.getInstance().getQvalue(state, i) > maxReward && i != rand_action){
				maxReward = Qtable.getInstance().getQvalue(state, i);
				policy_action = i;
			}
			
		}
		
		if(Qtable.getInstance().getQvalue(state, rand_action) > Qtable.getInstance().getQvalue(state, policy_action)){
			return rand_action;
		}
		
		
		Double sDelta = (Qtable.getInstance().getQvalue(state, rand_action) -  Qtable.getInstance().getQvalue(state, policy_action)) / 
		(temperature);
		if(Math.random() < Math.exp(sDelta)){
			return rand_action;
		}
		return policy_action;
	}*/
	
	private State getCurrentState(Integer fixatedLoc, List<Double> env){
		
		List<Double> nbour = UtilityFunctions.getInstance().getNeighbourValues(fixatedLoc, env); 
		State s = new State(fixatedLoc, env.get(fixatedLoc), nbour.get(0), nbour.get(1), nbour.get(2), nbour.get(3));
		
		return s;
	}
	
	
	private State getNewState(Integer fixatedLoc, List<Double> env){
		
		Integer s = BeliefState.getInstance().maxBelief();
		State state = null; 
		
		if(s < GlobalConstants.maxStates){
			
			state = new State(s, null, null, null, null, null);
			
		}else{
				//if no 5-feature signal is seen, then compromise for next best from the current evidence.
				Integer[] indices = UtilityFunctions.getInstance().getIndicesOfState(fixatedLoc);
				Integer beliefLoc = null;
				Double maxValue = 0.0;
				if(indices[0] - 1 >= 0){
					Integer n1 = (indices[0]-1) * GlobalConstants.MAX_ROW + indices[1];
					beliefLoc = n1;
					maxValue = env.get(beliefLoc);
				}
				
				if(indices[0] + 1 < GlobalConstants.MAX_ROW){
					Integer n2 = (indices[0]+1) * GlobalConstants.MAX_ROW + indices[1];
					if(env.get(n2) > maxValue){
						maxValue = env.get(n2);
						beliefLoc = n2;
					}
				}
				
				if(indices[1] + 1 < GlobalConstants.MAX_COL){
					Integer n3 = (indices[0]) * GlobalConstants.MAX_ROW + (indices[1]+1);
					if(env.get(n3) > maxValue){
						maxValue = env.get(n3);
						beliefLoc = n3;
					}					
				}
				
				if(indices[1] - 1 >= 0){
					Integer n4 = (indices[0]) * GlobalConstants.MAX_ROW + (indices[1]-1);
					if(env.get(n4) > maxValue){
						maxValue = env.get(n4);
						beliefLoc = n4;
					}
				}
				
				state = new State(beliefLoc, null, null, null, null, null);	
		}
		
		return state;
	}
	
}
