package ai.classifier.preprocessing;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ai.classifier.refinment.RefineData;

/**
 * @author Chirag
 * 
 */
public class CalculateIDF {
	
	private List<String> globalWordList;
	private static HashMap<String, Double> idf;
	
	public CalculateIDF() {
		idf = new HashMap<String, Double>();
		this.globalWordList = RefineData.getGlobalWordList();
	}
	
	public void calculateInverseDocumentFrequency(int totalDocs){
		for(int i=0;i<globalWordList.size();i++){
			int count = Collections.frequency(globalWordList, globalWordList.get(i));
			if(count>=1 && count <= 500){
				idf.put(globalWordList.get(i), java.lang.Math.log10((double)totalDocs /count));
			}
		}
	}
	
}
	
/*	
	public void createIDF(){
		int totalDocs = data.size();
		for(int i=0;i<globalWordList.size();i++){
			int count = Collections.frequency(globalWordList, globalWordList.get(i));
			if(count>=1 && count <= 500){
				idf.put(globalWordList.get(i), java.lang.Math.log10((double)totalDocs /count));
			}
		}
		for(int i=0;i<stopList.size();i++){
			if(idf.containsKey(stopList.get(i))){
				idf.remove(stopList.get(i));
			}
		}
		globalWordList = null;	
		Runtime.getRuntime().gc();
	}
*/	
	