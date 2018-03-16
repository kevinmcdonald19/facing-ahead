package hello;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping(value = "/categoriesAndQuestions", method = RequestMethod.GET)
	public List<CategoryQuestions> getCategoriesAndQuestions() {
		String[] categoryList = { "families", "roles", "finances", "values", "habits", "work", "leisure", "intimacy",
				"community", "communication", "parenting", "speaking", "life" };

		ArrayList<CategoryQuestions> categoryQuestionsList = new ArrayList<CategoryQuestions>();
		for (String category : categoryList) {
			CategoryQuestions cq = new CategoryQuestions(category,
					questionsRepository.findByCategoryIgnoreCase(category));
			categoryQuestionsList.add(cq);
		}

		return categoryQuestionsList;
	}

	@RequestMapping(value = "/questions", method = RequestMethod.POST)
	public Question createQuestion(@ModelAttribute Question question) {
		return questionsRepository.save(new Question(question));
	}

	/* Update a question */
	@RequestMapping(value = "/questions/{id}", method = RequestMethod.POST)
	public Question updateQuestion(@PathVariable("id") String id, @RequestBody QuestionDTO questionDTO) {
		Question p = questionsRepository.findOne(id);

		if (questionDTO.getCategory() != null) {
			p.setCategory(questionDTO.getCategory());
		}

		if (questionDTO.getText() != null) {
			p.setText(questionDTO.getText());
		}
		
		if (questionDTO.getWhy() != null) {
			p.setWhy(questionDTO.getWhy());
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
