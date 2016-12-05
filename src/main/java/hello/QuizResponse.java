package hello;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizResponse {

	private Set<QuestionAnswer> questionAnswers = new HashSet<QuestionAnswer>();
	private User user;

	public QuizResponse() {

	}

	public QuizResponse(List<Question> questions) {
		for (Question question : questions) {
			questionAnswers.add(new QuestionAnswer(question, null));
		}
	}

	public void syncUserQuizResponse(List<Question> questions) {
//		questionAnswers.clear();

		// add any questions that haven't been added
		for (Question question : questions) {
			if (!containsQuestion(question)) {
				questionAnswers.add(new QuestionAnswer(question, null));
			}
		}
	}

	private boolean containsQuestion(Question question) {
		for (QuestionAnswer questionAnswer : questionAnswers) {
			if (questionAnswer.getQuestion().getId().equals(question.getId())) {
				return true;
			}
		}
		return false;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}

	public List<QuestionAnswer> getQuestionsByCategory(String category) {
		List<QuestionAnswer> filteredList = new ArrayList<QuestionAnswer>();
		for (QuestionAnswer qa : this.questionAnswers) {
			if (qa.getQuestion().getCategory().equalsIgnoreCase(category)) {
				filteredList.add(qa);
			}
		}
		return filteredList;
	}

	public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

}
