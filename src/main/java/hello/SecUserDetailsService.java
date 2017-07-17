package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsersRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*
		 * Here add user data layer fetching from the MongoDB. I have used
		 * userRepository
		 */
		User user = userRepository.findByUsername(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			UserDetails details = new SecUserDetails(user);
			return details;
		}
	}
}
