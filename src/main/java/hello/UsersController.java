package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepository;

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User createUser(@ModelAttribute User User) {
		return usersRepository.save(new User(User.getName()));
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable("id") String id) {
		User p = usersRepository.findOne(id);
		User updatedUser = usersRepository.save(p);
		return updatedUser;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getUser() {
		return usersRepository.findAll();
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable("id") String id) {
		User p = usersRepository.findOne(id);
		return p;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public User deleteUser(@PathVariable("id") String id) {
		User p = usersRepository.findOne(id);
		usersRepository.delete(p);
		return p;
	}

}
