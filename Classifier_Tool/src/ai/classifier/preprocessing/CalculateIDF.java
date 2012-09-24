package ai.classifier.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ai.classifier.readwrite.WriteFile;
import ai.classifier.refinment.RefineData;

/**
 * @author Chirag
 * Inverse Document Frequency:
 */
public class CalculateIDF {	
	private List<String> globalWordList;
	private List<Integer> countList;
	//private HashMap<String, Double> IDF;
	
	public CalculateIDF() {
		//this.IDF = new HashMap<String, Double>();
		this.countList = new ArrayList<Integer>();
		this.globalWordList = RefineData.getGlobalWordList();
	}
	
	/**
	 * @param totalDocs
	 */
	public void calculateInverseDocumentFrequency(int totalDocs){
		for(int i=0;i<globalWordList.size();i++){
			int count = Collections.frequency(globalWordList, globalWordList.get(i));
			if(count>=1 && count <= 500){
				countList.add(count);
			}
			else{
				globalWordList.remove(i);
			}
		}
		String idf = globalWordList+";"+countList;
		WriteFile.writeInTempFile(idf, new File("File/InverseDocumentFrequency.txt"));
	}

}

/*	public HashMap<String, Double> calculateInverseDocumentFrequencyInline(int totalDocs){
		for(int i=0;i<globalWordList.size();i++){
			int count = Collections.frequency(globalWordList, globalWordList.get(i));
			if(count>=1 && count <= 500){
				IDF.put(globalWordList.get(i), java.lang.Math.log10((double)totalDocs /count));
			}
		}
		return IDF;
	}
*/		
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
	