package ai.classifier.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ai.classifier.readwrite.WriteFile;

/**
 * @author Chirag
 * Term Frequency:  
 */
public class CalculateTF {
	
	private String docData;
	
	public CalculateTF(String docData) {
		this.docData = docData;
	}
	
	/**
	 * 
	 */
	public void calculateTermFreqency(){
		String docId =  docData.split(" ")[0];
		String docText = docData.split(" ")[2];
		List<String> wordList = new ArrayList<String>(Arrays.asList(docText.split(" ")));
		wordList.removeAll(Arrays.asList("", null));
		
		List<Integer> termFreq = new ArrayList<Integer>(); 
		for(int k=0;k<wordList.size();k++){
			termFreq.add(Collections.frequency(wordList, wordList.get(k)));
		}
		
		String termFrequencyData = docId+";"+wordList+";"+termFreq;
		WriteFile.writeInTempFile(termFrequencyData, new File("File/TermFrequency.txt"));
	}

}


/*	
	public void calculateTermFrequency(){
		String docId =  docData.split(" ")[0];
		String docText = docData.split(" ")[2];
		
		List<String> wordList = new ArrayList<String>(Arrays.asList(docText.split(" ")));
		wordList.removeAll(Arrays.asList("", null));
		tf.put(docId,new HashMap<String, Integer>());
		for(int k=0;k<wordList.size();k++){
			tf.get(docId).put(wordList.get(k), Collections.frequency(wordList, wordList.get(k)));	
		}
		
		
		//thinking of creating one more temporary file which can store TF 
		//instead of keeping stuff in memory for convenience
	}
*/
/*
	public void createTF(){
		List<String> idlist = new ArrayList<String>(data.keySet());
		for(int i=0;i<idlist.size();i++){
			String docId =  idlist.get(i);
			String label = data.get(docId).keySet().iterator().next();
			String text = data.get(docId).get(label).toString();			
			List<String> wordList = new ArrayList<String>(Arrays.asList(text.split(" ")));
			wordList.removeAll(Arrays.asList("", null));
//			System.out.println(docId + ", "+wordList);
			tf.put(docId,new HashMap<String, Integer>());
			for(int k=0;k<wordList.size();k++){
				tf.get(docId).put(wordList.get(k), Collections.frequency(wordList, wordList.get(k)));	
			}
		}
		System.out.println(tf);
	}
*/
