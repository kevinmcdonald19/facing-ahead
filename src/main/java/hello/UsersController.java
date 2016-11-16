package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import quiz_answers.QuestionAnswer;
import quiz_answers.QuizResponse;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User createUser(@ModelAttribute User user) {
		QuizResponse newQuizResponse = new QuizResponse(questionRepository.findAll());
		User savedUser = usersRepository.save(new User(user, newQuizResponse));
		return savedUser;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable("id") String id, @ModelAttribute User user) {
		User savedUser = usersRepository.findOne(id);
		savedUser.updateValues(user);
		User updatedUser = usersRepository.save(savedUser);
		return updatedUser;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return usersRepository.findAll();
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable("id") String id) {
		User p = usersRepository.findOne(id);
		return p;
	}

	@RequestMapping(value = "/users/{username}/quizResponse", method = RequestMethod.GET)
	public QuizResponse getQuizResponse(@PathVariable("username") String username) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse quizResponse = savedUser.getQuizResponse();
		return quizResponse;
	}

	@RequestMapping(value = "/users/{username}/quizResponse/questionAnswers/{category}", method = RequestMethod.GET)
	public List<QuestionAnswer> getQuizResponseByCategory(@PathVariable("username") String username,
			@PathVariable("category") String category) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse quizResponse = savedUser.getQuizResponse();
		return quizResponse.getQuestionsByCategory(category);
	}

	@RequestMapping(value = "/users/{username}/quizResponse/questionAnswers/{category}", method = RequestMethod.POST)
	@ResponseBody
	public List<QuestionAnswer> updateQuizResponseByCategory(@PathVariable("username") String username,
			@PathVariable("category") String category,
			@RequestBody UpdateQuizResponseDTO updateQuizResponseDTO) {
		User savedUser = usersRepository.findByUsername(username);
		QuizResponse savedQuizResponse = savedUser.getQuizResponse();
		
		for (UpdateQuestionAnswerDTO updateQuestionAnswerDTO : updateQuizResponseDTO.getUpdateQuestionAnswerDTOList()){
			QuestionAnswer savedQuestionAnswer = findQuestionAnswerWithQuestion(savedQuizResponse.getQuestionAnswers(), updateQuestionAnswerDTO.getQuestionID());
			savedQuestionAnswer.setAnswer(updateQuestionAnswerDTO.getAnswer());
			usersRepository.save(savedUser);
		}
		
		return savedUser.getQuizResponse().getQuestionsByCategory(category);
	}

	private QuestionAnswer findQuestionAnswerWithQuestion(List<QuestionAnswer> questionAnswerList, String questionID) {
		for (QuestionAnswer questionAnswer : questionAnswerList) {
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
