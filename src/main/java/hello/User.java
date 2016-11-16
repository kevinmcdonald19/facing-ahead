package hello;

import java.util.List;

import org.springframework.data.annotation.Id;

import quiz_answers.QuizResponse;

public class User {
	@Id
	private String id;
	private String username;
	private String password;
	private String name;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String role;
	private QuizResponse quizResponse;
	
	public User(){
		// blank constructor
	}
	
	public void updateValues(User user){
		this.name = user.getName();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public QuizResponse getQuizResponse() {
		return quizResponse;
	}

	public void setQuizResponse(QuizResponse quizResponse) {
		this.quizResponse = quizResponse;
	}

	public User(User user, QuizResponse quizResponse) {
		this.name = user.getName();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.quizResponse = quizResponse;
		this.quizResponse.setUser(user);
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

}
