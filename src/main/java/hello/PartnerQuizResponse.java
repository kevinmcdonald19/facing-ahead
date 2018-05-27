package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PartnerQuizResponse {

	private List<PartnerQuestionAnswer> partnerQuestionAnswerList;
	
//	public List<PartnerQuestionAnswer> populateQuestions(List<Question> questions, User partnerOne, User partnerTwo) {
//
//		for (Question question : questions) {
//
//			QuestionAnswer partnerOneQuestionAnswer = findAnswerForQuestion(partnerOne, question);
//
//			QuestionAnswer partnerTwoQuestionAnswer = findAnswerForQuestion(partnerTwo, question);
//
//			partnerQuestionAnswerList.add(new PartnerQuestionAnswer(question, partnerOneQuestionAnswer.getAnswer(),
//					partnerTwoQuestionAnswer.getAnswer()));
//		}
//
//		return this.partnerQuestionAnswerList;
//	}
//
//	private QuestionAnswer findAnswerForQuestion(User user, Question question) {
//		List<QuestionAnswer> questionAnswers = user.getQuizResponse().getQuestionAnswers();
//		List<QuestionAnswer> questionAnswersList = new ArrayList<QuestionAnswer>();
//
//		for (QuestionAnswer qa : questionAnswers) {
//			questionAnswersList.add(qa);
//		}
//
//		for (QuestionAnswer questionAnswer : questionAnswersList) {
//			if (questionAnswer.getQuestion().getId().equals(question.getId())) {
//				return questionAnswer;
//			}
//		}
//		return null;
//	}

//	public String getCategory() {
//		return category;
//	}
//
//	public void setCategory(String category) {
//		this.category = category;
//	}
	
	public PartnerQuizResponse() {
		partnerQuestionAnswerList = new ArrayList<PartnerQuestionAnswer>();
	}

	public List<PartnerQuestionAnswer> getPartnerQuestionAnswerList() {
		return partnerQuestionAnswerList;
	}

	public void setPartnerQuestionAnswerList(List<PartnerQuestionAnswer> partnerQuestionAnswerList) {
		this.partnerQuestionAnswerList = partnerQuestionAnswerList;
	}
}
