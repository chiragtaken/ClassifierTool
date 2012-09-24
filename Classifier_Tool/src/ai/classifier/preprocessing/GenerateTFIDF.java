package ai.classifier.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ai.classifier.objects.InverseDocumentFrequency;
import ai.classifier.objects.TermFrequency;
import ai.classifier.readwrite.ReadFile;
import ai.classifier.readwrite.WriteFile;

/**
 * @author Chirag
 *
 */
public class GenerateTFIDF {
	//private HashMap<String,HashMap<String, Integer>> tfidf;
	private List<Integer> tfidfValues;
	
	public GenerateTFIDF() {
		this.tfidfValues = new ArrayList<Integer>();
		//tfidf = new HashMap<String,HashMap<String, Integer>>();
	}
	
	/**
	 * @param totalDocs
	 * 
	 */
	public void generateTDIF(int totalDocs){
		InverseDocumentFrequency idfObject = ReadFile.readIDFFile(new File("File/InverseDocumentFrequency.txt")); 
		List<String> globalWordList = idfObject.getGlobalWordList();
		List<String> globalWordCount = idfObject.getGlobalWordCount();
		
		while(totalDocs > 0){
			TermFrequency termFrequency = ReadFile.readTFFile(new File("File/TermFrequency.txt"), totalDocs);
			String docId = termFrequency.getDocId();
			List<String> docWords = termFrequency.getDocWords();
			List<String> docWordsCount = termFrequency.getDovWordsValues();
			
			//tfidf.put(docId,new HashMap<String, Integer>());
			for(int i=0;i<globalWordList.size();i++){
				if(docWords.contains(globalWordList.get(i))){
					int index = docWords.indexOf(globalWordList.get(i));
					Double wordIDFValue = Double.parseDouble(globalWordCount.get(i));
					Double wordTFValue = Double.parseDouble(docWordsCount.get(index));
					if(wordIDFValue * wordTFValue > 0){
						tfidfValues.add(1);
						//tfidf.get(docId).put(docWords.get(index),1);
					}
					else{
						tfidfValues.add(0);
						//tfidf.get(docId).put(docWords.get(index),0);
					}
				}
				else{
					tfidfValues.add(0);
					//tfidf.get(docId).put(globalWordList.get(i),0);
				}
			}
			String tfidfData = docId+";"+globalWordList+";"+tfidfValues;
			WriteFile.writeInTempFile(tfidfData, new File("File/tfidf.txt"));
			totalDocs--;
		}
	}
	
}	
	
/*	public void generateTFIDF(){
		List<String> wordList = new ArrayList<String>(idf.keySet());		
		idf = null;Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
		Iterator<String> iterator = tf.keySet().iterator();
		while(iterator.hasNext()){
			String docId = iterator.next();
			tfidf.put(docId, new HashMap<String, Integer>());
			for(int j=0;j<wordList.size();j++){
				if(tf.get(docId).containsKey(wordList.get(j))){
					tfidf.get(docId).put(wordList.get(j), 1);
				}
				else{
					tfidf.get(docId).put(wordList.get(j), 0);
				}
			}
		}
		tf=null;Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
	}
*/


