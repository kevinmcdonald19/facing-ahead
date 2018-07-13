package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
	@Id
	private String id;
	private String username;
	private String password;
	private String name;
	private Long uniqueCode;
	private String partnerUsername;
	private String email;
	private String role;
	private QuizResponse quizResponse;
	private boolean ableToSubmitResults;
	private boolean partnerMutuallyAddedYou;
	private boolean partnerAnsweredAllQuestions;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public User() {
		// blank constructor
	}

	public void updateValues(User user) {
		this.name = user.getName();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public User(CreateUserDTO userDTO, QuizResponse quizResponse) {
		this.name = userDTO.getName();
		this.username = userDTO.getUsername().toLowerCase();
		
		
//		String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());

		this.password = userDTO.getPassword();
		this.quizResponse = quizResponse;
		// this.quizResponse.setUser(userDTO);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(Long uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getPartnerUsername() {
		return partnerUsername;
	}

	public void setPartnerUsername(String partnerUsername) {
		this.partnerUsername = partnerUsername;
	}

	public boolean isAbleToSubmitResults() {
		return ableToSubmitResults;
	}

	public void setAbleToSubmitResults(boolean ableToSubmitResults) {
		this.ableToSubmitResults = ableToSubmitResults;
	}

	public boolean isPartnerMutuallyAddedYou() {
		return partnerMutuallyAddedYou;
	}

	public void setPartnerMutuallyAddedYou(boolean partnerMutuallyAddedYou) {
		this.partnerMutuallyAddedYou = partnerMutuallyAddedYou;
	}
	
	

	public boolean isPartnerAnsweredAllQuestions() {
		return partnerAnsweredAllQuestions;
	}

	public void setPartnerAnsweredAllQuestions(boolean partnerAnsweredAllQuestions) {
		this.partnerAnsweredAllQuestions = partnerAnsweredAllQuestions;
	}

	public boolean partnerMutuallyAddedYou(User partner) {
		if (partner != null && partner.getPartnerUsername() != null) {
			if (partner.getPartnerUsername().equals(this.username)) {
				this.partnerMutuallyAddedYou = true;
				return true;
			} else {
				this.partnerMutuallyAddedYou = false;
				return false;
			}
		} else {
			return false;
		}

	}

	public boolean determineIfAbleToSubmitResults(User partner) {
		if (partner != null) {
			if (partnerMutuallyAddedYou(partner) && partnerAnsweredAllQuestions(partner) && this.quizResponse.allQuestionsAnswered()) {
				this.ableToSubmitResults = true;
				return true;
			} else {
				this.ableToSubmitResults = false;
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean partnerAnsweredAllQuestions(User partner) {
		for (QuestionAnswer qa: partner.getQuizResponse().getQuestionAnswers()){
			if (qa.getAnswer() == null){
				this.partnerAnsweredAllQuestions = false;
				return false;
			}
		}
		this.partnerAnsweredAllQuestions = true;
		return true;
	}

}
