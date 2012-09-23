package ai.classifier.readwrite;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import ai.classifier.refinment.RefineData;

/**
 * @author Chirag Tayal
 * This is a read file class. In this class I'll read
 * document file and stopList file provided by the user
 * 
 * Moreover, I do not want to keep the data in the memory
 * for this I am reading doc file line by line refining it
 * and write back into tempprary file.
 */
public class ReadFile {
	
	private File csvName, stopFileName;
	private static List<String> stopList = new ArrayList<String>();
	private RefineData refineData;

//	private JTextArea area;
		
	public ReadFile(File csvFileName,File stopFileName, JTextArea area) {
		this.csvName = csvFileName;
		this.stopFileName = stopFileName;
//		this.area = area;
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
				Write write = new Write();
				write.writeInTempFile(docData);
				
			}
			fstream.close();dstream.close();reader.close();	
		}catch (Exception e) {
			System.out.println("Error Reading CSV Document File: "+e);
		}
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
		

