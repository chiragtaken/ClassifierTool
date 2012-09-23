package ai.classifier.readwrite;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import javax.swing.JTextArea;

import ai.classifier.algorithms.SimplePerceptron;

/**
 * @author Chirag
 * @date 05/10/2012
 * reading of csv file into map 
 */
public class Next {
	
	public static HashMap<String, HashMap<String,String>> data;	
	private static List<String> globalWordList = new ArrayList<String>();
	public static List<String> stopList = new ArrayList<String>();
	private static String filename, algoName, splitPercentage;
	private static HashMap<String, Double> idf;
	private static HashMap<String, String> idLabelMap;
	private static HashMap<String,HashMap<String, Integer>> tf;
	private static HashMap<String,HashMap<String, Integer>> tfidf;
	private static int iteration;
	
	JTextArea area;
	
	public Next(String fileName, JTextArea area, String algoName, String splitPercentage, String iteration ) {
		tf = new HashMap<String,HashMap<String, Integer>>();
		idf = new HashMap<String, Double>();
		tfidf = new HashMap<String,HashMap<String, Integer>>();
		idLabelMap = new HashMap<String, String>();
		
		this.iteration = Integer.parseInt(iteration);
		this.filename = fileName;
		this.algoName = algoName;
		this.splitPercentage = splitPercentage;
//		System.out.println(filename + ", "+ algoName +" ,"+ splitPercentage);
		this.area = area;
		read(area);
		readfile("Z:\\Course\\Spring\\788-BrainThory\\Project\\Nstoplist.txt",area);
		refining(area);
		selectAlgoAndSplit();
	}
	
		
	public void read(JTextArea area){
		data = new HashMap<String, HashMap<String,String>>();		
		try{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream	 dstream = new DataInputStream(fstream);
			BufferedReader	reader = new BufferedReader(new InputStreamReader(dstream));
			String line;
			while((line = reader.readLine()) != null){
				String[] str = line.split(",");
				String docId = str[0];
				String label = str[1];
				String text = str[2];
				data.put(docId, new HashMap<String,String>());
				data.get(docId).put(label, text);
			}
			
			area.append("Total documents in the corpus : "+data.size()+"\n");
		}
		catch(Exception e){
			area.append("Error in reading .CSV File \n");
			System.out.println("Error In read file : "+e);
		}		
	}	
	
	
	public void readfile(String path, JTextArea area) {
		area.append("Reading Stop List \n");
		try{
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream	 dstream = new DataInputStream(fstream);
			BufferedReader	reader = new BufferedReader(new InputStreamReader(dstream));

			String line;
			while((line = reader.readLine()) != null){
				String data = line.toString();
				stopList.add(data);
			}
			area.append("Total Number of stop words :"+stopList.size()+"\n");
		}
		catch(Exception e){
			System.out.println("Error In reading file");
		}
	}
	

	public void refining(JTextArea area){
		area.append("Refining text of each document \n");
		Iterator<String> iterator = data.keySet().iterator();
		while(iterator.hasNext()){
			String docId = iterator.next();
			String text = "";
			String label = data.get(docId).keySet().iterator().next();
//			System.out.println(docId + ", "+ label);
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
//			System.out.println(text);
//			System.out.println(textNew);
			data.get(docId).remove(label);
			data.get(docId).put(label, textNew.trim().toLowerCase());
		}		
		
	}
	
	private int split = 0;
	public void selectAlgoAndSplit(){
		createIdSentiment();
		createTF();createIDF();generateTFIDF();
		
		if(splitPercentage.equalsIgnoreCase("80%-20%")){
			split = 80;
		}
		else if(splitPercentage.equalsIgnoreCase("70%-30%")){
			split = 70;
		}
		else if(splitPercentage.equalsIgnoreCase("60%-40%")){
			split = 60;	
		}
		
		if(algoName.equalsIgnoreCase("Simple Perceptron")){
			area.append("Entering Into Simple Perceptron \n");
			simplePerceptron();
		}
		else if(algoName.equalsIgnoreCase("Multilayer Perceptron")){
			
		}
		else if(algoName.equalsIgnoreCase("Naive Bayes")){
			
		}
		
		
	}
	
	public void simplePerceptron(){
		SimplePerceptron perceptron = new SimplePerceptron(tfidf, idLabelMap, area,split, iteration);
		perceptron.perceptronAlgorithm();
		perceptron.test();
		perceptron = null;
		Runtime.getRuntime().gc();
	}
	
	
	public void multilayerPerceptron(){
		
	}

	
	public void naiveBayes(){
		
	}
	
	public void createIdSentiment(){
		Iterator<String> iterator = data.keySet().iterator();
		while(iterator.hasNext()){
			String docId = iterator.next();
			String label = data.get(docId).keySet().toString();
			idLabelMap.put(docId,label);
//			else if(data.get(docId).containsKey(1)){
//				idLabelMap.put(docId,1);
//			}
		}
	}
	
	//TF calculation
	//TF = HashMap<tweetId, HashMap<words,count>>
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
//		System.out.println(tf);
	}
	
	//IDF calculation
	//IDF = HashMap<String, Double>
	public void createIDF(){
		int totalDocs = data.size();
//		System.out.println("Total Docs : "+ totalDocs);
//		System.out.println(globalWordList.size());
		for(int i=0;i<globalWordList.size();i++){
			int count = Collections.frequency(globalWordList, globalWordList.get(i));
//			System.out.println("Count :"+ count);
			if(count>=1 && count <= 500){
				idf.put(globalWordList.get(i), java.lang.Math.log10((double)totalDocs /count));
			}
		}
//		System.out.println("size of idf : "+idf.size());
		for(int i=0;i<stopList.size();i++){
			if(idf.containsKey(stopList.get(i))){
				idf.remove(stopList.get(i));
			}
		}
//		System.out.println("size of idf : "+idf.size());
		globalWordList = null;	
		Runtime.getRuntime().gc();
	}
			
	//generate TFIDF
	//TFIDF = new HashMap<Long, HashMap<String,Integer>>
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
