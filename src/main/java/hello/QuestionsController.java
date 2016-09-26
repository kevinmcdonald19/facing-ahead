package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionsController {

	@Autowired
	private QuestionRepository questionsRepository;

	@RequestMapping(value = "/{category}/questions", method = RequestMethod.GET)
	public List<Question> getQuestionsByCategory(@PathVariable("category") String category) {
		return questionsRepository.findByCategoryIgnoreCase(category);
	}

	@RequestMapping(value = "/questions", method = RequestMethod.POST)
	public Question createQuestion(@ModelAttribute Question question) {
		return questionsRepository.save(new Question(question));
	}

	@RequestMapping(value = "/questions/{id}", method = RequestMethod.PUT)
	public Question updateQuestion(@PathVariable("id") String id) {
		Question p = questionsRepository.findOne(id);
		Question updatedQuestion = questionsRepository.save(p);
		return updatedQuestion;
	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public List<Question> getQuestion() {
		return questionsRepository.findAll();
	}

	@RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
	public Question getQuestion(@PathVariable("id") String id) {
		Question p = questionsRepository.findOne(id);
		return p;
	}

	@RequestMapping(value = "/questions/{id}", method = RequestMethod.DELETE)
	public Question deleteQuestion(@PathVariable("id") String id) {
		Question p = questionsRepository.findOne(id);
		questionsRepository.delete(p);
		return p;
	}

}
