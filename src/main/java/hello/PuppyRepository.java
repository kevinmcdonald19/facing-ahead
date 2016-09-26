package hello;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;


public interface PuppyRepository extends MongoRepository<Puppy, String> {

	List<Puppy> findByName(@Param("name") String name);

}