package ai.classifier.readwrite;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Write {
	
	public void writeInTempFile(String docData){
		try{
			FileWriter fstream = new FileWriter("temp.csv",true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(docData);
			out.newLine();
			out.close();		
		}catch (Exception e) {
			System.out.println("Error writing to the temporary file: "+e);
		}
	}
	
}
