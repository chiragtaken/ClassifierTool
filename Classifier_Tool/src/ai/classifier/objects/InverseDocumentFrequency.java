package ai.classifier.objects;

import java.util.List;

public class InverseDocumentFrequency {
	private List<String> globalWordList;
	private List<String> globalWordCount;
	
	public List<String> getGlobalWordList() {
		return globalWordList;
	}
	public void setGlobalWordList(List<String> globalWordList) {
		this.globalWordList = globalWordList;
	}
	public List<String> getGlobalWordCount() {
		return globalWordCount;
	}
	public void setGlobalWordCount(List<String> globalWordCount) {
		this.globalWordCount = globalWordCount;
	}
	
	
}
