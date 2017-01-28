package hello;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionsController {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@Autowired
	private QuestionRepository questionsRepository;

	@RequestMapping(value = "/questions/{category}", method = RequestMethod.GET)
	public List<Question> getQuestionsByCategory(@PathVariable("category") String category) {
		return questionsRepository.findByCategoryIgnoreCase(category);
	}

	@RequestMapping(value = "/questions", method = RequestMethod.POST)
	public Question createQuestion(@ModelAttribute Question question) {
		return questionsRepository.save(new Question(question));
	}

	@RequestMapping(value = "/questions/{id}", method = RequestMethod.POST)
	public Question updateQuestion(@PathVariable("id") String id, @ModelAttribute QuestionDTO questionDTO) {
		Question p = questionsRepository.findOne(id);

		if (questionDTO.getCategory() != null) {
			p.setCategory(questionDTO.getCategory());
		}

		if (questionDTO.getText() != null) {
			p.setText(questionDTO.getText());
		}

		if (questionDTO.getOrder() != null) {
			p.setOrder(questionDTO.getOrder());
		}

		Question updatedQuestion = questionsRepository.save(p);
		return updatedQuestion;
	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public List<Question> getQuestions(Principal user) {
		return questionsRepository.findAll();
	}

	// @RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
	// public Question getQuestion(@PathVariable("id") int id) {
	// Question p = questionsRepository.findOne(id);
	// return p;
	// }

	@RequestMapping(value = "/questions/{id}", method = RequestMethod.DELETE)
	public Question deleteQuestion(@PathVariable("id") String id) {
		Question p = questionsRepository.findOne(id);
		questionsRepository.delete(p);
		return p;
	}

}
