package im.core.functions;

import im.core.env.VisualGrid;
import im.core.percept.PerceptGenerator;
import im.utility.constants.GlobalConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Likelihood {

	private static Likelihood instance = new Likelihood();
	
	private Map<String, ArrayList<Integer>> likelihood = new HashMap<String, ArrayList<Integer>>();
	//private Map<String, Double> likelihoodShape = new HashMap<String, Double>();
	
	protected Likelihood(){};
	
	public static Likelihood getInstance(){
		return instance;
	}
	
	
	
	public Map<String, ArrayList<Integer>> getLikelihood() {
		return likelihood;
	}

	public void setLikelihood(Map<String, ArrayList<Integer>> likelihood) {
		this.likelihood = likelihood;
	}

	public void init(){
		
		VisualGrid _grid = new VisualGrid();
		_grid.shuffleArray();
		for(int hypo = 0; hypo < GlobalConstants.maxActions; hypo++){
			for(int fix = 0; fix < GlobalConstants.maxStates; fix++){
				System.out.println("Running for fixation location = " + fix + "and hypothesis = " + hypo);
				likelihoodFeatureGenerate(fix, hypo, this.likelihood, _grid);
			}
		}
		
	}
	
	private void likelihoodFeatureGenerate(Integer fixationLocation, Integer hypothesis, Map<String, ArrayList<Integer>> likelihoodEnv, VisualGrid grid){
		
		for(int likelihoodTrial = 0; likelihoodTrial < 400; likelihoodTrial++){
			List<Integer> env = grid.getGrid();
			int i = env.indexOf(5);
			//swap values to make hypothesis true
			if(hypothesis < GlobalConstants.maxStates){
				Integer temp = env.get(hypothesis);
				env.set(hypothesis, env.get(i));
				env.set(i, temp);
			}
			else{
				env.set(i, 1);
			}
			
			List<Double> noisyEnv = PerceptGenerator.getIntance().generateNoisyPercept(env, fixationLocation);
			
			String key = generateKey(fixationLocation, hypothesis, noisyEnv);
			
			if(likelihoodEnv.containsKey(key)){
				likelihoodEnv.get(key).set(0, likelihoodEnv.get(key).get(0)+1);
				//likelihoodEnv.get(key).set(1, likelihoodEnv.get(key).get(1)+1);
			}
			else{
				ArrayList<Integer> list = new ArrayList<Integer>(Collections.nCopies(2, 1));
				list.set(1, 400);
				
				likelihoodEnv.put(key, list);
			}	
			if(hypothesis >= GlobalConstants.maxStates){
				env.set(i, 5);
			}
			grid.shuffleArray();
		}
		
	}
	
	public List<Double> likelihoodExploitedLookup(Integer fixationLoc, List<Double> observation, Integer ntrials){
		
		List<Double> lk = new ArrayList<Double>(Collections.nCopies(GlobalConstants.maxActions, 0.0));
		
		for(int hypo = 0; hypo < GlobalConstants.maxActions; hypo++){
			String key = generateKey(fixationLoc, hypo, observation);
			if(this.likelihood.containsKey(key)){
				ArrayList<Integer> list = this.likelihood.get(key);
				Double likely = (double)list.get(0)/(double)list.get(1);
				lk.set(hypo, likely);
			}
		}
		
		return lk;		
		
		
	}
	
	public List<Double> likelihoodLookup(Integer fixationLoc, List<Double> observation, Integer ntrials){
		
		List<Double> lk = new ArrayList<Double>(Collections.nCopies(GlobalConstants.maxActions, 0.0));
		
		for(int hypo = 0; hypo < GlobalConstants.maxActions; hypo++){
			String key = generateKey(fixationLoc, hypo, observation);
			if(this.likelihood.containsKey(key)){
				ArrayList<Integer> list = this.likelihood.get(key);
				Double likely = (double)list.get(0)/(double)list.get(1);
				lk.set(hypo, likely);
				
				//update the likelihood count.
/*				this.likelihood.get(key).set(0, this.likelihood.get(key).get(0)+1);
				Set<String> keyList = this.likelihood.keySet();
				
				String str = fixationLoc+","+hypo;
				
				for(String s : keyList){
					if(s.contains(str)){
						this.likelihood.get(s).set(1, this.likelihood.get(key).get(1)+1);
					}
				}*/
				
			}
			else{
				ArrayList<Integer> l = new ArrayList<Integer>(Collections.nCopies(2, 0));
				l.set(0, 1);
				l.set(1, 400);
				this.likelihood.put(key, l);
				lk.set(hypo, (double)l.get(0)/(double)l.get(1));
			}
		}
		
		return lk;
	}
	
	private String generateKey(Integer fix, Integer hypo, List<Double> percept){
		
		String key = fix + GlobalConstants.DILIMITER + hypo + GlobalConstants.DILIMITER + percept.indexOf(Collections.max(percept)); 
		
		return key;
	}
	
	public void getSize(){
		System.out.println(this.likelihood.size());
	}
}
