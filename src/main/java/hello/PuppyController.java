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
public class PuppyController {

	@Autowired
	private PuppyRepository puppyRepository;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping(value = "/puppies", method = RequestMethod.POST)
	public Puppy createPuppy(@ModelAttribute Puppy puppy) {
		return puppyRepository.save(new Puppy(puppy.getName()));
	}

	@RequestMapping(value = "/puppies/{id}", method = RequestMethod.PUT)
	public Puppy updatePuppy(@PathVariable("id") String id) {
		Puppy p = puppyRepository.findOne(id);
		Puppy updatedPuppy = puppyRepository.save(p);
		return updatedPuppy;
	}

	@RequestMapping(value = "/puppies", method = RequestMethod.GET)
	public List<Puppy> getPuppy() {
		return puppyRepository.findAll();
	}

	@RequestMapping(value = "/puppies/{id}", method = RequestMethod.GET)
	public Puppy getPuppy(@PathVariable("id") String id) {
		Puppy p = puppyRepository.findOne(id);
		return p;
	}

	@RequestMapping(value = "/puppies/{id}", method = RequestMethod.DELETE)
	public Puppy deletePuppy(@PathVariable("id") String id) {
		Puppy p = puppyRepository.save(new Puppy("Nanners"));
		return p;
	}

}
