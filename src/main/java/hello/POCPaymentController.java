package hello;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;


@RestController
public class POCPaymentController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private QuestionRepository questionsRepository;

	private static BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX, "5qskfs7yf7ph5459",
			"55yf5s784jyddqb9", "4e7ec3e5adbc6d76bd89ce8f87a6a80c");

	@RequestMapping(value = "/pocpayment", method = RequestMethod.GET)
	public Object getClientToken() {
		return gateway.clientToken().generate();
	}

	// @RequestMapping(value="/checkout", method = RequestMethod.POST)
	// public Object handle(@RequestBody CheckoutDTO checkoutDTO) {
	// String nonceFromTheClient = checkoutDTO.getData();
	// // Use payment method nonce here
	//
	// TransactionRequest request = new TransactionRequest().amount(new
	// BigDecimal("10.00"))
	// .paymentMethodNonce(nonceFromTheClient).options().submitForSettlement(true).done();
	//
	// Result<Transaction> result = gateway.transaction().sale(request);
	//
	// return null;
	// }

	// private BraintreeGateway gateway = Application.gateway;

//	private Status[] TRANSACTION_SUCCESS_STATUSES = new Status[] { Transaction.Status.AUTHORIZED,
//			Transaction.Status.AUTHORIZING, Transaction.Status.SETTLED, Transaction.Status.SETTLEMENT_CONFIRMED,
//			Transaction.Status.SETTLEMENT_PENDING, Transaction.Status.SETTLING,
//			Transaction.Status.SUBMITTED_FOR_SETTLEMENT };

