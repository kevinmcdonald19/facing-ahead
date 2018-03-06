package hello;

public class QuestionDTO implements java.io.Serializable {
	private String text;
	private String category;
	private String order;
	private String why;

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

	public void setWhy(String why) {
		this.why = why;
	}
	
	public String getWhy(){
		return this.why;
	}

	public QuestionDTO() {

	}

}
