package swing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 * @author Chirag
 * @date 05/15/2012f
 * @Description: Let x be a training example
 *	Let yx be the desired output for x (0 or 1)
 *	Calculate output Ox = g(Wx)
 *	Calculate error ex = yx – ox
 *	Update weights: Wj =Wj-1 + alpha g'(Wx) ex xj
 *  Threshold units: Wj =Wj-1 + alpha ex xj
 *	Continue until stopping criterion is reached
 *	//g(x) = 0 if x<0
 *	//g(x) = 1 if x>0 
 */
public class SimplePerceptron {
	
	private static HashMap<String, HashMap<String, Integer>> tfidfMap; // map of tweetId and tweet words and TDIDF
	private static HashMap<String, String> idLabelMap;//map of tweetId and sentiment
		
	private static DecimalFormat myFormatter = new DecimalFormat("###.###");
	
	private static int patNum; // number of patterns
	
	//weights
	private static double[] weight; 
	private static double[] oldWeight; 
	private static List<Double> convergedWeights;
	
	//bias
	private double biasWeight;
	private double biasWeightOld;
	
	private int number;
	private double computedOutput;
	JTextArea area;
	private static int split = 0;
	private static int iteration = 0;
	
	public SimplePerceptron(HashMap<String, HashMap<String, Integer>> tfidfMap, HashMap<String, String> idLabelMap, JTextArea area, int split, int iteration){
		this.tfidfMap = tfidfMap;
		this.idLabelMap = idLabelMap;
		this.area = area;
		this.split = split;
		this.iteration = iteration;
		convergedWeights = new ArrayList<Double>();
		intitalizeWeight();
		System.out.println("Total number of words : "+number);
	}
	
	//intialization of weights
	public void intitalizeWeight(){
		patNum = tfidfMap.size();
		String docId = tfidfMap.keySet().iterator().next();
		number = tfidfMap.get(docId).keySet().size();
		Random random = new Random();
		weight = new double[number];
		oldWeight = new double[number];
		biasWeight = Double.parseDouble(myFormatter.format(random.nextDouble()));
		area.append("Initial Weights :" + biasWeight);
		for(int i=0;i<number;i++){
			weight[i] = Double.parseDouble(myFormatter.format(random.nextDouble()));
			area.append(", "+weight[i]);
		}
		area.append("\n");	
	}
	
	//return sentiment based on the tweetId
	public String returnSentiment(String docId){
		return idLabelMap.get(docId);
	}
	
	public void perceptronAlgorithm(){
		List<String> docIds = new ArrayList<String>(tfidfMap.keySet());
		int iter = 0;
		for(int s=0;s<iteration;s++){
			new JProgressBar();
			iter++;
			biasWeightOld = biasWeight;
			for(int i=0;i<weight.length;i++){
				oldWeight[i] = weight[i];
			}
//			System.out.println("work split"+ docIds.size() +", "+ split);
			for(int i=0;i<docIds.size() * split * 0.01;i++){
//				System.out.println("Inside loop 1");
				String docId = docIds.get(i);
				List<String> wordList = new ArrayList<String>(tfidfMap.get(docId).keySet());
//				System.out.println(wordList);
				computedOutput = biasWeight * -1;
				List<Integer> temporary = new ArrayList<Integer>();
				for(int j=0;j<wordList.size();j++){
					double a = tfidfMap.get(docId).get(wordList.get(j)); 
					//System.out.println("A : "+a);
					if( a > 0.0)
						computedOutput += tfidfMap.get(docId).get(wordList.get(j)) * weight[j];
					temporary.add(tfidfMap.get(docId).get(wordList.get(j)));
				}
				//System.out.println(computedOutput);
				String desiredOP = returnSentiment(docId).toString();
				int indexOfOpen = desiredOP.indexOf("[");
				int indexOfClose = desiredOP.indexOf("]");
				activation(computedOutput, desiredOP.substring(indexOfOpen+1, indexOfClose), temporary);
			}
		}	
		convergedWeights.add(biasWeight);
		for(int i=0;i<number;i++){
			convergedWeights.add(weight[i]);
		}
	}
	
	public void activation(double compOutput, String desiredOutput, List<Integer> temp){
//		System.out.println("Into activation function");
		double error = 0.0;
		double learning = 0.02;
		compOutput = compOutput>0 ? 1 : 0;
//		System.out.println(desiredOutput);
		error = Integer.parseInt(desiredOutput) - compOutput;
		if(java.lang.Math.abs(error)!=0){
			biasWeight = Double.parseDouble(myFormatter.format(biasWeight + learning * error * -1)) ;
			for(int i=0;i<number;i++){
				weight[i] = Double.parseDouble(myFormatter.format(weight[i] + (learning * error * temp.get(i))));  
			}	
		}
	}
	
	public void test(){
		
		List<String> docIds = new ArrayList<String>(tfidfMap.keySet());
		System.out.println(docIds.size());
		int count =0;
		double outputTest = 0.0; 
//		System.out.println(convergedWeights.size());
		for(int i= (int) (docIds.size() * split * 0.01);i<docIds.size();i++){
			String docId = docIds.get(i);
			List<String> wordList = new ArrayList<String>(tfidfMap.get(docId).keySet());
			outputTest = 0;
			outputTest = convergedWeights.get(0) * -1; 
			for(int j=0;j<number;j++){
				outputTest += tfidfMap.get(docId).get(wordList.get(j)) * convergedWeights.get(j+1);
				
			}
			String desiredOP = returnSentiment(docId).toString();
			int indexOfOpen = desiredOP.indexOf("[");
			int indexOfClose = desiredOP.indexOf("]");
			desiredOP= desiredOP.substring(indexOfOpen+1, indexOfClose);
			int desiredTest = Integer.parseInt(desiredOP);

			if(outputTest > 0){outputTest = 1;}
			else{outputTest = 0;}
//			System.out.println(outputTest + " -- "+ desiredTest);
			if(outputTest == desiredTest){
				count++;
			}		
		}
//		System.out.println("Count :" + count);
//		System.out.println(java.lang.Math.round(docIds.size() - docIds.size() * split * 0.01));
		double accuracy = (double)count/java.lang.Math.round(docIds.size() - docIds.size() * split * 0.01) * 100;
		area.append("---------------------------------------------------------------------------------------------------------------\n");
		area.append("Final Weights :" + convergedWeights + "\n");
		area.append("Accuracy Of Simple Perceptron : "+ accuracy + "\n");
		System.out.println("Accuracy Of Simple Perceptron : "+ accuracy);
	}
	
}
