package ai.classifier.readwrite;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.classifier.objects.InverseDocumentFrequency;
import ai.classifier.objects.TermFrequency;
import ai.classifier.refinment.RefineData;

/**
 * @author Chirag Tayal
 * This is a read file class. In this class I'll read
 * document file and stopList file provided by the user
 * 
 * Moreover, I do not want to keep the data in the memory
 * for this I am reading doc file line by line refining it
 * and write back into temporary file.
 */
/**
 * @author Chirag
 *
 */
public class ReadFile {
	
	private File csvName, stopFileName;
	private static List<String> stopList = new ArrayList<String>();
	private RefineData refineData;

		
	/**
	 * Default Constructor
	 */
	public ReadFile() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param csvFileName
	 * @param stopFileName
	 */
	public ReadFile(File csvFileName,File stopFileName) {
		this.csvName = csvFileName;
		this.stopFileName = stopFileName;
	}
	
	/**
	 * read stop list data into stopList
	 * and set the stopList into RefineData's 
	 * static stopList variable
	 */
	public void readStopFileProcedure() {
		try{
			FileInputStream fstream = new FileInputStream(stopFileName);
			DataInputStream	 dstream = new DataInputStream(fstream);
			BufferedReader	reader = new BufferedReader(new InputStreamReader(dstream));

			String line;
			while((line = reader.readLine()) != null){
				String data = line.toString();
				stopList.add(data);
			}
			fstream.close();dstream.close();reader.close();
		}
		catch(Exception e){
			System.out.println("Error In reading Stop List File: "+e);
		}finally{
			RefineData.setStopList(stopList);
		}
	}

	
	/**
	 * Read the csv file provided by the user and refine the
	 * document text using refine data functions and write the
	 * content to temporary file. which will help in saving memory 
	 * Now initial csv file can be of any length
	 */
	public void readInitialFileProcedure(){
		try{
			FileInputStream fstream = new FileInputStream(csvName);
			DataInputStream	 dstream = new DataInputStream(fstream);
			BufferedReader	reader = new BufferedReader(new InputStreamReader(dstream));
			String line;
			
			while((line=reader.readLine())!=null){
				String[] str = line.split(",");
			//read the line of file line by line	
				String docId = str[0];
				String docLabel = str[1];
				String docText = str[2];
			
			//refine the document text
				refineData = new RefineData(docText);
				docText = refineData.refineDocText();
			
			//write document data to temporary file	
				String docData = docId +","+docLabel+","+docText;
				WriteFile.writeInTempFile(docData, new File("File/temporary.csv"));
				
			}
			fstream.close();dstream.close();reader.close();	
		}catch (Exception e) {
			System.out.println("Error Reading CSV Document File: "+e);
		}
	}
	
	public static InverseDocumentFrequency readIDFFile(File fileName){
		InverseDocumentFrequency inverseDocumentFrequency = new InverseDocumentFrequency();
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream	 dstream = new DataInputStream(fstream);
			BufferedReader	reader = new BufferedReader(new InputStreamReader(dstream));
			String line = reader.readLine();
			String[] lineSplit = line.split(";");
			inverseDocumentFrequency.setGlobalWordList(Arrays.asList(lineSplit[0]));
			inverseDocumentFrequency.setGlobalWordCount(Arrays.asList(lineSplit[1]));
			reader.close();dstream.close();
		} catch (Exception e) {
			System.out.println("Error in reading File: "+fileName+" : \n"+e);
		}
		return inverseDocumentFrequency; 
	}
	
	public static TermFrequency readTFFile(File fileName, int lineIndex){
		TermFrequency termFrequency = new TermFrequency();
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream	 dstream = new DataInputStream(fstream);
			BufferedReader	reader = new BufferedReader(new InputStreamReader(dstream));
			while(lineIndex > 1){
				reader.readLine();
				lineIndex--;
			}
			String[] tfSplit = reader.readLine().split(";");
			termFrequency.setDocId(tfSplit[0]);
			termFrequency.setDocWords(Arrays.asList(tfSplit[1]));
			termFrequency.setDovWordsValues(Arrays.asList(tfSplit[2]));
			
			reader.close();dstream.close();
		} catch (Exception e) {
			System.out.println("Error in reading File: "+fileName+" : \n"+e);
		}
		return termFrequency; 
	}
}


/*	
	public void read(){
		data = new HashMap<String, HashMap<String,String>>();		
		try{
			FileInputStream fstream = new FileInputStream(csvName);
			DataInputStream	 dstream = new DataInputStream(fstream);
			BufferedReader	reader = new BufferedReader(new InputStreamReader(dstream));
			String line;
			
			while((line = reader.readLine()) != null){
				String[] str = line.split(",");
				String docId = str[0];
				String docLabel = str[1];
				String docText = str[2];
				data.put(docId, new HashMap<String,String>());
				data.get(docId).put(docLabel, docText);
			}
			fstream.close();dstream.close();reader.close();
//			area.append("Total documents in the corpus : "+data.size()+"\n");
		}
		catch(Exception e){
//			area.append("Error in reading .CSV File \n");
			System.out.println("Error In read Document File : "+e);
		}		
	}
*/	
		

