package quiz_answers;

import java.util.ArrayList;
import java.util.List;

import hello.Question;
import hello.User;

public class QuizResponse {	

	private List<QuestionAnswer> questionAnswers = new ArrayList<QuestionAnswer>();
	private User user;
	
	public QuizResponse(){
		
	}

	public QuizResponse(List<Question> questions) {
		for (Question question : questions){
			questionAnswers.add(new QuestionAnswer(question, null));
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}
	
	public List<QuestionAnswer> getQuestionsByCategory(String category){
		List<QuestionAnswer> filteredList = new ArrayList<QuestionAnswer>();
		for (QuestionAnswer qa : this.questionAnswers){
			if (qa.getQuestion().getCategory().equalsIgnoreCase(category)){
				filteredList.add(qa);
			}
		}
		return filteredList;
	}

	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

}
