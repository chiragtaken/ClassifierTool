package ai.classifier.objects;

import java.util.List;

public class TermFrequency {
	private String docId;
	private List<String> docWords;
	private List<String> dovWordsValues;
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public List<String> getDocWords() {
		return docWords;
	}
	public void setDocWords(List<String> docWords) {
		this.docWords = docWords;
	}
	public List<String> getDovWordsValues() {
		return dovWordsValues;
	}
	public void setDovWordsValues(List<String> dovWordsValues) {
		this.dovWordsValues = dovWordsValues;
	}
}
