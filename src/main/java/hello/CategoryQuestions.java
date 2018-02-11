package hello;

import java.util.ArrayList;
import java.util.List;

public class CategoryQuestions {
	private String category;
	private List<Question> questionsList;

	public CategoryQuestions() {

	}
	
	public CategoryQuestions(String category, List<Question> questionList){
		this.category = category;
		this.questionsList = questionList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Question> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(ArrayList<Question> questionsList) {
		this.questionsList = questionsList;
	}
	
	
}
