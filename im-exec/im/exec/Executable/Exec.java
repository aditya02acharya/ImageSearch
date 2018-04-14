package im.exec.Executable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.core.env.VisualGrid;
import im.core.functions.Likelihood;
import im.exec.trainer.ModelTrainer;
import im.utility.constants.GlobalConstants;

public class Exec {

	public static void main(String[] args) {
		
		//create the environment
		VisualGrid grid = new VisualGrid();
		File likeliFile = new File("Likelihood_High_Density.ser");
		if(!likeliFile.exists()){
			Likelihood.getInstance().init();
			System.out.println("Saving likelihood Data to disk.");
	    	FileOutputStream f;
			try {
				f = new FileOutputStream(likeliFile);
		    	ObjectOutputStream s = new ObjectOutputStream(f);          
		    	s.writeObject(Likelihood.getInstance().getLikelihood());
		    	s.flush();
		    	s.close();
		    	System.out.println("Data Saved.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		else{
			System.out.println("Loading saved likelihood data from file.");
			FileInputStream fi;
			try {
				fi = new FileInputStream(likeliFile);
				ObjectInputStream si = new ObjectInputStream(fi);  
				Map<String, ArrayList<Integer>> fileObj = 
						(HashMap<String, ArrayList<Integer>>)si.readObject();
				
				Likelihood.getInstance().setLikelihood(fileObj);
				
				si.close();
				System.out.println("Data loaded.");
				Likelihood.getInstance().getSize();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}         
		}
		//create the trainer instance 
		ModelTrainer trainer = new ModelTrainer();
	
		File f = new File("rewardRate.txt");
		
		if(f.exists()){
			f.delete();
		}
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("rewardRate.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Start the training of the model
		for(int nTrail = 1; nTrail <= GlobalConstants.Episodes; nTrail++){
			System.out.println(nTrail);
			grid.shuffleArray();
			Double reward = trainer.ExploreLearning(grid, nTrail);
			writer.println(nTrail+ "\t" + GlobalConstants.df.format(reward));
			writer.flush();
		}
		
		writer.close();
		
		System.out.println("Exploiting the knowledge learnt.");
		//Exploit the learned knowledge 
		Double meanUtility = 0.0;
		Double meanTime = 0.0;
		Double meanFixations = 0.0;
		for(int nTrial = 0; nTrial < 50; nTrial++){
			System.out.println(nTrial);
			grid.shuffleArray();
			List<Double> result = trainer.ExploitLearning(grid, nTrial);
			meanUtility += result.get(0);
			meanTime += result.get(1);
			meanFixations += result.get(2);
		}
		System.out.println();
		System.out.println("Model Results :: ");
		System.out.println();
		
		System.out.println("Mean Utility : " + meanUtility/50.0);
		System.out.println("Mean TimeSpent : " + meanTime/50.0);
		System.out.println("Mean number of Fixations : " + meanFixations/50.0);

/**************************************END********************************/	
	}
	
}
