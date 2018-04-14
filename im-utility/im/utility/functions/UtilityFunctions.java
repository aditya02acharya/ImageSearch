package im.utility.functions;

import im.utility.constants.GlobalConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class UtilityFunctions {

	private static UtilityFunctions instance = new UtilityFunctions();
	
	private static HashMap<Integer, Integer[]> stateMap = new HashMap<Integer, Integer[]>();
	
	
	static{
		int state = 0;
		for(int nrow = 0; nrow < GlobalConstants.MAX_ROW; nrow++){
			for(int ncol = 0; ncol < GlobalConstants.MAX_COL; ncol++){
				Integer[] index = new Integer[2];
				index[0] = nrow;
				index[1] = ncol;
				stateMap.put(state, index);
				state++;
			}
		}
	}
	
	protected UtilityFunctions(){};
	
	public static UtilityFunctions getInstance(){
		return instance;
	}
	
	public String getPropValues(String propertyName){
		 
		String result = "";
		Properties prop = new Properties();
 
		InputStream inputStream = null;

			try {
				inputStream = new FileInputStream(GlobalConstants.propertiesFilename);
				prop.load(inputStream);
				// get the property value and print it out
				result = prop.getProperty(propertyName);	
				inputStream.close();
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("property file '" + GlobalConstants.propertiesFilename + "' not found in the classpath");
			}
			return result;
	}
	
	public Integer[] getIndicesOfState(Integer state){
		
		return stateMap.get(state);
	}
	
	public Double getEccentricity(Integer fixated, Integer external){
		
		String gapProperty = getPropValues(GlobalConstants.HIGH_DENSITY);
		
		boolean gProp = Boolean.valueOf(gapProperty);
		
		Integer[] fix_index = getIndicesOfState(fixated);
		Integer[] ext_index = getIndicesOfState(external);
		
		Float fix_x = (float)(fix_index[0] * (GlobalConstants.PERSPECTIVE + 
				(gProp?GlobalConstants.GAP_HD:GlobalConstants.GAP_LD)));
		Float fix_y = (float)(fix_index[1] * (GlobalConstants.PERSPECTIVE + 
				(gProp?GlobalConstants.GAP_HD:GlobalConstants.GAP_LD)));
		
		Float ext_x = (float)(ext_index[0] * (GlobalConstants.PERSPECTIVE + 
				(gProp?GlobalConstants.GAP_HD:GlobalConstants.GAP_LD)));
		Float ext_y = (float)(ext_index[1] * (GlobalConstants.PERSPECTIVE + 
				(gProp?GlobalConstants.GAP_HD:GlobalConstants.GAP_LD)));	
		
		Float a = Math.abs(ext_x - fix_x);
		Float b = Math.abs(ext_y - fix_y);
		
		return (Math.sqrt((a*a) + (b*b)));
		
	}
	
	public ArrayList<String> replicateDataInArray(ArrayList<String> list,String data, Integer n){
		
		for(int i = 0; i < n; i++){
			list.add(data);
		}
		
		return list;
	}
	
	public List<Double> getNeighbourValues(Integer state, List<Double> env){
		
		List<Double> list = new ArrayList<Double>(Collections.nCopies(4, null));
		
		Integer[] indices = UtilityFunctions.getInstance().getIndicesOfState(state);
		
		//neighbour-1 
		if(indices[0] - 1 < 0){
			list.set(0, null);
		}
		else{
			Integer n1 = (indices[0]-1) * GlobalConstants.MAX_ROW + indices[1];
			list.set(0, env.get(n1));
		}
		
		//neighbour-2
		if(indices[0] + 1 >= GlobalConstants.MAX_ROW){
			list.set(1, null);
		}
		else{
			Integer n2 = (indices[0]+1) * GlobalConstants.MAX_ROW + indices[1];
			list.set(1, env.get(n2));
		}
		
		//neighbour-3
		if(indices[1] + 1 >= GlobalConstants.MAX_COL){
			list.set(2, null);
		}
		else{
			Integer n3 = (indices[0]) * GlobalConstants.MAX_ROW + (indices[1]+1);
			list.set(2, env.get(n3));
		}		
		
		//neighbour-4
		if(indices[1] - 1 < 0){
			list.set(3, null);
		}
		else{
			Integer n4 = (indices[0]) * GlobalConstants.MAX_ROW + (indices[1]-1);
			list.set(3, env.get(n4));
		}		
		
		return list;
	}
	
}
