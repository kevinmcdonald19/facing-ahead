package hello;

import org.springframework.data.annotation.Id;

public class QuestionAnswer {
	
	@Id
	private String id;
	private Question question;
	private String answer;
	private String partnerAnswer;
	
	public QuestionAnswer(){
		// blank constructor
	}
	
	public QuestionAnswer(Question question, String answer){
		this.question = question;
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
