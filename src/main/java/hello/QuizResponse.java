package hello;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuizResponse {

	private List<QuestionAnswer> questionAnswers = new ArrayList<QuestionAnswer>();
	private User user;
	private boolean allQuestionsAnswered;
	private List<QuestionAnswer> unansweredQuestions = new ArrayList<QuestionAnswer>();
	private List<Question> questionsToDelete = new ArrayList<Question>();
	private List<CategoryQuestionAnswers> categoryQuestionAnswerList = new ArrayList<CategoryQuestionAnswers>();

	public List<CategoryQuestionAnswers> getCategoryQuestionAnswerList() {
		return categoryQuestionAnswerList;
	}

	public void setCategoryQuestionAnswerList(List<CategoryQuestionAnswers> categoryQuestionAnswerList) {
		this.categoryQuestionAnswerList = categoryQuestionAnswerList;
	}

	public QuizResponse() {

	}

	public QuizResponse(List<Question> questions) {
		for (Question question : questions) {
			questionAnswers.add(new QuestionAnswer(question, null));
		}
	}

	public void syncUserQuizResponse(List<Question> questionsInRepo) {
		// questionAnswers.clear();

		for (QuestionAnswer userQuestionAnswer : this.questionAnswers) {
			Question usersQuestion = userQuestionAnswer.getQuestion();

			if (usersQuestion != null) {
				Question savedRepoQuestion = questionsRepoContains(usersQuestion, questionsInRepo);

				if (savedRepoQuestion != null) {
					// sync
					usersQuestion.setText(savedRepoQuestion.getText());
					usersQuestion.setCategory(savedRepoQuestion.getCategory());
					usersQuestion.setOrder(savedRepoQuestion.getOrder());
					usersQuestion.setWhy(savedRepoQuestion.getWhy());
				} else {
					// delete the question as it no longer exists anymore
					questionsToDelete.add(usersQuestion);
				}
			}
		}

		// add additional questions if they haven't been added
		for (Question repoQuestion : questionsInRepo) {
			QuestionAnswer questionAnswer = quizResponseContainsQuestion(repoQuestion);
			if (questionAnswer == null) {
				this.questionAnswers.add(new QuestionAnswer(repoQuestion, null));
			}
		}

		// delete any questions that aren't in the questions repo anymore
		for (Question questionToDelete : questionsToDelete) {
			QuestionAnswer questionAnswer = findRelatedQuestionAnswer(questionToDelete);
			this.questionAnswers.remove(questionAnswer);
		}
	}

	public List<Question> getQuestionsToDelete() {
		return questionsToDelete;
	}

	public void setQuestionsToDelete(List<Question> questionsToDelete) {
		this.questionsToDelete = questionsToDelete;
	}

	private Question questionsRepoContains(Question usersQuestion, List<Question> questionsInRepo) {
		for (Question repoQuestion : questionsInRepo) {
			if (repoQuestion.getId().equals(usersQuestion.getId())) {
				return repoQuestion;
			}
		}
		return null;
	}

	private QuestionAnswer findRelatedQuestionAnswer(Question question) {
		for (QuestionAnswer questionAnswer : this.questionAnswers) {
			if (questionAnswer.getQuestion().getId().equals(question.getId())) {
				return questionAnswer;
			}
		}
		return null;
	}

	private QuestionAnswer quizResponseContainsQuestion(Question question) {
		for (QuestionAnswer questionAnswer : questionAnswers) {
			if (questionAnswer.getQuestion().getId().equals(question.getId())) {
				return questionAnswer;
			}
		}
		return null;
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

	public List<QuestionAnswer> getQuestionAnswersByCategory(String category) {
		List<QuestionAnswer> filteredList = new ArrayList<QuestionAnswer>();
		for (QuestionAnswer qa : this.questionAnswers) {
			if (qa.getQuestion().getCategory().equalsIgnoreCase(category)) {
				filteredList.add(qa);
			}
		}
		return filteredList;
	}

	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	public boolean isAllQuestionsAnswered() {
		return allQuestionsAnswered;
	}

	public void setAllQuestionsAnswered(boolean allQuestionsAnswered) {
		this.allQuestionsAnswered = allQuestionsAnswered;
	}

	public List<QuestionAnswer> getUnansweredQuestions() {
		return unansweredQuestions;
	}

	public void setUnansweredQuestions(List<QuestionAnswer> unansweredQuestions) {
		this.unansweredQuestions = unansweredQuestions;
	}

	public boolean allQuestionsAnswered() {
		for (QuestionAnswer qa : this.questionAnswers) {
			if (qa.getAnswer() == null || qa.getAnswer().isEmpty()) {
				this.unansweredQuestions.add(qa);
			}
		}

		if (this.unansweredQuestions.size() > 0) {
			this.allQuestionsAnswered = false;
			return false;
		} else {
			this.allQuestionsAnswered = true;
			return true;
		}
	}

}
