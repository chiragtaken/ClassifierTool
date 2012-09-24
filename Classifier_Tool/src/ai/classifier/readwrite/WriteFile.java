package ai.classifier.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class WriteFile {
	
	public static void writeInTempFile(String docData, File fileName){
		try{
			FileWriter fstream = new FileWriter(fileName,true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(docData);
			out.newLine();
			out.close();		
		}catch (Exception e) {
			System.out.println("Error writing to the temporary file: "+e);
		}
	}
	
}
