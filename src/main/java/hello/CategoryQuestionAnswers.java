package hello;

import java.util.List;

public class CategoryQuestionAnswers {
	private String category;
	private List<QuestionAnswer> questionAnswerList;

	public CategoryQuestionAnswers() {

	}
	
	public CategoryQuestionAnswers(String category, List<QuestionAnswer> questionAnswerList){
		this.category = category;
		this.questionAnswerList = questionAnswerList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<QuestionAnswer> getQuestionAnswerList() {
		return questionAnswerList;
	}

	public void setQuestionAnswerList(List<QuestionAnswer> questionAnswerList) {
		this.questionAnswerList = questionAnswerList;
	}

}
