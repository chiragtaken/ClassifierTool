package ai.classifier.refinment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chirag
 * This class is used to refine the data and remove
 * unnecessary words which does not convey any meaning 
 */
public class RefineData {
	
	private String docText;
	private static List<String> stopList;
	private static List<String> globalWordList = new ArrayList<String>();
	
	public RefineData(String docText) {
		this.docText = docText;
	}
	
	public static void setStopList(List<String> stopList){
		RefineData.stopList = stopList;
	}
	public static List<String> getGlobalWordList(){
		return globalWordList;
	}
	
	/**
	 * In order to search for a word of docText in stopList
	 * we need quick mechanism. 
	 * docText has maximum of 15-20 words. I'll create hashset of these
	 * words and search each word in stopList this will be quick. I need 
	 * to make only 15 to 20 searches in stopList   
	 */
	public String refineDocText(){ 
		HashSet<String> docTextToSet = convertDocTextToSet();
		if(!docTextToSet.isEmpty()){
			Iterator<String> docSetIterator = docTextToSet.iterator();
			while(docSetIterator.hasNext()){
				String docString = docSetIterator.next();
				if(stopList.contains(docString)){
					docTextToSet.remove(docString);
				}
			}
		}
		String refinedDocText  = convertDocSetToText(docTextToSet);
		return refinedDocText;
	}
	
	/**
	 * converting docText to docSet
	 */
	private HashSet<String> convertDocTextToSet(){
		HashSet<String> docTextToSet = new HashSet<String>(Arrays.asList(docText.split(" ")));
		return docTextToSet;
	}
	
	/**
	 * Converting back from docSet to deocText
	 */
	private String convertDocSetToText(HashSet<String> docTextToSet){
		String docText = "";
		Iterator<String> docSetIterator = docTextToSet.iterator();
		while(docSetIterator.hasNext()){
			String docString = docSetIterator.next();
			docString = docString.replaceAll("[^a-zA-Z]+","").replaceAll("(.)\\1+", "$1").trim() + " ";
			globalWordList.add(docString);
			docText += docString;
		}
		return docText;
	}	
	
}	


/*	
	public void refining(){
		area.append("Refining text of each document \n");
		
		Iterator<String> iterator = data.keySet().iterator();
		while(iterator.hasNext()){
			String docId = iterator.next();
			String text = "";
			String label = data.get(docId).keySet().iterator().next();
			if(data.get(docId).containsKey(label)){
				text = data.get(docId).get(label);
			}
			for(int i=0;i<stopList.size();i++){
				if(text.indexOf(stopList.get(i)) > -1){
					text.replaceAll(stopList.get(i),"");
				}
			}
			String[] textArray = text.trim().split(" ");
			String textNew = "";
			for(int i=0;i<textArray.length;i++){				
				textNew += textArray[i].replaceAll("[^a-zA-Z]+","").replaceAll("(.)\\1+", "$1").trim() + " ";
				globalWordList.add(textArray[i].trim().replaceAll("[^a-zA-Z]+","").replaceAll("(.)\\1+", "$1").toLowerCase().trim());
				globalWordList.removeAll(Arrays.asList("", null));
			}				
			textNew.replaceAll( "\\W", "" );
			data.get(docId).remove(label);
			data.get(docId).put(label, textNew.trim().toLowerCase());
		}		
		
	}
*/
