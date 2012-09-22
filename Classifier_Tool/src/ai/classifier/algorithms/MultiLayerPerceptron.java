package swing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Chirag
 * @date 05/25/2012
 * @Description: 
 */
public class MultiLayerPerceptron {
	
	//user defineable variables
	 public static int iteration = 500; //number of training cycles
	 public static int numInputs; //number of inputs - this includes the input bias
	 public static int numHidden; //number of hidden units
	 public static int numPatterns; //number of training patterns (negative, positive)
	 public static double LR_IH = 0.7; //learning rate (I ---> H)
	 public static double LR_HO = 0.07; //learning rate (H ---> O)

	 //process variables
	 public static int num;
	 public static double errThisPat;
	 public static double outPred;
	 public static double error;

	 //training data
	 public static double[][] trainInputs ;
	 public static double[] trainOutput;

	 //the outputs of the hidden neurons
	 public static double[] hiddenVal;

	 //the weights
	 public static double[][] weightsIH;
	 public static double[] weightsHO;
	 
	 
	 public MultiLayerPerceptron(HashMap<Long, HashMap<String, Integer>> tfidfMap, HashMap<Long, Integer> idSentimentMap) {
		long tweetId = tfidfMap.keySet().iterator().next();
		numInputs = tfidfMap.get(tweetId).keySet().size() + 1; //1 is added for bias 5035 + 1 
		numPatterns = tfidfMap.size(); //500
		numHidden = (int)(2*numInputs - 1); // number Hidden = 2 * number Input - 1
		System.out.println("Number Inputs : "+numInputs + "Number patterns : "+ numPatterns + "Number Hidden : "+ numHidden); 
		
		trainInputs  = new double[numPatterns][numInputs];
		trainOutput = new double[numPatterns];
		weightsIH = new double[numInputs][numHidden];
		weightsHO = new double[numHidden];	
		hiddenVal  = new double[numHidden];
		
		List<Long> list = new ArrayList<Long>(tfidfMap.keySet());
		for(int i=0;i<numPatterns;i++){
			long tweetIds = list.get(i);
			trainOutput[i] = idSentimentMap.get(tweetIds);
			List<String> list2 = new ArrayList<String>(tfidfMap.get(tweetIds).keySet()); // numInput = list2.size() + 1
			for(int j=0;j<numInputs;j++){
				if(j == numInputs-1){
					trainInputs[i][j] = 1; // bias
				}else{
					trainInputs[i][j] = tfidfMap.get(tweetIds).get(list2.get(j));
				}
			}
		}
		list =null;
		Runtime.getRuntime().gc();		
	 }
	 
	 public void perceptronAlgorithm(){

	  //initiate the weights
	  initWeights();
	  //train the network
	    for(int j = 0;j <= iteration;j++){
	        for(int i = 0;i<8000;i++){//(numPatterns*0.8)
	            //select a pattern at random
	            num =  i;
	            calcNet();
	            //change network weights
	            WeightChangesHO();
	            WeightChangesIH();
	        }
	        calcOverallError();
	        System.out.println("iteration = " + j + " Error = " + error);
	    }
	    //training has finished%
	    
	    //display the results
	    displayResults();

	 } 
	
	 public static void calcNet()
	 {
	    //calculate the outputs of the hidden neurons
	    //the hidden neurons are tanh
	    for(int i = 0;i<numHidden;i++)
	    {
		hiddenVal[i] = 0.0;

	        for(int j = 0;j<numInputs;j++)
	        hiddenVal[i] = hiddenVal[i] + (trainInputs[num][j] * weightsIH[j][i]);
	        hiddenVal[i] = tanh(hiddenVal[i]);
	    }

	   //calculate the output of the network
	   //the output neuron is linear
	   outPred = 0.0;
	   for(int i = 0;i<numHidden;i++)
	    outPred = outPred + hiddenVal[i] * weightsHO[i];
	   
	   	outPred = outPred>0 ? 1 : 0;
	    //calculate the error
	    errThisPat = outPred - trainOutput[num];
	 }

	 public static void WeightChangesHO()
	 //adjust the weights hidden-output
	 {
	   for(int k = 0;k<numHidden;k++)
	   {
	    double weightChange = LR_HO * errThisPat * hiddenVal[k];
	    weightsHO[k] = weightsHO[k] - weightChange;
	   }
	 }
	 public static void WeightChangesIH()
	 //adjust the weights input-hidden
	 {
	  for(int i = 0;i<numHidden;i++)
	  {
	   for(int k = 0;k<numInputs;k++)
	   {
	    double x = 1 - (hiddenVal[i] * hiddenVal[i]);
	    x = x * weightsHO[i] * errThisPat * LR_IH;
	    x = x * trainInputs[num][k];
	    double weightChange = x;
	    weightsIH[k][i] = weightsIH[k][i] - weightChange;
	   }
	  }
	 }
	 public static void initWeights()
	 {

	  for(int j = 0;j<numHidden;j++)
	  {
	    weightsHO[j] = (Math.random() - 0.5)/2;
	    for(int i = 0;i<numInputs;i++)
	    weightsIH[i][j] = (Math.random() - 0.5)/5;
	  }
	 }
	 public static double tanh(double x)
	 {
	    if (x > 20)
	        return 1;
	    else if (x < -20)
	        return -1;
	    else
	        {
	        double a = Math.exp(x);
	        double b = Math.exp(-x);
	        return (a-b)/(a+b);
	        }
	 }

	 public static void displayResults()
	    {int count = 0;
	     for(int i = 8000 ;i<numPatterns;i++)//(int)(numPatterns*0.8)
	     {
	        num = i;
	        calcNet();
	        System.out.println("pat = " + (num+1) + " Desired O/P :" + trainOutput[num] + " neural model O/P : " + outPred);
	        if(trainOutput[num] == outPred) count++;
	     }
	     System.out.println("Accuracy of Multi Layer Perceptron : "+ (double)count/10);
	    }

	 public static void calcOverallError()
	   	{
	     error = 0.0;
	     for(int i = 0;i<(numPatterns*0.8);i++)
	     {
	        num = i;
	        calcNet();
	        error = error + (errThisPat * errThisPat);
	     }
	     error = error/numPatterns;
	     error = java.lang.Math.sqrt(error);
	    }

	
	 
	 
	 
}
