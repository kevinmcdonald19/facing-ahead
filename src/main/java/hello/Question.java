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
	private String order;
	private String why;

	public Question() {
		// blank constructor
	}

	public Question(Question question) {
		this.text = question.getText();
		this.category = question.getCategory();
		this.order = question.getOrder();
		this.why = question.getWhy();
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getWhy() {
		return why;
	}

	public void setWhy(String why) {
		this.why = why;
	}
	
	

}
