package hello;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Category {
	@Id
	private String id;
	
	private String name;
	private List<Question> questions;
	
	public Category(){
		// blank constructor
	}

	public Category(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
