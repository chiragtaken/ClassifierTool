/**
 * 
 */
package ai.classifier.preprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chirag
 *
 */
public class GenerateTFIDF {
	private HashMap<String,HashMap<String, Integer>> tfidf;
	
	public GenerateTFIDF() {
		tfidf = new HashMap<String,HashMap<String, Integer>>();
	}
	
	public void generateTFIDF(){
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
}
