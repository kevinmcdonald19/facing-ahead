package hello;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private QuestionRepository questionsRepository;

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@RequestMapping(value = "/create/user", method = RequestMethod.POST)
	public User createUser(@RequestBody CreateUserDTO createUserDTO, Principal principalUser,
			HttpServletRequest request) {

		// check to see if the user exists
		User existingUser = usersRepository.findByUsername(createUserDTO.getUsername().toLowerCase());
		if (existingUser == null) {
			if (createUserDTO.getUsername().contains("@")) {
				QuizResponse newQuizResponse = new QuizResponse(questionsRepository.findAll());

				// save the user with a lowercase username to make everyone the same
				User savedUser = usersRepository.save(new User(createUserDTO, newQuizResponse));

				return savedUser;
			} else {
				// user didn't provide an email
				return null;
			}
			
		} else {
			// the user already exists - can't create this user
			return null;
		}
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable("id") String id, @ModelAttribute User user) {
		User savedUser = usersRepository.findOne(id);
		savedUser.updateValues(user);
		User updatedUser = usersRepository.save(savedUser);
		return updatedUser;
	}

	@RequestMapping(value = "/users/{currentUsername:.+}/{partnerUsername:.+}", method = RequestMethod.PUT)
	public User pairPartner(@PathVariable("currentUsername") String currentUsername,
			@PathVariable("partnerUsername") String partnerUsername) {
		User savedUser = usersRepository.findByUsername(currentUsername.toLowerCase());

		User savedPartner = usersRepository.findByUsername(partnerUsername.toLowerCase());
		if (savedPartner != null) {
			savedUser.setPartnerUsername(partnerUsername);
			User updatedUser = usersRepository.save(savedUser);
			return updatedUser;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return usersRepository.findAll();
	}

	@RequestMapping(value = "/simpleUsers", method = RequestMethod.GET)
	public List<SimpleUser> getSimpleUsers() {
		List<User> users = usersRepository.findAll();
		List<SimpleUser> simpleUsers = new ArrayList<SimpleUser>();
		for (User user : users) {
			simpleUsers.add(new SimpleUser(user));
		}
		return simpleUsers;
	}

	// @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	// public User getUser(@PathVariable("id") String id) {
	// User p = usersRepository.findOne(id);
	// return p;
	// }

	public void determineIfAbleToCompareResults(User user) {
		user.getQuizResponse().allQuestionsAnswered();
		user.determineIfAbleToSubmitResults(usersRepository.findByUsername(user.getPartnerUsername()));
	}

	@RequestMapping(value = "/users/{username:.+}", method = RequestMethod.GET)
	public User getUser(@PathVariable("username") String username) {
		User user = usersRepository.findByUsername(username.toLowerCase());
		determineIfAbleToCompareResults(user);
		return user;
	}

	@RequestMapping(value = "/users/{id}/clearQuestionAnswers", method = RequestMethod.GET)
	public User clearQuizResponse(@PathVariable("id") String id) {
		User p = usersRepository.findOne(id);
		p.getQuizResponse().getQuestionAnswers().clear();
		usersRepository.save(p);
		return p;
	}

	@RequestMapping(value = "/users/{username:.+}/quizResponse", method = RequestMethod.GET)
	public QuizResponse getQuizResponse(@PathVariable("username") String username) {
		User savedUser = usersRepository.findByUsername(username);

		String[] categoryList = { "families", "roles", "finances", "values", "habits", "work", "leisure", "intimacy",
				"community", "communication", "parenting", "speaking", "life" };

		QuizResponse quizResponse = savedUser.getQuizResponse();
		List<CategoryQuestionAnswers> categoryQuestionAnswersList = quizResponse.getCategoryQuestionAnswerList();

		ArrayList<CategoryQuestions> categoryQuestionsList = new ArrayList<CategoryQuestions>();
		for (String category : categoryList) {
			categoryQuestionAnswersList.add(new CategoryQuestionAnswers(capitalize(category),
					quizResponse.getQuestionAnswersByCategory(category)));
		}

		return quizResponse;
	}

	private String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	@RequestMapping(value = "/users/{username:.+}/results", method = RequestMethod.GET)
	public Results getResults(@PathVariable("username") String username) {
		User currentUser = usersRepository.findByUsername(username);
		User partnerUser = usersRepository.findByUsername(currentUser.getPartnerUsername());

		List<Question> allRepoQuestions = questionsRepository.findAll();
		currentUser.getQuizResponse().syncUserQuizResponse(allRepoQuestions);
		partnerUser.getQuizResponse().syncUserQuizResponse(allRepoQuestions);
		determineIfAbleToCompareResults(currentUser);

		if (currentUser.isAbleToSubmitResults()) {
			Results results = new Results(currentUser, partnerUser);
			return results;
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/users/{username:.+}/quizResponse/questionAnswers/{category}", method = RequestMethod.GET)
	public List<QuestionAnswer> getQuizResponseByCategory(@PathVariable("username") String username,
			@PathVariable("category") String category) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse quizResponse = savedUser.getQuizResponse();
		List<Question> allRepoQuestions = questionsRepository.findAll();
		quizResponse.syncUserQuizResponse(allRepoQuestions);
		quizResponse.getQuestionAnswers().removeAll(quizResponse.getQuestionsToDelete());
		usersRepository.save(savedUser);
		return savedUser.getQuizResponse().getQuestionAnswersByCategory(category);
	}

	@RequestMapping(value = "/users/{username:.+}/quizResponse/questionAnswers/{category}", method = RequestMethod.POST)
	@ResponseBody
	public List<QuestionAnswer> updateQuizResponseByCategory(Principal principal,
			@PathVariable("username") String username, @PathVariable("category") String category,
			@RequestBody UpdateQuizResponseDTO updateQuizResponseDTO) {

		// User savedUser = usersRepository.findByUsername(username);

		// reference: http://www.baeldung.com/get-user-in-spring-security
		User savedUser = usersRepository.findByUsername(principal.getName());

		QuizResponse savedQuizResponse = savedUser.getQuizResponse();

		for (UpdateQuestionAnswerDTO updateQuestionAnswerDTO : updateQuizResponseDTO.getUpdateQuestionAnswerDTOList()) {
			QuestionAnswer savedQuestionAnswer = findQuestionAnswerWithQuestion(savedQuizResponse.getQuestionAnswers(),
					updateQuestionAnswerDTO.getQuestionID());
			savedQuestionAnswer.setAnswer(updateQuestionAnswerDTO.getAnswer());
			usersRepository.save(savedUser);
		}
		return savedUser.getQuizResponse().getQuestionAnswersByCategory(category);
	}

	@RequestMapping(value = "/users/{username:.+}/updateAllAnswersYes", method = RequestMethod.POST)
	@ResponseBody
	public User updateAllAnswersYes(Principal principal, @PathVariable("username") String username) {

		// User savedUser = usersRepository.findByUsername(username);

		// reference: http://www.baeldung.com/get-user-in-spring-security
		User savedUser = usersRepository.findByUsername(username);

		for (QuestionAnswer qa : savedUser.getQuizResponse().getQuestionAnswers()) {
			qa.setAnswer("yes");
		}

		usersRepository.save(savedUser);

		return usersRepository.findByUsername(username);
	}

	private QuestionAnswer findQuestionAnswerWithQuestion(List<QuestionAnswer> list, String questionID) {
		for (QuestionAnswer questionAnswer : list) {
			if (questionAnswer.getQuestion().getId().equals(questionID)) {
				return questionAnswer;
			}
		}
		return null;
	}

	@RequestMapping(value = "/users/{username:.+}/quizResponse", method = RequestMethod.PUT)
	public QuizResponse updateQuizResponse(@PathVariable("username") String username,
			@ModelAttribute QuizResponse quizResponse) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse savedQuizResponse = savedUser.getQuizResponse();
		return savedQuizResponse;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public User deleteUser(@PathVariable("id") String id) {
		User p = usersRepository.findOne(id);
		usersRepository.delete(p);
		return p;
	}

	// One-time use method to encryp all users' passwords in the system
	@RequestMapping(value = "/resetPasswords", method = RequestMethod.GET)
	public String encryptAllUsers() {
		List<User> users = usersRepository.findAll();
		int countUpdated = 0;
		for (User user : users) {
			String plainPassword = user.getPassword();
			if (plainPassword != null) {
				user.setPassword(user.getUsername());
				usersRepository.save(user);
				countUpdated++;
			}
		}

		return "success: " + countUpdated;
	}

	// One-time use
	@RequestMapping(value = "/removeUserFromQuizResponse", method = RequestMethod.GET)
	public String removeUserFromQuizResponse() {
		List<User> users = usersRepository.findAll();
		int countUpdated = 0;
		for (User user : users) {
			QuizResponse quizResponse = user.getQuizResponse();
			if (quizResponse != null) {
				quizResponse.setUser(null);
				usersRepository.save(user);
				countUpdated++;
			}
		}

		return "success: " + countUpdated;
	}

}
