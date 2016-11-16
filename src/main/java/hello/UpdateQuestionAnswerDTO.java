package hello;

public class UpdateQuestionAnswerDTO {

	private String questionID;
	private String answer;

	public UpdateQuestionAnswerDTO() {
		// blank constructor
	}

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
