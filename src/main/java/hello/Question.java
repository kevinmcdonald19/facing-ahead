package hello;

import org.springframework.data.annotation.Id;

public class Question {
	private final String yes = "YES";
	private final String no = "NO";
	private final String maybe = "MAYBE";

	@Id
	private String id;
	private String text;
	private String category;

	public Question() {
		// blank constructor
	}
	
	public Question(Question question){
		this.text = question.text;
		this.category = question.category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}



	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



}
