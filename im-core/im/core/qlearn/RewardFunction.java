package im.core.qlearn;

import im.utility.constants.GlobalConstants;

public class RewardFunction {

	private static RewardFunction instance = new RewardFunction(); 
	
	protected RewardFunction() {};
	
	public static RewardFunction getInstance(){
		return instance;
	}
	
	public Double getPowerReward(Double duration, Integer nFeatures){
		
		Double utility = GlobalConstants.POWER_FEATURE_BAG[nFeatures]/(duration/1000.0);
		
		return utility;
	}
	
}
