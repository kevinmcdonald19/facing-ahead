package hello;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
	public User createUser(@ModelAttribute User user, Principal principalUser, HttpServletRequest request) {

		QuizResponse newQuizResponse = new QuizResponse(questionRepository.findAll());

		User savedUser = usersRepository.save(new User(user, newQuizResponse));

		// UsernamePasswordAuthenticationToken authRequest = new
		// UsernamePasswordAuthenticationToken(user.getUsername(),
		// user.getPassword());
		// // Authenticate the user
		//
		// List<GrantedAuthority> authorities = new
		// ArrayList<GrantedAuthority>();
		// authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		// UserDetails newUser = new User("kevinmcdonald", "password",
		// authorities);
		// Authentication authentication = new
		// UsernamePasswordAuthenticationToken(newUser.getUsername(),
		// newUser.getPassword(), authorities);
		// SecurityContextHolder.getContext().setAuthentication(authentication);

		return savedUser;
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

	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public User getUser(@PathVariable("username") String username) {
		User p = usersRepository.findByUsername(username);
		return p;
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

		if (partnerUser != null) {
			Results results = new Results(currentUser, partnerUser);
			results.getPartnerQuizResponse().populateQuestions(questionRepository.findAll(), currentUser, partnerUser);
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
		quizResponse.syncUserQuizResponse(questionRepository.findAll());
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

	private QuestionAnswer findQuestionAnswerWithQuestion(Set<QuestionAnswer> questionAnswerList, String questionID) {
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
