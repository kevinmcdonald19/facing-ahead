package hello;

public class PartnerQuestionAnswer {
	private Question question;
	private String partnerOneAnswer;
	private String partnerTwoAnswer;
	
	public PartnerQuestionAnswer(Question question, String partnerOneAnswer, String partnerTwoAnswer){
		this.question = question;
		this.partnerOneAnswer = partnerOneAnswer;
		this.partnerTwoAnswer = partnerTwoAnswer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getPartnerOneAnswer() {
		return partnerOneAnswer;
	}

	public void setPartnerOneAnswer(String partnerOneAnswer) {
		this.partnerOneAnswer = partnerOneAnswer;
	}

	public String getPartnerTwoAnswer() {
		return partnerTwoAnswer;
	}

	public void setPartnerTwoAnswer(String partnerTwoAnswer) {
		this.partnerTwoAnswer = partnerTwoAnswer;
	}
}
