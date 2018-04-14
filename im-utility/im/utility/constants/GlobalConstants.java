package im.utility.constants;

import java.text.DecimalFormat;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;

import umontreal.iro.lecuyer.probdist.InverseGaussianDist;

public class GlobalConstants {

	//Environment Constants 
	public static final int MAX_COL = 6;
	public static final int MAX_ROW = 6;
	public static final int FIVE_FEATURE = 5;
	public static final int FOUR_FEATURE = 4;
	public static final int THREE_FEATURE = 3;
	public static final int TWO_FEATURE = 2;
	public static final int ONE_FEATURE = 1;
	public static final int nFEATURE = 5; 
	public static final float MEAN_WT = (float)0.2;
	public static final int[] FEATURE_BAG = {26, 6, 2, 1, 1};
	public static final Float FEATURE_SIZE = (float)2.15/5;
	
	//Training Constants
	public static final int Episodes = 30000;
	public static final float alpha = (float)0.01;
	public static final float gamma = (float)1.0;
	public static final int maxFixations = 36;
	public static final int maxStates = 36;
	public static final int maxActions = maxStates + 1;
	public static final int CLICK_ACTION = 36;
	public static final double coolingrate = 0.003;
	public static final Double EPSILON = 0.05;
	public static final int[] LINEAR_FEATURE_BAG = {-1, 0, 40, 70, 100, 130};
	public static final int[] POWER_FEATURE_BAG = {-1, 0, 20, 30, 60, 200};
	
	//Perceptual Constants
	public static final float PERSPECTIVE = (float)2.15;
	public static final float GAP_LD = (float) 0.0086;
	public static final float GAP_HD = (float) 0.086;
	public static final float SPACIAL_NOISE_WT = (float) 0.01;
	public static final float FEATURE_NOISE_WT = (float) 1.0;
	public static final Double[] LEVELS = {0.2,0.4,0.6,0.8,1.0}; 
	
	//CRUD Operations
	public static final int SUCCESS = 0;
	public static final int FAILURE = -1;
	public static final int EXIST = 1;
	
	//Program Utilities
	public static final Random _randomGenerator = new Random(System.currentTimeMillis());
	public static final String propertiesFilename = "config.properties";
	public static final NormalDistribution LOW_NOISE = new NormalDistribution();
	public static final NormalDistribution HIGH_NOISE = new NormalDistribution(0, 5);
	public static final InverseGaussianDist discriminability = new InverseGaussianDist(1, 0.2);
	public static final DecimalFormat df = new DecimalFormat("#.##");
	public static final String DILIMITER = ",";
	
	//Program Properties
	public static final String HIGH_DENSITY =  "high.desnsity";
	public static final String LOW_DENSITY =  "low.desnsity";
	public static final String POWER_FUNCTION =  "power.function";
	public static final String LINEAR_FUNCTION =  "linear.function";
			
}
