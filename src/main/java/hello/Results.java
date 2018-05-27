package hello;

import java.util.ArrayList;
import java.util.List;

public class Results {

	private PartnerQuizResponse partnerQuizResponse;
	private User partnerOne;
	private User partnerTwo;
	private List<CategoryPartnerQuestionAnswers> categoryPartnerQuestionAnswersList = new ArrayList<CategoryPartnerQuestionAnswers>();

	String[] categoryList = { "families", "roles", "finances", "values", "habits", "work", "leisure", "intimacy",
			"community", "communication", "parenting", "speaking", "life" };

	public Results(User partnerOne, User partnerTwo) {

		this.partnerOne = partnerOne;
		this.partnerTwo = partnerTwo;

		// form category partner question answer list
		for (String category : categoryList) {
			categoryPartnerQuestionAnswersList.add(new CategoryPartnerQuestionAnswers(capitalize(category),
					findPartnerQuestionAnswers(partnerOne, partnerTwo, category)));
		}
	}

	private List<PartnerQuestionAnswer> findPartnerQuestionAnswers(User user1, User user2, String category) {

		List<PartnerQuestionAnswer> partnerQuestionAnswerList = new ArrayList<PartnerQuestionAnswer>();

		List<QuestionAnswer> user1QuestionAnswersList = user1.getQuizResponse().getQuestionAnswersByCategory(category);
		List<QuestionAnswer> user2QuestionAnswersList = user2.getQuizResponse().getQuestionAnswersByCategory(category);

		for (QuestionAnswer user1QuestionAnswer : user1QuestionAnswersList) {
			// find the same question in the other list, and add a new list item to the
			// partnerquestionanswer list with each answer
			QuestionAnswer partnerQuestionAnswer = findPartnerQuestionAnswer(user1QuestionAnswer,
					user2QuestionAnswersList);
			partnerQuestionAnswerList.add(new PartnerQuestionAnswer(partnerQuestionAnswer.getQuestion(),
					user1QuestionAnswer.getAnswer(), partnerQuestionAnswer.getAnswer()));
		}

		return partnerQuestionAnswerList;
	}

	// find the matching question between user1 and user2
	private QuestionAnswer findPartnerQuestionAnswer(QuestionAnswer user1QuestionAnswer,
			List<QuestionAnswer> user2QuestionAnswersList) {
		for (QuestionAnswer user2QuestionAnswer : user2QuestionAnswersList) {
			if (user1QuestionAnswer.getQuestion().getId().equals(user2QuestionAnswer.getQuestion().getId())) {
				return user2QuestionAnswer;
			}
		}
		return null;
	}

	public PartnerQuizResponse getPartnerQuizResponse() {
		return partnerQuizResponse;
	}

	public void setPartnerQuizResponse(PartnerQuizResponse partnerQuizResponse) {
		this.partnerQuizResponse = partnerQuizResponse;
	}

	public User getPartnerOne() {
		return partnerOne;
	}

	public void setPartnerOne(User partnerOne) {
		this.partnerOne = partnerOne;
	}

	public User getPartnerTwo() {
		return partnerTwo;
	}

	public void setPartnerTwo(User partnerTwo) {
		this.partnerTwo = partnerTwo;
	}

	public List<CategoryPartnerQuestionAnswers> getCategoryPartnerQuestionAnswersList() {
		return categoryPartnerQuestionAnswersList;
	}

	public void setCategoryPartnerQuestionAnswersList(
			List<CategoryPartnerQuestionAnswers> categoryPartnerQuestionAnswersList) {
		this.categoryPartnerQuestionAnswersList = categoryPartnerQuestionAnswersList;
	}

	private String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

}