//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String root(Model model) {
//		return "redirect:checkouts";
//	}

	@RequestMapping(value = "/checkouts", method = RequestMethod.GET)
	public String checkout(Model model) {
		String clientToken = gateway.clientToken().generate();
		model.addAttribute("clientToken", clientToken);

		return "checkouts/new";
	}

	@RequestMapping(value = "/checkouts", method = RequestMethod.POST)
	public String postForm(@RequestParam("amount") String amount, @RequestParam("payment_method_nonce") String nonce,
			Model model, final RedirectAttributes redirectAttributes) {
		BigDecimal decimalAmount;
		try {
			decimalAmount = new BigDecimal(amount);
		} catch (NumberFormatException e) {
			redirectAttributes.addFlashAttribute("errorDetails", "Error: 81503: Amount is an invalid format.");
			return "redirect:checkouts";
		}

		TransactionRequest request = new TransactionRequest().amount(decimalAmount).paymentMethodNonce(nonce).options()
				.submitForSettlement(true).done();

		Result<Transaction> result = gateway.transaction().sale(request);

		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			return "redirect:checkouts/" + transaction.getId();
		} else if (result.getTransaction() != null) {
			Transaction transaction = result.getTransaction();
			return "redirect:checkouts/" + transaction.getId();
		} else {
			String errorString = "";
			for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
				errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
			}
			redirectAttributes.addFlashAttribute("errorDetails", errorString);
			return "redirect:checkouts";
		}
	}

	@RequestMapping(value = "/checkouts/{transactionId}")
	public String getTransaction(@PathVariable String transactionId, Model model) {
		Transaction transaction;
		CreditCard creditCard;
		Customer customer;

		try {
			transaction = gateway.transaction().find(transactionId);
			creditCard = transaction.getCreditCard();
			customer = transaction.getCustomer();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return "redirect:/checkouts";
		}

//		model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
		model.addAttribute("transaction", transaction);
		model.addAttribute("creditCard", creditCard);
		model.addAttribute("customer", customer);

		return "checkouts/show";
	}

	// @RequestMapping(value = "/checkouts", method = RequestMethod.POST)
	// public String postForm(@RequestParam("amount") String amount,
	// @RequestParam("payment_method_nonce") String nonce,
	// Model model, final RedirectAttributes redirectAttributes) {
	// BigDecimal decimalAmount;
	// try {
	// decimalAmount = new BigDecimal(amount);
	// } catch (NumberFormatException e) {
	// redirectAttributes.addFlashAttribute("errorDetails", "Error: 81503: Amount is
	// an invalid format.");
	// return "redirect:checkouts";
	// }
	//
	// TransactionRequest request = new
	// TransactionRequest().amount(decimalAmount).paymentMethodNonce(nonce).options()
	// .submitForSettlement(true).done();
	//
	// Result<Transaction> result = gateway.transaction().sale(request);
	//
	// if (result.isSuccess()) {
	// Transaction transaction = result.getTarget();
	// return "redirect:checkouts/" + transaction.getId();
	// } else if (result.getTransaction() != null) {
	// Transaction transaction = result.getTransaction();
	// return "redirect:checkouts/" + transaction.getId();
	// } else {
	// String errorString = "";
	// for (ValidationError error : result.getErrors().getAllDeepValidationErrors())
	// {
	// errorString += "Error: " + error.getCode() + ": " + error.getMessage() +
	// "\n";
	// }
	// redirectAttributes.addFlashAttribute("errorDetails", errorString);
	// return "redirect:checkouts";
	// }
	// }

	// @RequestMapping(value = "/create/user", method = RequestMethod.POST)
	// public User createUser(@RequestBody CreateUserDTO createUserDTO, Principal
	// principalUser,
	// HttpServletRequest request) {
	//
	// // check to see if the user exists
	// User existingUser =
	// usersRepository.findByUsername(createUserDTO.getUsername().toLowerCase());
	// if (existingUser == null) {
	// QuizResponse newQuizResponse = new
	// QuizResponse(questionsRepository.findAll());
	//
	// // save the user with a lowercase username to make everyone the same
	// User savedUser = usersRepository.save(new User(createUserDTO,
	// newQuizResponse));
	//
	// return savedUser;
	// } else {
	// // the user already exists - can't create this user
	// return null;
	// }
	// }
	//
	//
	// @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	// public User updateUser(@PathVariable("id") String id, @ModelAttribute User
	// user) {
	// User savedUser = usersRepository.findOne(id);
	// savedUser.updateValues(user);
	// User updatedUser = usersRepository.save(savedUser);
	// return updatedUser;
	// }
	//
	// @RequestMapping(value = "/users/{currentUsername}/{partnerUsername}", method
	// = RequestMethod.PUT)
	// public User pairPartner(@PathVariable("currentUsername") String
	// currentUsername,
	// @PathVariable("partnerUsername") String partnerUsername) {
	// User savedUser = usersRepository.findByUsername(currentUsername);
	//
	// User savedPartner = usersRepository.findByUsername(partnerUsername);
	// if (savedPartner != null) {
	// savedUser.setPartnerUsername(partnerUsername);
	// User updatedUser = usersRepository.save(savedUser);
	// return updatedUser;
	// } else {
	// return null;
	// }
	// }
	//
	// @RequestMapping(value = "/users", method = RequestMethod.GET)
	// public List<User> getUsers() {
	// return usersRepository.findAll();
	// }
	//
	// // @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	// // public User getUser(@PathVariable("id") String id) {
	// // User p = usersRepository.findOne(id);
	// // return p;
	// // }
	//
	// public void determineIfAbleToCompareResults(User user) {
	// user.getQuizResponse().allQuestionsAnswered();
	// user.determineIfAbleToSubmitResults(usersRepository.findByUsername(user.getPartnerUsername()));
	// }
	//
	// @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	// public User getUser(@PathVariable("username") String username) {
	// User user = usersRepository.findByUsername(username.toLowerCase());
	// determineIfAbleToCompareResults(user);
	// return user;
	// }
	//
	// @RequestMapping(value = "/users/{id}/clearQuestionAnswers", method =
	// RequestMethod.GET)
	// public User clearQuizResponse(@PathVariable("id") String id) {
	// User p = usersRepository.findOne(id);
	// p.getQuizResponse().getQuestionAnswers().clear();
	// usersRepository.save(p);
	// return p;
	// }
	//
	// @RequestMapping(value = "/users/{username}/quizResponse", method =
	// RequestMethod.GET)
	// public QuizResponse getQuizResponse(@PathVariable("username") String
	// username) {
	// User savedUser = usersRepository.findByUsername(username);
	// QuizResponse quizResponse = savedUser.getQuizResponse();
	// return quizResponse;
	// }
	//
	// @RequestMapping(value = "/users/{username}/results", method =
	// RequestMethod.GET)
	// public Results getResults(@PathVariable("username") String username) {
	// User currentUser = usersRepository.findByUsername(username);
	// User partnerUser =
	// usersRepository.findByUsername(currentUser.getPartnerUsername());
	//
	// List<Question> allRepoQuestions = questionsRepository.findAll();
	// currentUser.getQuizResponse().syncUserQuizResponse(allRepoQuestions);
	// partnerUser.getQuizResponse().syncUserQuizResponse(allRepoQuestions);
	// determineIfAbleToCompareResults(currentUser);
	//
	// if (currentUser.isAbleToSubmitResults()) {
	// Results results = new Results(currentUser, partnerUser);
	// results.getPartnerQuizResponse().populateQuestions(questionsRepository.findAll(),
	// currentUser, partnerUser);
	// return results;
	// } else {
	// return null;
	// }
	//
	// }
	//
	// @RequestMapping(value =
	// "/users/{username}/quizResponse/questionAnswers/{category}", method =
	// RequestMethod.GET)
	// public List<QuestionAnswer>
	// getQuizResponseByCategory(@PathVariable("username") String username,
	// @PathVariable("category") String category) {
	// User savedUser = usersRepository.findByUsername(username);
	// QuizResponse quizResponse = savedUser.getQuizResponse();
	// List<Question> allRepoQuestions = questionsRepository.findAll();
	// quizResponse.syncUserQuizResponse(allRepoQuestions);
	// quizResponse.getQuestionAnswers().removeAll(quizResponse.getQuestionsToDelete());
	// usersRepository.save(savedUser);
	// return savedUser.getQuizResponse().getQuestionsByCategory(category);
	// }
	//
	// @RequestMapping(value =
	// "/users/{username}/quizResponse/questionAnswers/{category}", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public List<QuestionAnswer>
	// updateQuizResponseByCategory(@PathVariable("username") String username,
	// @PathVariable("category") String category, @RequestBody UpdateQuizResponseDTO
	// updateQuizResponseDTO) {
	// User savedUser = usersRepository.findByUsername(username);
	// QuizResponse savedQuizResponse = savedUser.getQuizResponse();
	//
	// for (UpdateQuestionAnswerDTO updateQuestionAnswerDTO :
	// updateQuizResponseDTO.getUpdateQuestionAnswerDTOList()) {
	// QuestionAnswer savedQuestionAnswer =
	// findQuestionAnswerWithQuestion(savedQuizResponse.getQuestionAnswers(),
	// updateQuestionAnswerDTO.getQuestionID());
	// savedQuestionAnswer.setAnswer(updateQuestionAnswerDTO.getAnswer());
	// usersRepository.save(savedUser);
	// }
	// return savedUser.getQuizResponse().getQuestionsByCategory(category);
	// }
	//
	// private QuestionAnswer findQuestionAnswerWithQuestion(List<QuestionAnswer>
	// list, String questionID) {
	// for (QuestionAnswer questionAnswer : list) {
	// if (questionAnswer.getQuestion().getId().equals(questionID)) {
	// return questionAnswer;
	// }
	// }
	// return null;
	// }
	//
	// @RequestMapping(value = "/users/{username}/quizResponse", method =
	// RequestMethod.PUT)
	// public QuizResponse updateQuizResponse(@PathVariable("username") String
	// username,
	// @ModelAttribute QuizResponse quizResponse) {
	// User savedUser = usersRepository.findByUsername(username);
	// QuizResponse savedQuizResponse = savedUser.getQuizResponse();
	// return savedQuizResponse;
	// }
	//
	// @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	// public User deleteUser(@PathVariable("id") String id) {
	// User p = usersRepository.findOne(id);
	// usersRepository.delete(p);
	// return p;
	// }

}
