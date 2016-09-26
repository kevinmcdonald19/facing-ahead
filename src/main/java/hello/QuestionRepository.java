package hello;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface QuestionRepository extends MongoRepository<Question, String> {

	List<Question> findByCategoryIgnoreCase(String category);
}