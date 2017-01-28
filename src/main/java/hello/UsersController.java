package hello;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

	@RequestMapping(value = "/create/user", method = RequestMethod.POST)
	public User createUser(@RequestBody CreateUserDTO createUserDTO, Principal principalUser,
			HttpServletRequest request) {

		// check to see if the user exists
		User existingUser = usersRepository.findByUsername(createUserDTO.getUsername());
		if (existingUser == null) {
			QuizResponse newQuizResponse = new QuizResponse(questionsRepository.findAll());
			User savedUser = usersRepository.save(new User(createUserDTO, newQuizResponse));
			return savedUser;
		} else {
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

	@RequestMapping(value = "/users/{currentUsername}/{partnerUsername}", method = RequestMethod.PUT)
	public User pairPartner(@PathVariable("currentUsername") String currentUsername,
			@PathVariable("partnerUsername") String partnerUsername) {
		User savedUser = usersRepository.findByUsername(currentUsername);

		User savedPartner = usersRepository.findByUsername(partnerUsername);
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

	// @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	// public User getUser(@PathVariable("id") String id) {
	// User p = usersRepository.findOne(id);
	// return p;
	// }

	public void determineIfAbleToCompareResults(User user) {
		user.getQuizResponse().allQuestionsAnswered();
		user.determineIfAbleToSubmitResults(usersRepository.findByUsername(user.getPartnerUsername()));
	}

	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public User getUser(@PathVariable("username") String username) {
		User user = usersRepository.findByUsername(username);
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

	@RequestMapping(value = "/users/{username}/quizResponse", method = RequestMethod.GET)
	public QuizResponse getQuizResponse(@PathVariable("username") String username) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse quizResponse = savedUser.getQuizResponse();
		return quizResponse;
	}

	@RequestMapping(value = "/users/{username}/results", method = RequestMethod.GET)
	public Results getResults(@PathVariable("username") String username) {
		User currentUser = usersRepository.findByUsername(username);
		User partnerUser = usersRepository.findByUsername(currentUser.getPartnerUsername());

		List<Question> allRepoQuestions = questionsRepository.findAll();
		currentUser.getQuizResponse().syncUserQuizResponse(allRepoQuestions);
		partnerUser.getQuizResponse().syncUserQuizResponse(allRepoQuestions);
		determineIfAbleToCompareResults(currentUser);

		if (currentUser.isAbleToSubmitResults()) {
			Results results = new Results(currentUser, partnerUser);
			results.getPartnerQuizResponse().populateQuestions(questionsRepository.findAll(), currentUser, partnerUser);
			return results;
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/users/{username}/quizResponse/questionAnswers/{category}", method = RequestMethod.GET)
	public List<QuestionAnswer> getQuizResponseByCategory(@PathVariable("username") String username,
			@PathVariable("category") String category) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse quizResponse = savedUser.getQuizResponse();
		List<Question> allRepoQuestions = questionsRepository.findAll();
		quizResponse.syncUserQuizResponse(allRepoQuestions);
		quizResponse.getQuestionAnswers().removeAll(quizResponse.getQuestionsToDelete());
		usersRepository.save(savedUser);
		return savedUser.getQuizResponse().getQuestionsByCategory(category);
	}

	@RequestMapping(value = "/users/{username}/quizResponse/questionAnswers/{category}", method = RequestMethod.POST)
	@ResponseBody
	public List<QuestionAnswer> updateQuizResponseByCategory(@PathVariable("username") String username,
			@PathVariable("category") String category, @RequestBody UpdateQuizResponseDTO updateQuizResponseDTO) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse savedQuizResponse = savedUser.getQuizResponse();

		for (UpdateQuestionAnswerDTO updateQuestionAnswerDTO : updateQuizResponseDTO.getUpdateQuestionAnswerDTOList()) {
			QuestionAnswer savedQuestionAnswer = findQuestionAnswerWithQuestion(savedQuizResponse.getQuestionAnswers(),
					updateQuestionAnswerDTO.getQuestionID());
			savedQuestionAnswer.setAnswer(updateQuestionAnswerDTO.getAnswer());
			usersRepository.save(savedUser);
		}
		return savedUser.getQuizResponse().getQuestionsByCategory(category);
	}

	private QuestionAnswer findQuestionAnswerWithQuestion(List<QuestionAnswer> list, String questionID) {
		for (QuestionAnswer questionAnswer : list) {
			if (questionAnswer.getQuestion().getId().equals(questionID)) {
				return questionAnswer;
			}
		}
		return null;
	}

	@RequestMapping(value = "/users/{username}/quizResponse", method = RequestMethod.PUT)
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

}
