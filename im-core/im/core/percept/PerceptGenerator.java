package im.core.percept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import im.utility.constants.GlobalConstants;
import im.utility.functions.UtilityFunctions;

public class PerceptGenerator {

	private static PerceptGenerator instance = new PerceptGenerator();
	
	protected PerceptGenerator() {};
	
	public static PerceptGenerator getIntance(){
		return instance;
	}
	

	public List<Double> generateNoisyPercept(List<Integer> env, Integer fixLoc){
		
		List<Double> percept = new ArrayList<Double>(Collections.nCopies(env.size(), 0.0));
		
		for(int i = 0; i < GlobalConstants.maxStates; i++){
			Double e = UtilityFunctions.getInstance().getEccentricity(fixLoc, i);
			
			if(e <= 0.0){
				percept.set(i, (env.get(i)*0.2) + GlobalConstants._randomGenerator.nextGaussian()*0.1);
			}
			else if(e > 0.0 && e <= 5.0){
				percept.set(i, (env.get(i)*0.2) + GlobalConstants._randomGenerator.nextGaussian()*0.5);
			}
			else if(e > 5.0 && e <= 10.0){
				percept.set(i, (env.get(i)*0.2) + GlobalConstants._randomGenerator.nextGaussian()*1.0);
			}
			else if(e > 10.0 && e <= 15.0){
				percept.set(i, (env.get(i)*0.2) + GlobalConstants._randomGenerator.nextGaussian()*1.0);
			}
			else{
				percept.set(i, (env.get(i)*0.2) + GlobalConstants._randomGenerator.nextGaussian()*1.0);
			}
			
		}
		
		//perform value approximation
		ArrayList<Double> list = new ArrayList<Double>();
		for(Double d : percept){
			list.add(Math.abs(d - GlobalConstants.LEVELS[0]));
			list.add(Math.abs(d - GlobalConstants.LEVELS[1]));
			list.add(Math.abs(d - GlobalConstants.LEVELS[2]));
			list.add(Math.abs(d - GlobalConstants.LEVELS[3]));
			list.add(Math.abs(d - GlobalConstants.LEVELS[4]));
			Double element = Collections.min(list);
			
			int index = list.indexOf(element);
			int i = percept.indexOf(d);
			percept.set(i, GlobalConstants.LEVELS[index]);
			list.clear();
		}
		
		return percept;
	}
	
	public List<Boolean> perceptMatch(Double fovealDistance){
		
		for(int i = 0; i < GlobalConstants.FIVE_FEATURE; i++){
			
		}
		
		return null;
	}
	
}
