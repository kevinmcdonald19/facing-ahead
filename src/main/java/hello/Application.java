package hello;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		SecUserDetailsService userDetailsService;

		
//		@Bean
//		public PasswordEncoder passwordEncoder() {
//			return new BCryptPasswordEncoder();
//		}
//		
//		// Enable jdbc authentication
//		@Autowired
//		public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//			
//		}

		@Autowired
		public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
			builder.userDetailsService(userDetailsService);
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic().and().authorizeRequests()
					.antMatchers("/index.html", "/home.html", "/login.html", "/static/**", "/", "/lib/**", "/css/**",
							"/images/**", "/partials/**", "/create/user")
					.permitAll().anyRequest().authenticated().and().csrf().disable();
		}

		// @Autowired
		// public void configureGlobal(AuthenticationManagerBuilder auth) throws
		// Exception {
		// auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		// auth.inMemoryAuthentication().withUser("nalah").password("nalah").roles("USER");
		// }
	}
}
